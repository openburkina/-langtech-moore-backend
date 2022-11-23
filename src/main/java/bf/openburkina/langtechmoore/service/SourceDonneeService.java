package bf.openburkina.langtechmoore.service;

import bf.openburkina.langtechmoore.domain.Categorie;
import bf.openburkina.langtechmoore.domain.SourceDonnee;
import bf.openburkina.langtechmoore.repository.CategorieRepository;
import bf.openburkina.langtechmoore.repository.SourceDonneeRepository;
import bf.openburkina.langtechmoore.service.dto.MResponse;
import bf.openburkina.langtechmoore.service.dto.SourceDonneeDTO;
import bf.openburkina.langtechmoore.service.mapper.CategorieMapper;
import bf.openburkina.langtechmoore.service.mapper.SourceDonneeMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Service Implementation for managing {@link SourceDonnee}.
 */
@Service
@Transactional
public class SourceDonneeService {

    private final Logger log = LoggerFactory.getLogger(SourceDonneeService.class);

    private final SourceDonneeRepository sourceDonneeRepository;

    private final SourceDonneeMapper sourceDonneeMapper;
    private final UtilsService utilsService;

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    public SourceDonneeService(SourceDonneeRepository sourceDonneeRepository, SourceDonneeMapper sourceDonneeMapper, UtilsService utilsService, CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.sourceDonneeRepository = sourceDonneeRepository;
        this.sourceDonneeMapper = sourceDonneeMapper;
        this.utilsService = utilsService;
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    /**
     * Save a sourceDonnee.
     *
     * @param sourceDonneeDTO the entity to save.
     * @return the persisted entity.
     */
    public SourceDonneeDTO save(SourceDonneeDTO sourceDonneeDTO) {
        log.debug("Request to save SourceDonnee : {}", sourceDonneeDTO);
        //SourceDonnee sourceDonnee = sourceDonneeMapper.toEntity(sourceDonneeDTO);
        SourceDonnee sourceDonnee = new SourceDonnee();
        sourceDonnee.setLibelle(sourceDonneeDTO.getLibelle());
        isExist(sourceDonnee);
        return sourceDonneeMapper.toDto(sourceDonnee);
    }

    /**
     * Update a sourceDonnee.
     *
     * @param sourceDonneeDTO the entity to save.
     * @return the persisted entity.
     */
    public SourceDonneeDTO update(SourceDonneeDTO sourceDonneeDTO) {
        log.debug("Request to save SourceDonnee : {}", sourceDonneeDTO);
        Optional<SourceDonnee> src= sourceDonneeRepository.findById(sourceDonneeDTO.getId());
        SourceDonnee sourceDonnee =null;
        if (src.isPresent()){
            src.get().setLibelle(sourceDonneeDTO.getLibelle());
            sourceDonnee= sourceDonneeRepository.save(src.get());
        }
       // SourceDonnee sourceDonnee = sourceDonneeMapper.toEntity(sourceDonneeDTO);

        return sourceDonneeMapper.toDto(sourceDonnee);
    }

    /**
     * Partially update a sourceDonnee.
     *
     * @param sourceDonneeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SourceDonneeDTO> partialUpdate(SourceDonneeDTO sourceDonneeDTO) {
        log.debug("Request to partially update SourceDonnee : {}", sourceDonneeDTO);

        return sourceDonneeRepository
            .findById(sourceDonneeDTO.getId())
            .map(existingSourceDonnee -> {
                sourceDonneeMapper.partialUpdate(existingSourceDonnee, sourceDonneeDTO);

                return existingSourceDonnee;
            })
            .map(sourceDonneeRepository::save)
            .map(sourceDonneeMapper::toDto);
    }

    /**
     * Get all the sourceDonnees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDonneeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SourceDonnees");
        return sourceDonneeRepository.findAll(pageable).map(sourceDonneeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SourceDonneeDTO> findAllByCriteria(SourceDonneeDTO sourceDonneeDTO,Pageable pageable) {
        log.debug("Request to get all SourceDonnees");
        return sourceDonneeRepository.findAllWithCriteria(sourceDonneeDTO.getLibelle(),pageable).map(sourceDonneeMapper::toDto);
    }

    /**
     * Get one sourceDonnee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SourceDonneeDTO> findOne(Long id) {
        log.debug("Request to get SourceDonnee : {}", id);
        return sourceDonneeRepository.findById(id).map(sourceDonneeMapper::toDto);
    }

    /**
     * Delete the sourceDonnee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SourceDonnee : {}", id);
        sourceDonneeRepository.deleteById(id);
    }

    /**
     * Save a sourceDonnee.
     *
     * @param fichier the entity to save.
     * @return the persisted entity.
     */

    @Transactional()
    public MResponse saveComplete(byte[] fichier) throws IOException {

        InputStream fis = utilsService.bytesToInputStream(fichier);
        Workbook workbook = WorkbookFactory.create(fis);
        int numberOfSheet = workbook.getNumberOfSheets();

        MResponse m = new MResponse();
        m.setCode("0");

        for (int i = 0; i < numberOfSheet; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            int numberOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            if (sheet.getMergedRegions().isEmpty()){
                for (Row r: sheet){
                    if (numberOfColumns < r.getPhysicalNumberOfCells()) {
                        m.setCode("-1");
                        m.setMsg("Erreur sur la feuille " + sheet.getSheetName());
                    } else {
                        if (r.getRowNum() != 0 && r != null && utilsService.getCellValue(r.getCell(0)) != null && utilsService.getCellValue(r.getCell(1)) != null){
                            SourceDonnee s = new SourceDonnee();
                            String categorieLibelle = utilsService.getCellValue(r.getCell(1));
                            if (categorieLibelle != null){
                                Categorie c = categorieRepository.findByLibelle(categorieLibelle);
                                if (c != null){
                                    s.setCategorie(c);
                                }
                            }
                            s.setLibelle(utilsService.getCellValue(r.getCell(0)));
//                            else {
//                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                m.setCode("-1");
//                                m.setMsg("Erreur sur la categorie " + utilsService.getCellValue(r.getCell(1)));
//
//                                return m;
//                            }
                            isExist(s);
                        }
                    }
                }
            } else {
                m.setCode("-1");
                m.setMsg("Erreur sur la feuille " + sheet.getSheetName());
            }
        }
        return m;
    }

    public void isExist(SourceDonnee s){
        if (!sourceDonneeRepository.findByLibelle(s.getLibelle()).isPresent()){
            sourceDonneeRepository.save(s);
        }
    }
}
