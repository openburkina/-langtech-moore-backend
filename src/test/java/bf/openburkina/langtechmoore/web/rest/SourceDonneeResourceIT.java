package bf.openburkina.langtechmoore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bf.openburkina.langtechmoore.IntegrationTest;
import bf.openburkina.langtechmoore.domain.SourceDonnee;
import bf.openburkina.langtechmoore.repository.SourceDonneeRepository;
import bf.openburkina.langtechmoore.service.dto.SourceDonneeDTO;
import bf.openburkina.langtechmoore.service.mapper.SourceDonneeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SourceDonneeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SourceDonneeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/source-donnees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SourceDonneeRepository sourceDonneeRepository;

    @Autowired
    private SourceDonneeMapper sourceDonneeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSourceDonneeMockMvc;

    private SourceDonnee sourceDonnee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceDonnee createEntity(EntityManager em) {
        SourceDonnee sourceDonnee = new SourceDonnee().libelle(DEFAULT_LIBELLE);
        return sourceDonnee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SourceDonnee createUpdatedEntity(EntityManager em) {
        SourceDonnee sourceDonnee = new SourceDonnee().libelle(UPDATED_LIBELLE);
        return sourceDonnee;
    }

    @BeforeEach
    public void initTest() {
        sourceDonnee = createEntity(em);
    }

    @Test
    @Transactional
    void createSourceDonnee() throws Exception {
        int databaseSizeBeforeCreate = sourceDonneeRepository.findAll().size();
        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);
        restSourceDonneeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeCreate + 1);
        SourceDonnee testSourceDonnee = sourceDonneeList.get(sourceDonneeList.size() - 1);
        assertThat(testSourceDonnee.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void createSourceDonneeWithExistingId() throws Exception {
        // Create the SourceDonnee with an existing ID
        sourceDonnee.setId(1L);
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        int databaseSizeBeforeCreate = sourceDonneeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceDonneeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceDonneeRepository.findAll().size();
        // set the field null
        sourceDonnee.setLibelle(null);

        // Create the SourceDonnee, which fails.
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        restSourceDonneeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSourceDonnees() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        // Get all the sourceDonneeList
        restSourceDonneeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourceDonnee.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }

    @Test
    @Transactional
    void getSourceDonnee() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        // Get the sourceDonnee
        restSourceDonneeMockMvc
            .perform(get(ENTITY_API_URL_ID, sourceDonnee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sourceDonnee.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    void getNonExistingSourceDonnee() throws Exception {
        // Get the sourceDonnee
        restSourceDonneeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSourceDonnee() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();

        // Update the sourceDonnee
        SourceDonnee updatedSourceDonnee = sourceDonneeRepository.findById(sourceDonnee.getId()).get();
        // Disconnect from session so that the updates on updatedSourceDonnee are not directly saved in db
        em.detach(updatedSourceDonnee);
        updatedSourceDonnee.libelle(UPDATED_LIBELLE);
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(updatedSourceDonnee);

        restSourceDonneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceDonneeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
        SourceDonnee testSourceDonnee = sourceDonneeList.get(sourceDonneeList.size() - 1);
        assertThat(testSourceDonnee.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void putNonExistingSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceDonneeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSourceDonneeWithPatch() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();

        // Update the sourceDonnee using partial update
        SourceDonnee partialUpdatedSourceDonnee = new SourceDonnee();
        partialUpdatedSourceDonnee.setId(sourceDonnee.getId());

        restSourceDonneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceDonnee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSourceDonnee))
            )
            .andExpect(status().isOk());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
        SourceDonnee testSourceDonnee = sourceDonneeList.get(sourceDonneeList.size() - 1);
        assertThat(testSourceDonnee.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void fullUpdateSourceDonneeWithPatch() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();

        // Update the sourceDonnee using partial update
        SourceDonnee partialUpdatedSourceDonnee = new SourceDonnee();
        partialUpdatedSourceDonnee.setId(sourceDonnee.getId());

        partialUpdatedSourceDonnee.libelle(UPDATED_LIBELLE);

        restSourceDonneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSourceDonnee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSourceDonnee))
            )
            .andExpect(status().isOk());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
        SourceDonnee testSourceDonnee = sourceDonneeList.get(sourceDonneeList.size() - 1);
        assertThat(testSourceDonnee.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void patchNonExistingSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sourceDonneeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSourceDonnee() throws Exception {
        int databaseSizeBeforeUpdate = sourceDonneeRepository.findAll().size();
        sourceDonnee.setId(count.incrementAndGet());

        // Create the SourceDonnee
        SourceDonneeDTO sourceDonneeDTO = sourceDonneeMapper.toDto(sourceDonnee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceDonneeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sourceDonneeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SourceDonnee in the database
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSourceDonnee() throws Exception {
        // Initialize the database
        sourceDonneeRepository.saveAndFlush(sourceDonnee);

        int databaseSizeBeforeDelete = sourceDonneeRepository.findAll().size();

        // Delete the sourceDonnee
        restSourceDonneeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sourceDonnee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SourceDonnee> sourceDonneeList = sourceDonneeRepository.findAll();
        assertThat(sourceDonneeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
