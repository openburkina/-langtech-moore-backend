package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.repository.TraductionRepository;
import bf.openburkina.langtechmoore.service.dto.TraductionDTO;
import bf.openburkina.langtechmoore.service.mapper.TraductionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Traduction}.
 */
@Service
@Transactional
public class TraductionService {

    private final Logger log = LoggerFactory.getLogger(TraductionService.class);

    private final TraductionRepository traductionRepository;

    private final TraductionMapper traductionMapper;

    public TraductionService(TraductionRepository traductionRepository, TraductionMapper traductionMapper) {
        this.traductionRepository = traductionRepository;
        this.traductionMapper = traductionMapper;
    }

    /**
     * Save a traduction.
     *
     * @param traductionDTO the entity to save.
     * @return the persisted entity.
     */
    public TraductionDTO save(TraductionDTO traductionDTO) {
        log.debug("Request to save Traduction : {}", traductionDTO);
        Traduction traduction = traductionMapper.toEntity(traductionDTO);
        traduction = traductionRepository.save(traduction);
        return traductionMapper.toDto(traduction);
    }

    /**
     * Update a traduction.
     *
     * @param traductionDTO the entity to save.
     * @return the persisted entity.
     */
    public TraductionDTO update(TraductionDTO traductionDTO) {
        log.debug("Request to save Traduction : {}", traductionDTO);
        Traduction traduction = traductionMapper.toEntity(traductionDTO);
        traduction = traductionRepository.save(traduction);
        return traductionMapper.toDto(traduction);
    }

    /**
     * Partially update a traduction.
     *
     * @param traductionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TraductionDTO> partialUpdate(TraductionDTO traductionDTO) {
        log.debug("Request to partially update Traduction : {}", traductionDTO);

        return traductionRepository
            .findById(traductionDTO.getId())
            .map(existingTraduction -> {
                traductionMapper.partialUpdate(existingTraduction, traductionDTO);

                return existingTraduction;
            })
            .map(traductionRepository::save)
            .map(traductionMapper::toDto);
    }

    /**
     * Get all the traductions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TraductionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Traductions");
        return traductionRepository.findAll(pageable).map(traductionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TraductionDTO> findAllByCriteria(TraductionDTO traductionDTO,Pageable pageable) {
        log.debug("Request to get all Traductions");
        return traductionRepository.findAllWithCriteria(
            traductionDTO.getLibelle(),
            traductionDTO.getEtat(),
            traductionDTO.getType(),
            traductionDTO.getContenuAudioContentType(),
            traductionDTO.getSourceDonnee()!=null?traductionDTO.getSourceDonnee().getId():null,
            traductionDTO.getUtilisateur()!=null?traductionDTO.getUtilisateur().getId():null,
            traductionDTO.getLangue()!=null?traductionDTO.getLangue().getId():null
            ,pageable).map(traductionMapper::toDto);
    }

    /**
     * Get one traduction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TraductionDTO> findOne(Long id) {
        log.debug("Request to get Traduction : {}", id);
        return traductionRepository.findById(id).map(traductionMapper::toDto);
    }

    /**
     * Delete the traduction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Traduction : {}", id);
        traductionRepository.deleteById(id);
    }
}
