package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplateFieldOptions;
import club.eval.jhipster.repository.FormTemplateFieldOptionsRepository;
import club.eval.jhipster.service.FormTemplateFieldOptionsService;
import club.eval.jhipster.repository.search.FormTemplateFieldOptionsSearchRepository;
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
 * Test class for the FormTemplateFieldOptionsResource REST controller.
 *
 * @see FormTemplateFieldOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateFieldOptionsResourceIntTest {

    private static final Long DEFAULT_FIELD_ID = 1L;
    private static final Long UPDATED_FIELD_ID = 2L;

    private static final String DEFAULT_OPTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_CODE = "BBBBBBBBBB";

    @Autowired
    private FormTemplateFieldOptionsRepository formTemplateFieldOptionsRepository;

    @Autowired
    private FormTemplateFieldOptionsService formTemplateFieldOptionsService;

    @Autowired
    private FormTemplateFieldOptionsSearchRepository formTemplateFieldOptionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateFieldOptionsMockMvc;

    private FormTemplateFieldOptions formTemplateFieldOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateFieldOptionsResource formTemplateFieldOptionsResource = new FormTemplateFieldOptionsResource(formTemplateFieldOptionsService);
        this.restFormTemplateFieldOptionsMockMvc = MockMvcBuilders.standaloneSetup(formTemplateFieldOptionsResource)
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
    public static FormTemplateFieldOptions createEntity(EntityManager em) {
        FormTemplateFieldOptions formTemplateFieldOptions = new FormTemplateFieldOptions()
            .fieldId(DEFAULT_FIELD_ID)
            .optionCode(DEFAULT_OPTION_CODE);
        return formTemplateFieldOptions;
    }

    @Before
    public void initTest() {
        formTemplateFieldOptionsSearchRepository.deleteAll();
        formTemplateFieldOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldOptions() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldOptionsRepository.findAll().size();

        // Create the FormTemplateFieldOptions
        restFormTemplateFieldOptionsMockMvc.perform(post("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldOptions)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldOptions in the database
        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplateFieldOptions testFormTemplateFieldOptions = formTemplateFieldOptionsList.get(formTemplateFieldOptionsList.size() - 1);
        assertThat(testFormTemplateFieldOptions.getFieldId()).isEqualTo(DEFAULT_FIELD_ID);
        assertThat(testFormTemplateFieldOptions.getOptionCode()).isEqualTo(DEFAULT_OPTION_CODE);

        // Validate the FormTemplateFieldOptions in Elasticsearch
        FormTemplateFieldOptions formTemplateFieldOptionsEs = formTemplateFieldOptionsSearchRepository.findOne(testFormTemplateFieldOptions.getId());
        assertThat(formTemplateFieldOptionsEs).isEqualToComparingFieldByField(testFormTemplateFieldOptions);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldOptionsRepository.findAll().size();

        // Create the FormTemplateFieldOptions with an existing ID
        formTemplateFieldOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateFieldOptionsMockMvc.perform(post("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldOptions)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFieldIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldOptionsRepository.findAll().size();
        // set the field null
        formTemplateFieldOptions.setFieldId(null);

        // Create the FormTemplateFieldOptions, which fails.

        restFormTemplateFieldOptionsMockMvc.perform(post("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldOptions)))
            .andExpect(status().isBadRequest());

        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOptionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldOptionsRepository.findAll().size();
        // set the field null
        formTemplateFieldOptions.setOptionCode(null);

        // Create the FormTemplateFieldOptions, which fails.

        restFormTemplateFieldOptionsMockMvc.perform(post("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldOptions)))
            .andExpect(status().isBadRequest());

        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormTemplateFieldOptions() throws Exception {
        // Initialize the database
        formTemplateFieldOptionsRepository.saveAndFlush(formTemplateFieldOptions);

        // Get all the formTemplateFieldOptionsList
        restFormTemplateFieldOptionsMockMvc.perform(get("/api/form-template-field-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(DEFAULT_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionCode").value(hasItem(DEFAULT_OPTION_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplateFieldOptions() throws Exception {
        // Initialize the database
        formTemplateFieldOptionsRepository.saveAndFlush(formTemplateFieldOptions);

        // Get the formTemplateFieldOptions
        restFormTemplateFieldOptionsMockMvc.perform(get("/api/form-template-field-options/{id}", formTemplateFieldOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplateFieldOptions.getId().intValue()))
            .andExpect(jsonPath("$.fieldId").value(DEFAULT_FIELD_ID.intValue()))
            .andExpect(jsonPath("$.optionCode").value(DEFAULT_OPTION_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplateFieldOptions() throws Exception {
        // Get the formTemplateFieldOptions
        restFormTemplateFieldOptionsMockMvc.perform(get("/api/form-template-field-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplateFieldOptions() throws Exception {
        // Initialize the database
        formTemplateFieldOptionsService.save(formTemplateFieldOptions);

        int databaseSizeBeforeUpdate = formTemplateFieldOptionsRepository.findAll().size();

        // Update the formTemplateFieldOptions
        FormTemplateFieldOptions updatedFormTemplateFieldOptions = formTemplateFieldOptionsRepository.findOne(formTemplateFieldOptions.getId());
        updatedFormTemplateFieldOptions
            .fieldId(UPDATED_FIELD_ID)
            .optionCode(UPDATED_OPTION_CODE);

        restFormTemplateFieldOptionsMockMvc.perform(put("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplateFieldOptions)))
            .andExpect(status().isOk());

        // Validate the FormTemplateFieldOptions in the database
        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeUpdate);
        FormTemplateFieldOptions testFormTemplateFieldOptions = formTemplateFieldOptionsList.get(formTemplateFieldOptionsList.size() - 1);
        assertThat(testFormTemplateFieldOptions.getFieldId()).isEqualTo(UPDATED_FIELD_ID);
        assertThat(testFormTemplateFieldOptions.getOptionCode()).isEqualTo(UPDATED_OPTION_CODE);

        // Validate the FormTemplateFieldOptions in Elasticsearch
        FormTemplateFieldOptions formTemplateFieldOptionsEs = formTemplateFieldOptionsSearchRepository.findOne(testFormTemplateFieldOptions.getId());
        assertThat(formTemplateFieldOptionsEs).isEqualToComparingFieldByField(testFormTemplateFieldOptions);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplateFieldOptions() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateFieldOptionsRepository.findAll().size();

        // Create the FormTemplateFieldOptions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateFieldOptionsMockMvc.perform(put("/api/form-template-field-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldOptions)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldOptions in the database
        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplateFieldOptions() throws Exception {
        // Initialize the database
        formTemplateFieldOptionsService.save(formTemplateFieldOptions);

        int databaseSizeBeforeDelete = formTemplateFieldOptionsRepository.findAll().size();

        // Get the formTemplateFieldOptions
        restFormTemplateFieldOptionsMockMvc.perform(delete("/api/form-template-field-options/{id}", formTemplateFieldOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateFieldOptionsExistsInEs = formTemplateFieldOptionsSearchRepository.exists(formTemplateFieldOptions.getId());
        assertThat(formTemplateFieldOptionsExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplateFieldOptions> formTemplateFieldOptionsList = formTemplateFieldOptionsRepository.findAll();
        assertThat(formTemplateFieldOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplateFieldOptions() throws Exception {
        // Initialize the database
        formTemplateFieldOptionsService.save(formTemplateFieldOptions);

        // Search the formTemplateFieldOptions
        restFormTemplateFieldOptionsMockMvc.perform(get("/api/_search/form-template-field-options?query=id:" + formTemplateFieldOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(DEFAULT_FIELD_ID.intValue())))
            .andExpect(jsonPath("$.[*].optionCode").value(hasItem(DEFAULT_OPTION_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplateFieldOptions.class);
        FormTemplateFieldOptions formTemplateFieldOptions1 = new FormTemplateFieldOptions();
        formTemplateFieldOptions1.setId(1L);
        FormTemplateFieldOptions formTemplateFieldOptions2 = new FormTemplateFieldOptions();
        formTemplateFieldOptions2.setId(formTemplateFieldOptions1.getId());
        assertThat(formTemplateFieldOptions1).isEqualTo(formTemplateFieldOptions2);
        formTemplateFieldOptions2.setId(2L);
        assertThat(formTemplateFieldOptions1).isNotEqualTo(formTemplateFieldOptions2);
        formTemplateFieldOptions1.setId(null);
        assertThat(formTemplateFieldOptions1).isNotEqualTo(formTemplateFieldOptions2);
    }
}
