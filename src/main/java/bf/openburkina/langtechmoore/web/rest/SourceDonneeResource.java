package bf.openburkina.langtechmoore.web.rest;

import bf.openburkina.langtechmoore.repository.SourceDonneeRepository;
import bf.openburkina.langtechmoore.service.SourceDonneeService;
import bf.openburkina.langtechmoore.service.dto.MResponse;
import bf.openburkina.langtechmoore.service.dto.SourceDonneeDTO;
import bf.openburkina.langtechmoore.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link bf.openburkina.langtechmoore.domain.SourceDonnee}.
 */
@RestController
@RequestMapping("/api")
public class SourceDonneeResource {

    private final Logger log = LoggerFactory.getLogger(SourceDonneeResource.class);

    private static final String ENTITY_NAME = "sourceDonnee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SourceDonneeService sourceDonneeService;

    private final SourceDonneeRepository sourceDonneeRepository;

    public SourceDonneeResource(SourceDonneeService sourceDonneeService, SourceDonneeRepository sourceDonneeRepository) {
        this.sourceDonneeService = sourceDonneeService;
        this.sourceDonneeRepository = sourceDonneeRepository;
    }

    /**
     * {@code POST  /source-donnees} : Create a new sourceDonnee.
     *
     * @param sourceDonneeDTO the sourceDonneeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourceDonneeDTO, or with status {@code 400 (Bad Request)} if the sourceDonnee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/source-donnees")
    public ResponseEntity<SourceDonneeDTO> createSourceDonnee(@RequestBody SourceDonneeDTO sourceDonneeDTO)
        throws URISyntaxException {
        log.debug("REST request to save SourceDonnee : {}", sourceDonneeDTO);
        if (sourceDonneeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sourceDonnee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SourceDonneeDTO result = sourceDonneeService.save(sourceDonneeDTO);
        return ResponseEntity
            .created(new URI("/api/source-donnees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /source-donnees/:id} : Updates an existing sourceDonnee.
     *
     * @param id the id of the sourceDonneeDTO to save.
     * @param sourceDonneeDTO the sourceDonneeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceDonneeDTO,
     * or with status {@code 400 (Bad Request)} if the sourceDonneeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sourceDonneeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/source-donnees/{id}")
    public ResponseEntity<SourceDonneeDTO> updateSourceDonnee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SourceDonneeDTO sourceDonneeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SourceDonnee : {}, {}", id, sourceDonneeDTO);
        if (sourceDonneeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceDonneeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceDonneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SourceDonneeDTO result = sourceDonneeService.update(sourceDonneeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sourceDonneeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /source-donnees/:id} : Partial updates given fields of an existing sourceDonnee, field will ignore if it is null
     *
     * @param id the id of the sourceDonneeDTO to save.
     * @param sourceDonneeDTO the sourceDonneeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourceDonneeDTO,
     * or with status {@code 400 (Bad Request)} if the sourceDonneeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sourceDonneeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sourceDonneeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/source-donnees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SourceDonneeDTO> partialUpdateSourceDonnee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SourceDonneeDTO sourceDonneeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SourceDonnee partially : {}, {}", id, sourceDonneeDTO);
        if (sourceDonneeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sourceDonneeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sourceDonneeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SourceDonneeDTO> result = sourceDonneeService.partialUpdate(sourceDonneeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sourceDonneeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /source-donnees} : get all the sourceDonnees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sourceDonnees in body.
     */
    @GetMapping("/source-donnees")
    public ResponseEntity<List<SourceDonneeDTO>> getAllSourceDonnees(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SourceDonnees");
        Page<SourceDonneeDTO> page = sourceDonneeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/source-donnees/criteria")
    public ResponseEntity<List<SourceDonneeDTO>> getAllSourceDonneesByCriteria(@RequestBody SourceDonneeDTO sourceDonneeDTO,@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SourceDonnees");
        Page<SourceDonneeDTO> page = sourceDonneeService.findAllByCriteria(sourceDonneeDTO,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /source-donnees/:id} : get the "id" sourceDonnee.
     *
     * @param id the id of the sourceDonneeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sourceDonneeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/source-donnees/{id}")
    public ResponseEntity<SourceDonneeDTO> getSourceDonnee(@PathVariable Long id) {
        log.debug("REST request to get SourceDonnee : {}", id);
        Optional<SourceDonneeDTO> sourceDonneeDTO = sourceDonneeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sourceDonneeDTO);
    }

    /**
     * {@code DELETE  /source-donnees/:id} : delete the "id" sourceDonnee.
     *
     * @param id the id of the sourceDonneeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/source-donnees/{id}")
    public ResponseEntity<Void> deleteSourceDonnee(@PathVariable Long id) {
        log.debug("REST request to delete SourceDonnee : {}", id);
        sourceDonneeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /source-donnees} : Create a new sourceDonnee.
     *
     * @param s the sourceDonneeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourceDonneeDTO, or with status {@code 400 (Bad Request)} if the sourceDonnee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/source-donnees/upload")
    public ResponseEntity<MResponse> uploadSourceDonnee(@RequestBody SourceDonneeDTO s)
        throws IOException {
        log.debug("REST request to save SourceDonnee : {}", s);
        MResponse result = sourceDonneeService.saveComplete(s.getFile());
        return ResponseEntity.ok().body(result);
    }


}
