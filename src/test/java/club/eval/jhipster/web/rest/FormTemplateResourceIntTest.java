package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplate;
import club.eval.jhipster.repository.FormTemplateRepository;
import club.eval.jhipster.service.FormTemplateService;
import club.eval.jhipster.repository.search.FormTemplateSearchRepository;
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
 * Test class for the FormTemplateResource REST controller.
 *
 * @see FormTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateResourceIntTest {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_FORM_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    private FormTemplateService formTemplateService;

    @Autowired
    private FormTemplateSearchRepository formTemplateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateMockMvc;

    private FormTemplate formTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateResource formTemplateResource = new FormTemplateResource(formTemplateService);
        this.restFormTemplateMockMvc = MockMvcBuilders.standaloneSetup(formTemplateResource)
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
    public static FormTemplate createEntity(EntityManager em) {
        FormTemplate formTemplate = new FormTemplate()
            .parentId(DEFAULT_PARENT_ID)
            .formId(DEFAULT_FORM_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return formTemplate;
    }

    @Before
    public void initTest() {
        formTemplateSearchRepository.deleteAll();
        formTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplate() throws Exception {
        int databaseSizeBeforeCreate = formTemplateRepository.findAll().size();

        // Create the FormTemplate
        restFormTemplateMockMvc.perform(post("/api/form-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplate)))
            .andExpect(status().isCreated());

        // Validate the FormTemplate in the database
        List<FormTemplate> formTemplateList = formTemplateRepository.findAll();
        assertThat(formTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplate testFormTemplate = formTemplateList.get(formTemplateList.size() - 1);
        assertThat(testFormTemplate.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testFormTemplate.getFormId()).isEqualTo(DEFAULT_FORM_ID);
        assertThat(testFormTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FormTemplate in Elasticsearch
        FormTemplate formTemplateEs = formTemplateSearchRepository.findOne(testFormTemplate.getId());
        assertThat(formTemplateEs).isEqualToComparingFieldByField(testFormTemplate);
    }

    @Test
    @Transactional
    public void createFormTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateRepository.findAll().size();

        // Create the FormTemplate with an existing ID
        formTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateMockMvc.perform(post("/api/form-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplate> formTemplateList = formTemplateRepository.findAll();
        assertThat(formTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormTemplates() throws Exception {
        // Initialize the database
        formTemplateRepository.saveAndFlush(formTemplate);

        // Get all the formTemplateList
        restFormTemplateMockMvc.perform(get("/api/form-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplate() throws Exception {
        // Initialize the database
        formTemplateRepository.saveAndFlush(formTemplate);

        // Get the formTemplate
        restFormTemplateMockMvc.perform(get("/api/form-templates/{id}", formTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplate.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.formId").value(DEFAULT_FORM_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplate() throws Exception {
        // Get the formTemplate
        restFormTemplateMockMvc.perform(get("/api/form-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplate() throws Exception {
        // Initialize the database
        formTemplateService.save(formTemplate);

        int databaseSizeBeforeUpdate = formTemplateRepository.findAll().size();

        // Update the formTemplate
        FormTemplate updatedFormTemplate = formTemplateRepository.findOne(formTemplate.getId());
        updatedFormTemplate
            .parentId(UPDATED_PARENT_ID)
            .formId(UPDATED_FORM_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFormTemplateMockMvc.perform(put("/api/form-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplate)))
            .andExpect(status().isOk());

        // Validate the FormTemplate in the database
        List<FormTemplate> formTemplateList = formTemplateRepository.findAll();
        assertThat(formTemplateList).hasSize(databaseSizeBeforeUpdate);
        FormTemplate testFormTemplate = formTemplateList.get(formTemplateList.size() - 1);
        assertThat(testFormTemplate.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testFormTemplate.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testFormTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FormTemplate in Elasticsearch
        FormTemplate formTemplateEs = formTemplateSearchRepository.findOne(testFormTemplate.getId());
        assertThat(formTemplateEs).isEqualToComparingFieldByField(testFormTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplate() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateRepository.findAll().size();

        // Create the FormTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateMockMvc.perform(put("/api/form-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplate)))
            .andExpect(status().isCreated());

        // Validate the FormTemplate in the database
        List<FormTemplate> formTemplateList = formTemplateRepository.findAll();
        assertThat(formTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplate() throws Exception {
        // Initialize the database
        formTemplateService.save(formTemplate);

        int databaseSizeBeforeDelete = formTemplateRepository.findAll().size();

        // Get the formTemplate
        restFormTemplateMockMvc.perform(delete("/api/form-templates/{id}", formTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateExistsInEs = formTemplateSearchRepository.exists(formTemplate.getId());
        assertThat(formTemplateExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplate> formTemplateList = formTemplateRepository.findAll();
        assertThat(formTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplate() throws Exception {
        // Initialize the database
        formTemplateService.save(formTemplate);

        // Search the formTemplate
        restFormTemplateMockMvc.perform(get("/api/_search/form-templates?query=id:" + formTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplate.class);
        FormTemplate formTemplate1 = new FormTemplate();
        formTemplate1.setId(1L);
        FormTemplate formTemplate2 = new FormTemplate();
        formTemplate2.setId(formTemplate1.getId());
        assertThat(formTemplate1).isEqualTo(formTemplate2);
        formTemplate2.setId(2L);
        assertThat(formTemplate1).isNotEqualTo(formTemplate2);
        formTemplate1.setId(null);
        assertThat(formTemplate1).isNotEqualTo(formTemplate2);
    }
}
