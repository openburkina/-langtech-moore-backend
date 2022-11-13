package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Authority;
import bf.openburkina.langtechmoore.domain.Profil;
import bf.openburkina.langtechmoore.domain.User;
import bf.openburkina.langtechmoore.domain.Utilisateur;
import bf.openburkina.langtechmoore.repository.AuthorityRepository;
import bf.openburkina.langtechmoore.repository.ProfilRepository;
import bf.openburkina.langtechmoore.repository.UserRepository;
import bf.openburkina.langtechmoore.repository.UtilisateurRepository;
import bf.openburkina.langtechmoore.security.AuthoritiesConstants;
import bf.openburkina.langtechmoore.service.dto.ProfilDTO;
import bf.openburkina.langtechmoore.service.mapper.ProfilMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profil}.
 */
@Service
@Transactional
public class ProfilService {

    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    private final AuthorityRepository authorityRepository;

    private final UtilisateurRepository utilisateurRepository;

    private final UserRepository userRepository;

    public ProfilService(ProfilRepository profilRepository, ProfilMapper profilMapper, AuthorityRepository authorityRepository, UtilisateurRepository utilisateurRepository, UserRepository userRepository) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.authorityRepository = authorityRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a profil.
     *
     * @param profilDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfilDTO save(ProfilDTO profilDTO) {
        log.debug("Request to save Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        if (profilDTO.getAuthorities() != null) {
            Set<String> auths = profilDTO.getAuthorities();
            //Ajout du role USER par default
            auths.add(AuthoritiesConstants.USER);
            Set<Authority> authorities = profilDTO.getAuthorities().stream().map(authorityRepository::getOne).collect(Collectors.toSet());
            profil.setRoles(authorities);
            log.debug("PROFIL INFOS ===> {}", profil);

            /*
            Mise à jour des roles des utilisateurs ayant ce profil
            Pour verifier  que les roles de l'utilisateur ont changes
            on utilise l'attribut profilChange
         */
            if (profilDTO.getProfilsChange() != null) {
                if (profilDTO.getProfilsChange().equals(Boolean.TRUE)) {
                    //List<User> user = userRepository.findByProfilId(profilDTO.getId());
                    List<Utilisateur> utilisateurs = utilisateurRepository.findByProfilId(profilDTO.getId());
                    utilisateurs.forEach(user1 -> {
                        User user = user1.getUser();
                        if (user != null) {
                            user.setAuthorities(authorities);
                            userRepository.save(user);
                        }
                    });
                }
            }
        }
        profil = profilRepository.save(profil);
        return profilMapper.toDto(profil);
    }

    /*public ProfilDTO save(ProfilDTO profilDTO) {
        log.debug("Request to save Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        profil = profilRepository.save(profil);
        return profilMapper.toDto(profil);
    }*/

    /**
     * Update a profil.
     *
     * @param profilDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfilDTO update(ProfilDTO profilDTO) {
        log.debug("Request to save Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        profil = profilRepository.save(profil);
        return profilMapper.toDto(profil);
    }

    /**
     * Partially update a profil.
     *
     * @param profilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfilDTO> partialUpdate(ProfilDTO profilDTO) {
        log.debug("Request to partially update Profil : {}", profilDTO);

        return profilRepository
            .findById(profilDTO.getId())
            .map(existingProfil -> {
                profilMapper.partialUpdate(existingProfil, profilDTO);

                return existingProfil;
            })
            .map(profilRepository::save)
            .map(profilMapper::toDto);
    }

    /**
     * Get all the profils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profils");
        Page<ProfilDTO> profils = profilRepository.findAll(pageable).map(profil -> {
            ProfilDTO profilDTO = profilMapper.toDto(profil);
            profilDTO.setAuthorities(profil.getRoles().stream().map(Authority::getName).collect(Collectors.toSet()));
            return profilDTO;
        });
        log.debug("RETURN PROFILS ===> {}", profils.getContent());
        return profils;
    }

    /**
     * Get one profil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfilDTO> findOne(Long id) {
        log.debug("Request to get Profil : {}", id);
        Profil profil = profilRepository.findOneWithEagerRelationships(id);
        ProfilDTO profilDTO = profilMapper.toDto(profil);
        profilDTO.setAuthorities(profil.getRoles().stream().map(Authority::getName).collect(Collectors.toSet()));
        return Optional.of(profilDTO);
    }

    /**
     * Delete the profil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }
}
