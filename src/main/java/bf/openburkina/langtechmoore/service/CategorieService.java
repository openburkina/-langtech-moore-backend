package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Categorie;
import bf.openburkina.langtechmoore.repository.CategorieRepository;
import bf.openburkina.langtechmoore.service.dto.CategorieDTO;
import bf.openburkina.langtechmoore.service.mapper.CategorieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Categorie}.
 */
@Service
@Transactional
public class CategorieService {

    private final Logger log = LoggerFactory.getLogger(CategorieService.class);

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    public CategorieService(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieDTO save(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(categorie);
    }

    /**
     * Update a categorie.
     *
     * @param categorieDTO the entity to save.
     * @return the persisted entity.
     */
    public CategorieDTO update(CategorieDTO categorieDTO) {
        log.debug("Request to save Categorie : {}", categorieDTO);
        Categorie categorie = categorieMapper.toEntity(categorieDTO);
        categorie = categorieRepository.save(categorie);
        return categorieMapper.toDto(categorie);
    }

    /**
     * Partially update a categorie.
     *
     * @param categorieDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategorieDTO> partialUpdate(CategorieDTO categorieDTO) {
        log.debug("Request to partially update Categorie : {}", categorieDTO);

        return categorieRepository
            .findById(categorieDTO.getId())
            .map(existingCategorie -> {
                categorieMapper.partialUpdate(existingCategorie, categorieDTO);

                return existingCategorie;
            })
            .map(categorieRepository::save)
            .map(categorieMapper::toDto);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categorieRepository.findAll(pageable).map(categorieMapper::toDto);
    }

    /**
     * Get one categorie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategorieDTO> findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id).map(categorieMapper::toDto);
    }

    /**
     * Delete the categorie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categorie : {}", id);
        categorieRepository.deleteById(id);
    }
}
