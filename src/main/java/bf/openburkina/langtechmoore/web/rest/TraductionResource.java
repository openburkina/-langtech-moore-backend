package bf.openburkina.langtechmoore.web.rest;

import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.repository.TraductionRepository;
import bf.openburkina.langtechmoore.service.TraductionService;
import bf.openburkina.langtechmoore.service.dto.AllContributionDTO;
import bf.openburkina.langtechmoore.service.dto.StatMoisDTO;
import bf.openburkina.langtechmoore.service.dto.TraductionDTO;
import bf.openburkina.langtechmoore.service.dto.XSourceDTO;
import bf.openburkina.langtechmoore.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * REST controller for managing {@link bf.openburkina.langtechmoore.domain.Traduction}.
 */
@RestController
@RequestMapping("/api")
public class TraductionResource {

    private final Logger log = LoggerFactory.getLogger(TraductionResource.class);

    private static final String ENTITY_NAME = "traduction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TraductionService traductionService;

    private final TraductionRepository traductionRepository;

    public TraductionResource(TraductionService traductionService, TraductionRepository traductionRepository) {
        this.traductionService = traductionService;
        this.traductionRepository = traductionRepository;
    }

    /**
     * {@code POST  /traductions} : Create a new traduction.
     *
     * @param traductionDTO the traductionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new traductionDTO, or with status {@code 400 (Bad Request)} if the traduction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/traductions")
    public ResponseEntity<TraductionDTO> createTraduction(@Valid @RequestBody TraductionDTO traductionDTO) throws URISyntaxException {
        log.debug("REST request to save Traduction : {}", traductionDTO);
        if (traductionDTO.getId() != null) {
            throw new BadRequestAlertException("A new traduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TraductionDTO result = traductionService.save(traductionDTO);
        return ResponseEntity
            .created(new URI("/api/traductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /traductions/:id} : Updates an existing traduction.
     *
     * @param id            the id of the traductionDTO to save.
     * @param traductionDTO the traductionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traductionDTO,
     * or with status {@code 400 (Bad Request)} if the traductionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the traductionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/traductions/{id}")
    public ResponseEntity<TraductionDTO> updateTraduction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TraductionDTO traductionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Traduction : {}, {}", id, traductionDTO);
        if (traductionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traductionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traductionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TraductionDTO result = traductionService.update(traductionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, traductionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /traductions/:id} : Partial updates given fields of an existing traduction, field will ignore if it is null
     *
     * @param id            the id of the traductionDTO to save.
     * @param traductionDTO the traductionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated traductionDTO,
     * or with status {@code 400 (Bad Request)} if the traductionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the traductionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the traductionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/traductions/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<TraductionDTO> partialUpdateTraduction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TraductionDTO traductionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Traduction partially : {}, {}", id, traductionDTO);
        if (traductionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, traductionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!traductionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TraductionDTO> result = traductionService.partialUpdate(traductionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, traductionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /traductions} : get all the traductions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of traductions in body.
     */
    @GetMapping("/traductions")
    public ResponseEntity<List<TraductionDTO>> getAllTraductions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Traductions");
         Page<TraductionDTO> page = traductionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/traductions/criteria")
    public ResponseEntity<List<TraductionDTO>> getAllTraductionsByCriteria(@RequestBody TraductionDTO traductionDTO,@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Traductions");
        Page<TraductionDTO> page = traductionService.findAllByCriteria(traductionDTO,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /traductions/:id} : get the "id" traduction.
     *
     * @param id the id of the traductionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traductionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/traductions/{id}")
    public ResponseEntity<TraductionDTO> getTraduction(@PathVariable Long id) {
        log.debug("REST request to get Traduction : {}", id);
        Optional<TraductionDTO> traductionDTO = traductionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(traductionDTO);
    }

    @GetMapping("/traductions-by-contibuteur/{id}")
    public List<TraductionDTO> getTraductionByContributeur(@PathVariable Long id) {
        log.debug("REST request to get Traduction : {}", id);
        return traductionService.getTraductionByContributeur(id);
    }

    /**
     * {@code DELETE  /traductions/:id} : delete the "id" traduction.
     *
     * @param id the id of the traductionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/traductions/{id}")
    public ResponseEntity<Void> deleteTraduction(@PathVariable Long id) {
        log.debug("REST request to delete Traduction : {}", id);
        traductionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

 /*   @PostMapping("/upload/traduction/fichier")
    public TraductionDTO singleFileUpload(@RequestBody MultipartFile file) throws  Exception {
         return  traductionService.saveMultimedia(file);
    }*/

    // TODO: 03/11/2022 Resource d'appel pour la création de traduction avec depot ficchier sur le repertoire ajout du nouveau champ de stockage du lien du fichier
     @PostMapping("/upload/traduction/fichier")
    public TraductionDTO singleFileUpload(@RequestBody TraductionDTO traductionDTO) throws  Exception {
        return  traductionService.saveMultimedia(traductionDTO);
    }


    // TODO: 03/11/2022 Resource pour la récupération des traductions

    @GetMapping("/document/traduction")
    @Timed
    public TraductionDTO getDocTraduction(@RequestParam Long traductionId) throws IOException {
        return traductionService.getTraductionDoc(traductionId);
    }

    /**
     * {@code GET  /traductions/:id} : get the "id" traduction.
     *
     * @param t the traductionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the traductionDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/traductions/validation")
    public ResponseEntity<TraductionDTO> validation(@RequestBody TraductionDTO t) {
        log.debug("REST request to get Traduction : {}", t.getId());
        Optional<TraductionDTO> traductionDTO = traductionService.validation(t.getId(), t.getEtat().toString(),t.getMotif());
        return ResponseUtil.wrapOrNotFound(traductionDTO);
    }

    @PostMapping("/getStatistique")
    public List<AllContributionDTO> getStatistique(@RequestBody XSourceDTO xSourceDTO){
        return traductionService.getStatContribution(xSourceDTO);
    }

    @GetMapping("/traductions-by-source/{srcId}")
    public ResponseEntity<List<TraductionDTO>> getAllTraductions(@PathVariable Long srcId) {
        log.debug("REST request to get a page of Traductions");
        return ResponseEntity.ok().body(traductionService.getTraductionsBySource(srcId));
    }

    // TODO: 29/11/2022 Resource pour la récupération des stats des contribution en fonction de mois
    @GetMapping("/traductions-get-info-stats-mois")
    public List<StatMoisDTO> getInfoStatsMois() {
        return traductionService.getInfoStatsMois();
    }
}
