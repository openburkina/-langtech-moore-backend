package bf.openburkina.langtechmoore.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bf.openburkina.langtechmoore.IntegrationTest;
import bf.openburkina.langtechmoore.domain.Traduction;
import bf.openburkina.langtechmoore.domain.enumeration.Etat;
import bf.openburkina.langtechmoore.domain.enumeration.TypeTraduction;
import bf.openburkina.langtechmoore.repository.TraductionRepository;
import bf.openburkina.langtechmoore.service.dto.TraductionDTO;
import bf.openburkina.langtechmoore.service.mapper.TraductionMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TraductionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TraductionResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU_TEXTE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU_TEXTE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENU_AUDIO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENU_AUDIO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENU_AUDIO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENU_AUDIO_CONTENT_TYPE = "image/png";

    private static final TypeTraduction DEFAULT_TYPE = TypeTraduction.TEXTE;
    private static final TypeTraduction UPDATED_TYPE = TypeTraduction.AUDIO;

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;

    private static final Etat DEFAULT_ETAT = Etat.EN_ATTENTE;
    private static final Etat UPDATED_ETAT = Etat.VALIDER;

    private static final String ENTITY_API_URL = "/api/traductions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TraductionRepository traductionRepository;

    @Autowired
    private TraductionMapper traductionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTraductionMockMvc;

    private Traduction traduction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traduction createEntity(EntityManager em) {
        Traduction traduction = new Traduction()
            .libelle(DEFAULT_LIBELLE)
            .contenuTexte(DEFAULT_CONTENU_TEXTE)
            .contenuAudio(DEFAULT_CONTENU_AUDIO)
            .contenuAudioContentType(DEFAULT_CONTENU_AUDIO_CONTENT_TYPE)
            .type(DEFAULT_TYPE)
            .note(DEFAULT_NOTE)
            .etat(DEFAULT_ETAT);
        return traduction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traduction createUpdatedEntity(EntityManager em) {
        Traduction traduction = new Traduction()
            .libelle(UPDATED_LIBELLE)
            .contenuTexte(UPDATED_CONTENU_TEXTE)
            .contenuAudio(UPDATED_CONTENU_AUDIO)
            .contenuAudioContentType(UPDATED_CONTENU_AUDIO_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .note(UPDATED_NOTE)
            .etat(UPDATED_ETAT);
        return traduction;
    }

    @BeforeEach
    public void initTest() {
        traduction = createEntity(em);
    }

    @Test
    @Transactional
    void createTraduction() throws Exception {
        int databaseSizeBeforeCreate = traductionRepository.findAll().size();
        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);
        restTraductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traductionDTO)))
            .andExpect(status().isCreated());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeCreate + 1);
        Traduction testTraduction = traductionList.get(traductionList.size() - 1);
        assertThat(testTraduction.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTraduction.getContenuTexte()).isEqualTo(DEFAULT_CONTENU_TEXTE);
        assertThat(testTraduction.getContenuAudio()).isEqualTo(DEFAULT_CONTENU_AUDIO);
        assertThat(testTraduction.getContenuAudioContentType()).isEqualTo(DEFAULT_CONTENU_AUDIO_CONTENT_TYPE);
        assertThat(testTraduction.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTraduction.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTraduction.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    @Transactional
    void createTraductionWithExistingId() throws Exception {
        // Create the Traduction with an existing ID
        traduction.setId(1L);
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        int databaseSizeBeforeCreate = traductionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traductionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = traductionRepository.findAll().size();
        // set the field null
        traduction.setLibelle(null);

        // Create the Traduction, which fails.
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        restTraductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traductionDTO)))
            .andExpect(status().isBadRequest());

        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = traductionRepository.findAll().size();
        // set the field null
        traduction.setType(null);

        // Create the Traduction, which fails.
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        restTraductionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traductionDTO)))
            .andExpect(status().isBadRequest());

        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTraductions() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        // Get all the traductionList
        restTraductionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].contenuTexte").value(hasItem(DEFAULT_CONTENU_TEXTE)))
            .andExpect(jsonPath("$.[*].contenuAudioContentType").value(hasItem(DEFAULT_CONTENU_AUDIO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contenuAudio").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENU_AUDIO))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    void getTraduction() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        // Get the traduction
        restTraductionMockMvc
            .perform(get(ENTITY_API_URL_ID, traduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traduction.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.contenuTexte").value(DEFAULT_CONTENU_TEXTE))
            .andExpect(jsonPath("$.contenuAudioContentType").value(DEFAULT_CONTENU_AUDIO_CONTENT_TYPE))
            .andExpect(jsonPath("$.contenuAudio").value(Base64Utils.encodeToString(DEFAULT_CONTENU_AUDIO)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTraduction() throws Exception {
        // Get the traduction
        restTraductionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraduction() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();

        // Update the traduction
        Traduction updatedTraduction = traductionRepository.findById(traduction.getId()).get();
        // Disconnect from session so that the updates on updatedTraduction are not directly saved in db
        em.detach(updatedTraduction);
        updatedTraduction
            .libelle(UPDATED_LIBELLE)
            .contenuTexte(UPDATED_CONTENU_TEXTE)
            .contenuAudio(UPDATED_CONTENU_AUDIO)
            .contenuAudioContentType(UPDATED_CONTENU_AUDIO_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .note(UPDATED_NOTE)
            .etat(UPDATED_ETAT);
        TraductionDTO traductionDTO = traductionMapper.toDto(updatedTraduction);

        restTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traductionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
        Traduction testTraduction = traductionList.get(traductionList.size() - 1);
        assertThat(testTraduction.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTraduction.getContenuTexte()).isEqualTo(UPDATED_CONTENU_TEXTE);
        assertThat(testTraduction.getContenuAudio()).isEqualTo(UPDATED_CONTENU_AUDIO);
        assertThat(testTraduction.getContenuAudioContentType()).isEqualTo(UPDATED_CONTENU_AUDIO_CONTENT_TYPE);
        assertThat(testTraduction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTraduction.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void putNonExistingTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, traductionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(traductionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTraductionWithPatch() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();

        // Update the traduction using partial update
        Traduction partialUpdatedTraduction = new Traduction();
        partialUpdatedTraduction.setId(traduction.getId());

        partialUpdatedTraduction
            .libelle(UPDATED_LIBELLE)
            .contenuAudio(UPDATED_CONTENU_AUDIO)
            .contenuAudioContentType(UPDATED_CONTENU_AUDIO_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .etat(UPDATED_ETAT);

        restTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraduction))
            )
            .andExpect(status().isOk());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
        Traduction testTraduction = traductionList.get(traductionList.size() - 1);
        assertThat(testTraduction.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTraduction.getContenuTexte()).isEqualTo(DEFAULT_CONTENU_TEXTE);
        assertThat(testTraduction.getContenuAudio()).isEqualTo(UPDATED_CONTENU_AUDIO);
        assertThat(testTraduction.getContenuAudioContentType()).isEqualTo(UPDATED_CONTENU_AUDIO_CONTENT_TYPE);
        assertThat(testTraduction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTraduction.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void fullUpdateTraductionWithPatch() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();

        // Update the traduction using partial update
        Traduction partialUpdatedTraduction = new Traduction();
        partialUpdatedTraduction.setId(traduction.getId());

        partialUpdatedTraduction
            .libelle(UPDATED_LIBELLE)
            .contenuTexte(UPDATED_CONTENU_TEXTE)
            .contenuAudio(UPDATED_CONTENU_AUDIO)
            .contenuAudioContentType(UPDATED_CONTENU_AUDIO_CONTENT_TYPE)
            .type(UPDATED_TYPE)
            .note(UPDATED_NOTE)
            .etat(UPDATED_ETAT);

        restTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraduction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraduction))
            )
            .andExpect(status().isOk());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
        Traduction testTraduction = traductionList.get(traductionList.size() - 1);
        assertThat(testTraduction.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTraduction.getContenuTexte()).isEqualTo(UPDATED_CONTENU_TEXTE);
        assertThat(testTraduction.getContenuAudio()).isEqualTo(UPDATED_CONTENU_AUDIO);
        assertThat(testTraduction.getContenuAudioContentType()).isEqualTo(UPDATED_CONTENU_AUDIO_CONTENT_TYPE);
        assertThat(testTraduction.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTraduction.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTraduction.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    @Transactional
    void patchNonExistingTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, traductionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraduction() throws Exception {
        int databaseSizeBeforeUpdate = traductionRepository.findAll().size();
        traduction.setId(count.incrementAndGet());

        // Create the Traduction
        TraductionDTO traductionDTO = traductionMapper.toDto(traduction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTraductionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(traductionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traduction in the database
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraduction() throws Exception {
        // Initialize the database
        traductionRepository.saveAndFlush(traduction);

        int databaseSizeBeforeDelete = traductionRepository.findAll().size();

        // Delete the traduction
        restTraductionMockMvc
            .perform(delete(ENTITY_API_URL_ID, traduction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Traduction> traductionList = traductionRepository.findAll();
        assertThat(traductionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
