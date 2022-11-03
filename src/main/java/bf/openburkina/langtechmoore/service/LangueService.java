package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Langue;
import bf.openburkina.langtechmoore.repository.LangueRepository;
import bf.openburkina.langtechmoore.service.dto.LangueDTO;
import bf.openburkina.langtechmoore.service.mapper.LangueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Langue}.
 */
@Service
@Transactional
public class LangueService {

    private final Logger log = LoggerFactory.getLogger(LangueService.class);

    private final LangueRepository langueRepository;

    private final LangueMapper langueMapper;

    public LangueService(LangueRepository langueRepository, LangueMapper langueMapper) {
        this.langueRepository = langueRepository;
        this.langueMapper = langueMapper;
    }

    /**
     * Save a langue.
     *
     * @param langueDTO the entity to save.
     * @return the persisted entity.
     */
    public LangueDTO save(LangueDTO langueDTO) {
        log.debug("Request to save Langue : {}", langueDTO);
        Langue langue = langueMapper.toEntity(langueDTO);
        langue = langueRepository.save(langue);
        return langueMapper.toDto(langue);
    }

    /**
     * Update a langue.
     *
     * @param langueDTO the entity to save.
     * @return the persisted entity.
     */
    public LangueDTO update(LangueDTO langueDTO) {
        log.debug("Request to save Langue : {}", langueDTO);
        Langue langue = langueMapper.toEntity(langueDTO);
        langue = langueRepository.save(langue);
        return langueMapper.toDto(langue);
    }

    /**
     * Partially update a langue.
     *
     * @param langueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LangueDTO> partialUpdate(LangueDTO langueDTO) {
        log.debug("Request to partially update Langue : {}", langueDTO);

        return langueRepository
            .findById(langueDTO.getId())
            .map(existingLangue -> {
                langueMapper.partialUpdate(existingLangue, langueDTO);

                return existingLangue;
            })
            .map(langueRepository::save)
            .map(langueMapper::toDto);
    }

    /**
     * Get all the langues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LangueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Langues");
        return langueRepository.findAll(pageable).map(langueMapper::toDto);
    }

    /**
     * Get one langue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LangueDTO> findOne(Long id) {
        log.debug("Request to get Langue : {}", id);
        return langueRepository.findById(id).map(langueMapper::toDto);
    }

    /**
     * Delete the langue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Langue : {}", id);
        langueRepository.deleteById(id);
    }
}
