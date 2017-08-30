package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplateField;
import club.eval.jhipster.repository.FormTemplateFieldRepository;
import club.eval.jhipster.service.FormTemplateFieldService;
import club.eval.jhipster.repository.search.FormTemplateFieldSearchRepository;
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
 * Test class for the FormTemplateFieldResource REST controller.
 *
 * @see FormTemplateFieldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateFieldResourceIntTest {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_TEMPLATE_ID = 1L;
    private static final Long UPDATED_TEMPLATE_ID = 2L;

    private static final Long DEFAULT_ARRAY_ID = 1L;
    private static final Long UPDATED_ARRAY_ID = 2L;

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FormTemplateFieldRepository formTemplateFieldRepository;

    @Autowired
    private FormTemplateFieldService formTemplateFieldService;

    @Autowired
    private FormTemplateFieldSearchRepository formTemplateFieldSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateFieldMockMvc;

    private FormTemplateField formTemplateField;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateFieldResource formTemplateFieldResource = new FormTemplateFieldResource(formTemplateFieldService);
        this.restFormTemplateFieldMockMvc = MockMvcBuilders.standaloneSetup(formTemplateFieldResource)
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
    public static FormTemplateField createEntity(EntityManager em) {
        FormTemplateField formTemplateField = new FormTemplateField()
            .parentId(DEFAULT_PARENT_ID)
            .templateId(DEFAULT_TEMPLATE_ID)
            .arrayId(DEFAULT_ARRAY_ID)
            .groupId(DEFAULT_GROUP_ID)
            .name(DEFAULT_NAME)
            .typeCode(DEFAULT_TYPE_CODE)
            .description(DEFAULT_DESCRIPTION);
        return formTemplateField;
    }

    @Before
    public void initTest() {
        formTemplateFieldSearchRepository.deleteAll();
        formTemplateField = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplateField() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldRepository.findAll().size();

        // Create the FormTemplateField
        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateField in the database
        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplateField testFormTemplateField = formTemplateFieldList.get(formTemplateFieldList.size() - 1);
        assertThat(testFormTemplateField.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testFormTemplateField.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
        assertThat(testFormTemplateField.getArrayId()).isEqualTo(DEFAULT_ARRAY_ID);
        assertThat(testFormTemplateField.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testFormTemplateField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormTemplateField.getTypeCode()).isEqualTo(DEFAULT_TYPE_CODE);
        assertThat(testFormTemplateField.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FormTemplateField in Elasticsearch
        FormTemplateField formTemplateFieldEs = formTemplateFieldSearchRepository.findOne(testFormTemplateField.getId());
        assertThat(formTemplateFieldEs).isEqualToComparingFieldByField(testFormTemplateField);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldRepository.findAll().size();

        // Create the FormTemplateField with an existing ID
        formTemplateField.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkParentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setParentId(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setTemplateId(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setArrayId(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setGroupId(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setName(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setTypeCode(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldRepository.findAll().size();
        // set the field null
        formTemplateField.setDescription(null);

        // Create the FormTemplateField, which fails.

        restFormTemplateFieldMockMvc.perform(post("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isBadRequest());

        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormTemplateFields() throws Exception {
        // Initialize the database
        formTemplateFieldRepository.saveAndFlush(formTemplateField);

        // Get all the formTemplateFieldList
        restFormTemplateFieldMockMvc.perform(get("/api/form-template-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateField.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].arrayId").value(hasItem(DEFAULT_ARRAY_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplateField() throws Exception {
        // Initialize the database
        formTemplateFieldRepository.saveAndFlush(formTemplateField);

        // Get the formTemplateField
        restFormTemplateFieldMockMvc.perform(get("/api/form-template-fields/{id}", formTemplateField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplateField.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.intValue()))
            .andExpect(jsonPath("$.arrayId").value(DEFAULT_ARRAY_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.typeCode").value(DEFAULT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplateField() throws Exception {
        // Get the formTemplateField
        restFormTemplateFieldMockMvc.perform(get("/api/form-template-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplateField() throws Exception {
        // Initialize the database
        formTemplateFieldService.save(formTemplateField);

        int databaseSizeBeforeUpdate = formTemplateFieldRepository.findAll().size();

        // Update the formTemplateField
        FormTemplateField updatedFormTemplateField = formTemplateFieldRepository.findOne(formTemplateField.getId());
        updatedFormTemplateField
            .parentId(UPDATED_PARENT_ID)
            .templateId(UPDATED_TEMPLATE_ID)
            .arrayId(UPDATED_ARRAY_ID)
            .groupId(UPDATED_GROUP_ID)
            .name(UPDATED_NAME)
            .typeCode(UPDATED_TYPE_CODE)
            .description(UPDATED_DESCRIPTION);

        restFormTemplateFieldMockMvc.perform(put("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplateField)))
            .andExpect(status().isOk());

        // Validate the FormTemplateField in the database
        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeUpdate);
        FormTemplateField testFormTemplateField = formTemplateFieldList.get(formTemplateFieldList.size() - 1);
        assertThat(testFormTemplateField.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testFormTemplateField.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
        assertThat(testFormTemplateField.getArrayId()).isEqualTo(UPDATED_ARRAY_ID);
        assertThat(testFormTemplateField.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testFormTemplateField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormTemplateField.getTypeCode()).isEqualTo(UPDATED_TYPE_CODE);
        assertThat(testFormTemplateField.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FormTemplateField in Elasticsearch
        FormTemplateField formTemplateFieldEs = formTemplateFieldSearchRepository.findOne(testFormTemplateField.getId());
        assertThat(formTemplateFieldEs).isEqualToComparingFieldByField(testFormTemplateField);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplateField() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateFieldRepository.findAll().size();

        // Create the FormTemplateField

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateFieldMockMvc.perform(put("/api/form-template-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateField)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateField in the database
        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplateField() throws Exception {
        // Initialize the database
        formTemplateFieldService.save(formTemplateField);

        int databaseSizeBeforeDelete = formTemplateFieldRepository.findAll().size();

        // Get the formTemplateField
        restFormTemplateFieldMockMvc.perform(delete("/api/form-template-fields/{id}", formTemplateField.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateFieldExistsInEs = formTemplateFieldSearchRepository.exists(formTemplateField.getId());
        assertThat(formTemplateFieldExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplateField> formTemplateFieldList = formTemplateFieldRepository.findAll();
        assertThat(formTemplateFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplateField() throws Exception {
        // Initialize the database
        formTemplateFieldService.save(formTemplateField);

        // Search the formTemplateField
        restFormTemplateFieldMockMvc.perform(get("/api/_search/form-template-fields?query=id:" + formTemplateField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateField.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.intValue())))
            .andExpect(jsonPath("$.[*].arrayId").value(hasItem(DEFAULT_ARRAY_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].typeCode").value(hasItem(DEFAULT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplateField.class);
        FormTemplateField formTemplateField1 = new FormTemplateField();
        formTemplateField1.setId(1L);
        FormTemplateField formTemplateField2 = new FormTemplateField();
        formTemplateField2.setId(formTemplateField1.getId());
        assertThat(formTemplateField1).isEqualTo(formTemplateField2);
        formTemplateField2.setId(2L);
        assertThat(formTemplateField1).isNotEqualTo(formTemplateField2);
        formTemplateField1.setId(null);
        assertThat(formTemplateField1).isNotEqualTo(formTemplateField2);
    }
}
