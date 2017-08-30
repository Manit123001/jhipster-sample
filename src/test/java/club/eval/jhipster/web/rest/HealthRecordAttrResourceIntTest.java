package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.HealthRecordAttr;
import club.eval.jhipster.repository.HealthRecordAttrRepository;
import club.eval.jhipster.service.HealthRecordAttrService;
import club.eval.jhipster.repository.search.HealthRecordAttrSearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HealthRecordAttrResource REST controller.
 *
 * @see HealthRecordAttrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class HealthRecordAttrResourceIntTest {

    private static final Long DEFAULT_TEMPLATE_ID = 1L;
    private static final Long UPDATED_TEMPLATE_ID = 2L;

    private static final Long DEFAULT_TEMPLATE_FIELD_ID = 1L;
    private static final Long UPDATED_TEMPLATE_FIELD_ID = 2L;

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EXIST_RECORD = false;
    private static final Boolean UPDATED_IS_EXIST_RECORD = true;

    private static final String DEFAULT_RECORD_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_RECORD_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_FIELD = "BBBBBBBBBB";

    @Autowired
    private HealthRecordAttrRepository healthRecordAttrRepository;

    @Autowired
    private HealthRecordAttrService healthRecordAttrService;

    @Autowired
    private HealthRecordAttrSearchRepository healthRecordAttrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHealthRecordAttrMockMvc;

    private HealthRecordAttr healthRecordAttr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HealthRecordAttrResource healthRecordAttrResource = new HealthRecordAttrResource(healthRecordAttrService);
        this.restHealthRecordAttrMockMvc = MockMvcBuilders.standaloneSetup(healthRecordAttrResource)
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
    public static HealthRecordAttr createEntity(EntityManager em) {
        HealthRecordAttr healthRecordAttr = new HealthRecordAttr()
            .templateId(DEFAULT_TEMPLATE_ID)
            .templateFieldId(DEFAULT_TEMPLATE_FIELD_ID)
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldValue(DEFAULT_FIELD_VALUE)
            .isExistRecord(DEFAULT_IS_EXIST_RECORD)
            .recordTable(DEFAULT_RECORD_TABLE)
            .recordField(DEFAULT_RECORD_FIELD);
        return healthRecordAttr;
    }

    @Before
    public void initTest() {
        healthRecordAttrSearchRepository.deleteAll();
        healthRecordAttr = createEntity(em);
    }

    @Test
    @Transactional
    public void createHealthRecordAttr() throws Exception {
        int databaseSizeBeforeCreate = healthRecordAttrRepository.findAll().size();

        // Create the HealthRecordAttr
        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isCreated());

        // Validate the HealthRecordAttr in the database
        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeCreate + 1);
        HealthRecordAttr testHealthRecordAttr = healthRecordAttrList.get(healthRecordAttrList.size() - 1);
        assertThat(testHealthRecordAttr.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testHealthRecordAttr.getTemplateFieldId()).isEqualTo(DEFAULT_TEMPLATE_FIELD_ID);
        assertThat(testHealthRecordAttr.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testHealthRecordAttr.getFieldValue()).isEqualTo(DEFAULT_FIELD_VALUE);
        assertThat(testHealthRecordAttr.isIsExistRecord()).isEqualTo(DEFAULT_IS_EXIST_RECORD);
        assertThat(testHealthRecordAttr.getRecordTable()).isEqualTo(DEFAULT_RECORD_TABLE);
        assertThat(testHealthRecordAttr.getRecordField()).isEqualTo(DEFAULT_RECORD_FIELD);

        // Validate the HealthRecordAttr in Elasticsearch
        HealthRecordAttr healthRecordAttrEs = healthRecordAttrSearchRepository.findOne(testHealthRecordAttr.getId());
        assertThat(healthRecordAttrEs).isEqualToComparingFieldByField(testHealthRecordAttr);
    }

    @Test
    @Transactional
    public void createHealthRecordAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = healthRecordAttrRepository.findAll().size();

        // Create the HealthRecordAttr with an existing ID
        healthRecordAttr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthRecordAttrRepository.findAll().size();
        // set the field null
        healthRecordAttr.setTemplateId(null);

        // Create the HealthRecordAttr, which fails.

        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isBadRequest());

        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateFieldIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthRecordAttrRepository.findAll().size();
        // set the field null
        healthRecordAttr.setTemplateFieldId(null);

        // Create the HealthRecordAttr, which fails.

        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isBadRequest());

        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthRecordAttrRepository.findAll().size();
        // set the field null
        healthRecordAttr.setFieldName(null);

        // Create the HealthRecordAttr, which fails.

        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isBadRequest());

        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsExistRecordIsRequired() throws Exception {
        int databaseSizeBeforeTest = healthRecordAttrRepository.findAll().size();
        // set the field null
        healthRecordAttr.setIsExistRecord(null);

        // Create the HealthRecordAttr, which fails.

        restHealthRecordAttrMockMvc.perform(post("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isBadRequest());

        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHealthRecordAttrs() throws Exception {
        // Initialize the database
        healthRecordAttrRepository.saveAndFlush(healthRecordAttr);

        // Get all the healthRecordAttrList
        restHealthRecordAttrMockMvc.perform(get("/api/health-record-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthRecordAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateFieldId").value(hasItem(DEFAULT_TEMPLATE_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE.toString())))
            .andExpect(jsonPath("$.[*].isExistRecord").value(hasItem(DEFAULT_IS_EXIST_RECORD.booleanValue())))
            .andExpect(jsonPath("$.[*].recordTable").value(hasItem(DEFAULT_RECORD_TABLE.toString())))
            .andExpect(jsonPath("$.[*].recordField").value(hasItem(DEFAULT_RECORD_FIELD.toString())));
    }

    @Test
    @Transactional
    public void getHealthRecordAttr() throws Exception {
        // Initialize the database
        healthRecordAttrRepository.saveAndFlush(healthRecordAttr);

        // Get the healthRecordAttr
        restHealthRecordAttrMockMvc.perform(get("/api/health-record-attrs/{id}", healthRecordAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(healthRecordAttr.getId().intValue()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.intValue()))
            .andExpect(jsonPath("$.templateFieldId").value(DEFAULT_TEMPLATE_FIELD_ID.intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldValue").value(DEFAULT_FIELD_VALUE.toString()))
            .andExpect(jsonPath("$.isExistRecord").value(DEFAULT_IS_EXIST_RECORD.booleanValue()))
            .andExpect(jsonPath("$.recordTable").value(DEFAULT_RECORD_TABLE.toString()))
            .andExpect(jsonPath("$.recordField").value(DEFAULT_RECORD_FIELD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHealthRecordAttr() throws Exception {
        // Get the healthRecordAttr
        restHealthRecordAttrMockMvc.perform(get("/api/health-record-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHealthRecordAttr() throws Exception {
        // Initialize the database
        healthRecordAttrService.save(healthRecordAttr);

        int databaseSizeBeforeUpdate = healthRecordAttrRepository.findAll().size();

        // Update the healthRecordAttr
        HealthRecordAttr updatedHealthRecordAttr = healthRecordAttrRepository.findOne(healthRecordAttr.getId());
        updatedHealthRecordAttr
            .templateId(UPDATED_TEMPLATE_ID)
            .templateFieldId(UPDATED_TEMPLATE_FIELD_ID)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldValue(UPDATED_FIELD_VALUE)
            .isExistRecord(UPDATED_IS_EXIST_RECORD)
            .recordTable(UPDATED_RECORD_TABLE)
            .recordField(UPDATED_RECORD_FIELD);

        restHealthRecordAttrMockMvc.perform(put("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHealthRecordAttr)))
            .andExpect(status().isOk());

        // Validate the HealthRecordAttr in the database
        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeUpdate);
        HealthRecordAttr testHealthRecordAttr = healthRecordAttrList.get(healthRecordAttrList.size() - 1);
        assertThat(testHealthRecordAttr.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testHealthRecordAttr.getTemplateFieldId()).isEqualTo(UPDATED_TEMPLATE_FIELD_ID);
        assertThat(testHealthRecordAttr.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testHealthRecordAttr.getFieldValue()).isEqualTo(UPDATED_FIELD_VALUE);
        assertThat(testHealthRecordAttr.isIsExistRecord()).isEqualTo(UPDATED_IS_EXIST_RECORD);
        assertThat(testHealthRecordAttr.getRecordTable()).isEqualTo(UPDATED_RECORD_TABLE);
        assertThat(testHealthRecordAttr.getRecordField()).isEqualTo(UPDATED_RECORD_FIELD);

        // Validate the HealthRecordAttr in Elasticsearch
        HealthRecordAttr healthRecordAttrEs = healthRecordAttrSearchRepository.findOne(testHealthRecordAttr.getId());
        assertThat(healthRecordAttrEs).isEqualToComparingFieldByField(testHealthRecordAttr);
    }

    @Test
    @Transactional
    public void updateNonExistingHealthRecordAttr() throws Exception {
        int databaseSizeBeforeUpdate = healthRecordAttrRepository.findAll().size();

        // Create the HealthRecordAttr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHealthRecordAttrMockMvc.perform(put("/api/health-record-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(healthRecordAttr)))
            .andExpect(status().isCreated());

        // Validate the HealthRecordAttr in the database
        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHealthRecordAttr() throws Exception {
        // Initialize the database
        healthRecordAttrService.save(healthRecordAttr);

        int databaseSizeBeforeDelete = healthRecordAttrRepository.findAll().size();

        // Get the healthRecordAttr
        restHealthRecordAttrMockMvc.perform(delete("/api/health-record-attrs/{id}", healthRecordAttr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean healthRecordAttrExistsInEs = healthRecordAttrSearchRepository.exists(healthRecordAttr.getId());
        assertThat(healthRecordAttrExistsInEs).isFalse();

        // Validate the database is empty
        List<HealthRecordAttr> healthRecordAttrList = healthRecordAttrRepository.findAll();
        assertThat(healthRecordAttrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHealthRecordAttr() throws Exception {
        // Initialize the database
        healthRecordAttrService.save(healthRecordAttr);

        // Search the healthRecordAttr
        restHealthRecordAttrMockMvc.perform(get("/api/_search/health-record-attrs?query=id:" + healthRecordAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(healthRecordAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateFieldId").value(hasItem(DEFAULT_TEMPLATE_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldValue").value(hasItem(DEFAULT_FIELD_VALUE.toString())))
            .andExpect(jsonPath("$.[*].isExistRecord").value(hasItem(DEFAULT_IS_EXIST_RECORD.booleanValue())))
            .andExpect(jsonPath("$.[*].recordTable").value(hasItem(DEFAULT_RECORD_TABLE.toString())))
            .andExpect(jsonPath("$.[*].recordField").value(hasItem(DEFAULT_RECORD_FIELD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthRecordAttr.class);
        HealthRecordAttr healthRecordAttr1 = new HealthRecordAttr();
        healthRecordAttr1.setId(1L);
        HealthRecordAttr healthRecordAttr2 = new HealthRecordAttr();
        healthRecordAttr2.setId(healthRecordAttr1.getId());
        assertThat(healthRecordAttr1).isEqualTo(healthRecordAttr2);
        healthRecordAttr2.setId(2L);
        assertThat(healthRecordAttr1).isNotEqualTo(healthRecordAttr2);
        healthRecordAttr1.setId(null);
        assertThat(healthRecordAttr1).isNotEqualTo(healthRecordAttr2);
    }
}
