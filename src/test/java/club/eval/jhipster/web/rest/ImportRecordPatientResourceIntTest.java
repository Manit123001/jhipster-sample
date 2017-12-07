package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.ImportRecordPatient;
import club.eval.jhipster.repository.ImportRecordPatientRepository;
import club.eval.jhipster.repository.search.ImportRecordPatientSearchRepository;
import club.eval.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImportRecordPatientResource REST controller.
 *
 * @see ImportRecordPatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class ImportRecordPatientResourceIntTest {

    private static final LocalDate DEFAULT_GMT_CREATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_CREATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_GMT_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GMT_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_SEND_ID = "AAAAAAAAAA";
    private static final String UPDATED_SEND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TOUSER = "AAAAAAAAAA";
    private static final String UPDATED_TOUSER = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MINIPROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_MINIPROGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_EMPHASISKEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_EMPHASISKEYWORD = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    @Autowired
    private ImportRecordPatientRepository importRecordPatientRepository;

    @Autowired
    private ImportRecordPatientSearchRepository importRecordPatientSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImportRecordPatientMockMvc;

    private ImportRecordPatient importRecordPatient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImportRecordPatientResource importRecordPatientResource = new ImportRecordPatientResource(importRecordPatientRepository, importRecordPatientSearchRepository);
        this.restImportRecordPatientMockMvc = MockMvcBuilders.standaloneSetup(importRecordPatientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImportRecordPatient createEntity(EntityManager em) {
        ImportRecordPatient importRecordPatient = new ImportRecordPatient()
            .gmtCreate(DEFAULT_GMT_CREATE)
            .gmtModified(DEFAULT_GMT_MODIFIED)
            .type(DEFAULT_TYPE)
            .sendId(DEFAULT_SEND_ID)
            .touser(DEFAULT_TOUSER)
            .templateId(DEFAULT_TEMPLATE_ID)
            .url(DEFAULT_URL)
            .miniprogram(DEFAULT_MINIPROGRAM)
            .page(DEFAULT_PAGE)
            .formId(DEFAULT_FORM_ID)
            .data(DEFAULT_DATA)
            .emphasiskeyword(DEFAULT_EMPHASISKEYWORD)
            .status(DEFAULT_STATUS)
            .result(DEFAULT_RESULT);
        return importRecordPatient;
    }

    @Before
    public void initTest() {
        importRecordPatientSearchRepository.deleteAll();
        importRecordPatient = createEntity(em);
    }

    @Test
    @Transactional
    public void createImportRecordPatient() throws Exception {
        int databaseSizeBeforeCreate = importRecordPatientRepository.findAll().size();

        // Create the ImportRecordPatient
        restImportRecordPatientMockMvc.perform(post("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isCreated());

        // Validate the ImportRecordPatient in the database
        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeCreate + 1);
        ImportRecordPatient testImportRecordPatient = importRecordPatientList.get(importRecordPatientList.size() - 1);
        assertThat(testImportRecordPatient.getGmtCreate()).isEqualTo(DEFAULT_GMT_CREATE);
        assertThat(testImportRecordPatient.getGmtModified()).isEqualTo(DEFAULT_GMT_MODIFIED);
        assertThat(testImportRecordPatient.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testImportRecordPatient.getSendId()).isEqualTo(DEFAULT_SEND_ID);
        assertThat(testImportRecordPatient.getTouser()).isEqualTo(DEFAULT_TOUSER);
        assertThat(testImportRecordPatient.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testImportRecordPatient.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testImportRecordPatient.getMiniprogram()).isEqualTo(DEFAULT_MINIPROGRAM);
        assertThat(testImportRecordPatient.getPage()).isEqualTo(DEFAULT_PAGE);
        assertThat(testImportRecordPatient.getFormId()).isEqualTo(DEFAULT_FORM_ID);
        assertThat(testImportRecordPatient.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testImportRecordPatient.getEmphasiskeyword()).isEqualTo(DEFAULT_EMPHASISKEYWORD);
        assertThat(testImportRecordPatient.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testImportRecordPatient.getResult()).isEqualTo(DEFAULT_RESULT);

        // Validate the ImportRecordPatient in Elasticsearch
        ImportRecordPatient importRecordPatientEs = importRecordPatientSearchRepository.findOne(testImportRecordPatient.getId());
        assertThat(importRecordPatientEs).isEqualToComparingFieldByField(testImportRecordPatient);
    }

    @Test
    @Transactional
    public void createImportRecordPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = importRecordPatientRepository.findAll().size();

        // Create the ImportRecordPatient with an existing ID
        importRecordPatient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImportRecordPatientMockMvc.perform(post("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGmtCreateIsRequired() throws Exception {
        int databaseSizeBeforeTest = importRecordPatientRepository.findAll().size();
        // set the field null
        importRecordPatient.setGmtCreate(null);

        // Create the ImportRecordPatient, which fails.

        restImportRecordPatientMockMvc.perform(post("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isBadRequest());

        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGmtModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = importRecordPatientRepository.findAll().size();
        // set the field null
        importRecordPatient.setGmtModified(null);

        // Create the ImportRecordPatient, which fails.

        restImportRecordPatientMockMvc.perform(post("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isBadRequest());

        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = importRecordPatientRepository.findAll().size();
        // set the field null
        importRecordPatient.setType(null);

        // Create the ImportRecordPatient, which fails.

        restImportRecordPatientMockMvc.perform(post("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isBadRequest());

        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImportRecordPatients() throws Exception {
        // Initialize the database
        importRecordPatientRepository.saveAndFlush(importRecordPatient);

        // Get all the importRecordPatientList
        restImportRecordPatientMockMvc.perform(get("/api/import-record-patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(importRecordPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].gmtCreate").value(hasItem(DEFAULT_GMT_CREATE.toString())))
            .andExpect(jsonPath("$.[*].gmtModified").value(hasItem(DEFAULT_GMT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].sendId").value(hasItem(DEFAULT_SEND_ID.toString())))
            .andExpect(jsonPath("$.[*].touser").value(hasItem(DEFAULT_TOUSER.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].miniprogram").value(hasItem(DEFAULT_MINIPROGRAM.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE.toString())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].emphasiskeyword").value(hasItem(DEFAULT_EMPHASISKEYWORD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())));
    }

    @Test
    @Transactional
    public void getImportRecordPatient() throws Exception {
        // Initialize the database
        importRecordPatientRepository.saveAndFlush(importRecordPatient);

        // Get the importRecordPatient
        restImportRecordPatientMockMvc.perform(get("/api/import-record-patients/{id}", importRecordPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(importRecordPatient.getId().intValue()))
            .andExpect(jsonPath("$.gmtCreate").value(DEFAULT_GMT_CREATE.toString()))
            .andExpect(jsonPath("$.gmtModified").value(DEFAULT_GMT_MODIFIED.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.sendId").value(DEFAULT_SEND_ID.toString()))
            .andExpect(jsonPath("$.touser").value(DEFAULT_TOUSER.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.miniprogram").value(DEFAULT_MINIPROGRAM.toString()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE.toString()))
            .andExpect(jsonPath("$.formId").value(DEFAULT_FORM_ID.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.emphasiskeyword").value(DEFAULT_EMPHASISKEYWORD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImportRecordPatient() throws Exception {
        // Get the importRecordPatient
        restImportRecordPatientMockMvc.perform(get("/api/import-record-patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImportRecordPatient() throws Exception {
        // Initialize the database
        importRecordPatientRepository.saveAndFlush(importRecordPatient);
        importRecordPatientSearchRepository.save(importRecordPatient);
        int databaseSizeBeforeUpdate = importRecordPatientRepository.findAll().size();

        // Update the importRecordPatient
        ImportRecordPatient updatedImportRecordPatient = importRecordPatientRepository.findOne(importRecordPatient.getId());
        updatedImportRecordPatient
            .gmtCreate(UPDATED_GMT_CREATE)
            .gmtModified(UPDATED_GMT_MODIFIED)
            .type(UPDATED_TYPE)
            .sendId(UPDATED_SEND_ID)
            .touser(UPDATED_TOUSER)
            .templateId(UPDATED_TEMPLATE_ID)
            .url(UPDATED_URL)
            .miniprogram(UPDATED_MINIPROGRAM)
            .page(UPDATED_PAGE)
            .formId(UPDATED_FORM_ID)
            .data(UPDATED_DATA)
            .emphasiskeyword(UPDATED_EMPHASISKEYWORD)
            .status(UPDATED_STATUS)
            .result(UPDATED_RESULT);

        restImportRecordPatientMockMvc.perform(put("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImportRecordPatient)))
            .andExpect(status().isOk());

        // Validate the ImportRecordPatient in the database
        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeUpdate);
        ImportRecordPatient testImportRecordPatient = importRecordPatientList.get(importRecordPatientList.size() - 1);
        assertThat(testImportRecordPatient.getGmtCreate()).isEqualTo(UPDATED_GMT_CREATE);
        assertThat(testImportRecordPatient.getGmtModified()).isEqualTo(UPDATED_GMT_MODIFIED);
        assertThat(testImportRecordPatient.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testImportRecordPatient.getSendId()).isEqualTo(UPDATED_SEND_ID);
        assertThat(testImportRecordPatient.getTouser()).isEqualTo(UPDATED_TOUSER);
        assertThat(testImportRecordPatient.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testImportRecordPatient.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testImportRecordPatient.getMiniprogram()).isEqualTo(UPDATED_MINIPROGRAM);
        assertThat(testImportRecordPatient.getPage()).isEqualTo(UPDATED_PAGE);
        assertThat(testImportRecordPatient.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testImportRecordPatient.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testImportRecordPatient.getEmphasiskeyword()).isEqualTo(UPDATED_EMPHASISKEYWORD);
        assertThat(testImportRecordPatient.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testImportRecordPatient.getResult()).isEqualTo(UPDATED_RESULT);

        // Validate the ImportRecordPatient in Elasticsearch
        ImportRecordPatient importRecordPatientEs = importRecordPatientSearchRepository.findOne(testImportRecordPatient.getId());
        assertThat(importRecordPatientEs).isEqualToComparingFieldByField(testImportRecordPatient);
    }

    @Test
    @Transactional
    public void updateNonExistingImportRecordPatient() throws Exception {
        int databaseSizeBeforeUpdate = importRecordPatientRepository.findAll().size();

        // Create the ImportRecordPatient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImportRecordPatientMockMvc.perform(put("/api/import-record-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(importRecordPatient)))
            .andExpect(status().isCreated());

        // Validate the ImportRecordPatient in the database
        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImportRecordPatient() throws Exception {
        // Initialize the database
        importRecordPatientRepository.saveAndFlush(importRecordPatient);
        importRecordPatientSearchRepository.save(importRecordPatient);
        int databaseSizeBeforeDelete = importRecordPatientRepository.findAll().size();

        // Get the importRecordPatient
        restImportRecordPatientMockMvc.perform(delete("/api/import-record-patients/{id}", importRecordPatient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean importRecordPatientExistsInEs = importRecordPatientSearchRepository.exists(importRecordPatient.getId());
        assertThat(importRecordPatientExistsInEs).isFalse();

        // Validate the database is empty
        List<ImportRecordPatient> importRecordPatientList = importRecordPatientRepository.findAll();
        assertThat(importRecordPatientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchImportRecordPatient() throws Exception {
        // Initialize the database
        importRecordPatientRepository.saveAndFlush(importRecordPatient);
        importRecordPatientSearchRepository.save(importRecordPatient);

        // Search the importRecordPatient
        restImportRecordPatientMockMvc.perform(get("/api/_search/import-record-patients?query=id:" + importRecordPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(importRecordPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].gmtCreate").value(hasItem(DEFAULT_GMT_CREATE.toString())))
            .andExpect(jsonPath("$.[*].gmtModified").value(hasItem(DEFAULT_GMT_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].sendId").value(hasItem(DEFAULT_SEND_ID.toString())))
            .andExpect(jsonPath("$.[*].touser").value(hasItem(DEFAULT_TOUSER.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].miniprogram").value(hasItem(DEFAULT_MINIPROGRAM.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE.toString())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].emphasiskeyword").value(hasItem(DEFAULT_EMPHASISKEYWORD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImportRecordPatient.class);
        ImportRecordPatient importRecordPatient1 = new ImportRecordPatient();
        importRecordPatient1.setId(1L);
        ImportRecordPatient importRecordPatient2 = new ImportRecordPatient();
        importRecordPatient2.setId(importRecordPatient1.getId());
        assertThat(importRecordPatient1).isEqualTo(importRecordPatient2);
        importRecordPatient2.setId(2L);
        assertThat(importRecordPatient1).isNotEqualTo(importRecordPatient2);
        importRecordPatient1.setId(null);
        assertThat(importRecordPatient1).isNotEqualTo(importRecordPatient2);
    }
}
