package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Authority;
import bf.openburkina.langtechmoore.domain.Profil;
import bf.openburkina.langtechmoore.domain.User;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.domain.enumeration.TypeUtilisateur;
import bf.openburkina.langtechmoore.repository.AuthorityRepository;
import bf.openburkina.langtechmoore.repository.ProfilRepository;
import bf.openburkina.langtechmoore.repository.UserRepository;
import bf.openburkina.langtechmoore.repository.UtilisateurRepository;
import bf.openburkina.langtechmoore.service.dto.UserDTO;
import bf.openburkina.langtechmoore.service.dto.UtilisateurDTO;
import bf.openburkina.langtechmoore.service.mapper.UserMapper;
import bf.openburkina.langtechmoore.service.mapper.UtilisateurMapper;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service Implementation for managing {@link Utilisateur}.
 */
@Service
@Transactional
public class UtilisateurService {

    private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper utilisateurMapper;

    private final ProfilRepository profilRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthorityRepository authorityRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper, ProfilRepository profilRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper, AuthorityRepository authorityRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.profilRepository = profilRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a utilisateur.
     *
     * @param utilisateurDTO the entity to save.
     * @return the persisted entity.
     */
    public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
        log.debug("Request to save Utilisateur : {}", utilisateurDTO);
        User user = new User();
        String encryptedPassword;
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        System.out.println("========================= "+utilisateurDTO.getProfil());
        Profil profil = profilRepository.findOneWithEagerRelationships(utilisateurDTO.getProfil().getId());
        user.setLogin(utilisateurDTO.getEmail());
        user.setEmail(utilisateurDTO.getEmail());
       // String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        if (utilisateurDTO.getPassword()!=null && utilisateurDTO.getPassword().trim().length()>0){
            encryptedPassword = passwordEncoder.encode(utilisateurDTO.getPassword());
        }else {
            encryptedPassword = passwordEncoder.encode("langtech");
        }
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        user.setDefaultPassord(false);
        user.setLastName(utilisateurDTO.getNom());
        user.setFirstName(utilisateurDTO.getPrenom());
        if (profil != null) {
            Set<Authority> profilRoles = profil.getRoles();
            Set<Authority> authorities = new HashSet<>();
            profilRoles.forEach(authority -> {
                Authority autho = new Authority();
                autho.setName(authority.getName());
                authorities.add(autho);
            });
            if (!authorities.isEmpty()) {
                user.setAuthorities(authorities);
            } else {
                throw new RuntimeException("Liste vide");
            }
            utilisateur.setProfil(profil);
            user = userRepository.save(user);
        } else {
            throw new RuntimeException("Profil introuvable !");
        }

        utilisateur.setUser(user);
        utilisateur = utilisateurRepository.save(utilisateur);
        try {
           // taskExecutor.execute(new SendMail(user, "NEW_PASSWORD"));
            //sendGridMailService.sendPasswordCreateResetMailWithDynamicTemplate(user, "NEW_PASSWORD");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateurMapper.toDto(utilisateur);
    }

   /* public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
        log.debug("Request to save Utilisateur : {}", utilisateurDTO);
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }*/

    /**
     * Update a utilisateur.
     *
     * @param utilisateurDTO the entity to save.
     * @return the persisted entity.
     */
    public UtilisateurDTO update(UtilisateurDTO utilisateurDTO) {
        log.debug("Request to save Utilisateur : {}", utilisateurDTO);
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    public UtilisateurDTO updateUser(UtilisateurDTO utilisateurDTO) {
        log.debug("Request to updated Utilisateur : {}", utilisateurDTO);
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDTO);
        UserDTO userDTO;
        User user;
        Utilisateur ut = utilisateurRepository.getReferenceById(utilisateurDTO.getId());
        user = ut.getUser();
        userDTO = userMapper.userToUserDTO(user);
        Set<Authority> managedAuthorities = user.getAuthorities();
        managedAuthorities.clear();
        userDTO.getAuthorities().stream().map(authorityRepository::getReferenceById).forEach(managedAuthorities::add);
        if (utilisateur.getProfil() != null) {
            utilisateur.setProfil(profilRepository.findById(utilisateur.getProfil().getId()).get());
        }
        user.setLogin(utilisateurDTO.getEmail());
        user.setEmail(utilisateurDTO.getEmail());
        user.setLastName(utilisateurDTO.getNom());
        user.setFirstName(utilisateurDTO.getPrenom());
        user = userRepository.save(user);
        utilisateur.setUser(user);
        utilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }


    /**
     * Partially update a utilisateur.
     *
     * @param utilisateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UtilisateurDTO> partialUpdate(UtilisateurDTO utilisateurDTO) {
        log.debug("Request to partially update Utilisateur : {}", utilisateurDTO);

        return utilisateurRepository
            .findById(utilisateurDTO.getId())
            .map(existingUtilisateur -> {
                utilisateurMapper.partialUpdate(existingUtilisateur, utilisateurDTO);

                return existingUtilisateur;
            })
            .map(utilisateurRepository::save)
            .map(utilisateurMapper::toDto);
    }

    /**
     * Get all the utilisateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UtilisateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Utilisateurs");
        return utilisateurRepository.findAll(pageable).map(utilisateurMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<UtilisateurDTO> findAllByCriteria(UtilisateurDTO utilisateurDTO,Pageable pageable) {
        log.debug("Request to get all Traductions");
        return utilisateurRepository.findAllWithCriteria(
            utilisateurDTO.getNom(),
            utilisateurDTO.getPrenom(),
            utilisateurDTO.getEmail(),
            utilisateurDTO.getTelephone(),
            utilisateurDTO.getTypeUtilisateur()!=null?utilisateurDTO.getTypeUtilisateur():null
            ,pageable).map(utilisateurMapper::toDto);
    }

    /**
     * Get one utilisateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UtilisateurDTO> findOne(Long id) {
        log.debug("Request to get Utilisateur : {}", id);
        return utilisateurRepository.findById(id).map(utilisateurMapper::toDto);
    }

    /**
     * Delete the utilisateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Utilisateur : {}", id);
        utilisateurRepository.deleteById(id);
    }

    public List<UtilisateurDTO> findAllContributeur() {
        List<Utilisateur> optionalUtilisateurs = utilisateurRepository.findByTypeUtilisateur(TypeUtilisateur.CONTRIBUTEUR);
        return Optional.of(optionalUtilisateurs).map(utilisateurs -> utilisateurs.stream().map(utilisateurMapper::toDto).collect(Collectors.toList())).orElse(null);
    }

    @Transactional(readOnly = true)
    public long countContributeur() {
        log.debug("Request to get all Utilisateurs");
        return utilisateurRepository.findByTypeUtilisateur(TypeUtilisateur.CONTRIBUTEUR).size();
    }
}
