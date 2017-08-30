package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplateFieldGroup;
import club.eval.jhipster.repository.FormTemplateFieldGroupRepository;
import club.eval.jhipster.service.FormTemplateFieldGroupService;
import club.eval.jhipster.repository.search.FormTemplateFieldGroupSearchRepository;
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
 * Test class for the FormTemplateFieldGroupResource REST controller.
 *
 * @see FormTemplateFieldGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateFieldGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FormTemplateFieldGroupRepository formTemplateFieldGroupRepository;

    @Autowired
    private FormTemplateFieldGroupService formTemplateFieldGroupService;

    @Autowired
    private FormTemplateFieldGroupSearchRepository formTemplateFieldGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateFieldGroupMockMvc;

    private FormTemplateFieldGroup formTemplateFieldGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateFieldGroupResource formTemplateFieldGroupResource = new FormTemplateFieldGroupResource(formTemplateFieldGroupService);
        this.restFormTemplateFieldGroupMockMvc = MockMvcBuilders.standaloneSetup(formTemplateFieldGroupResource)
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
    public static FormTemplateFieldGroup createEntity(EntityManager em) {
        FormTemplateFieldGroup formTemplateFieldGroup = new FormTemplateFieldGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return formTemplateFieldGroup;
    }

    @Before
    public void initTest() {
        formTemplateFieldGroupSearchRepository.deleteAll();
        formTemplateFieldGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldGroup() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldGroupRepository.findAll().size();

        // Create the FormTemplateFieldGroup
        restFormTemplateFieldGroupMockMvc.perform(post("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldGroup)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldGroup in the database
        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplateFieldGroup testFormTemplateFieldGroup = formTemplateFieldGroupList.get(formTemplateFieldGroupList.size() - 1);
        assertThat(testFormTemplateFieldGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormTemplateFieldGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FormTemplateFieldGroup in Elasticsearch
        FormTemplateFieldGroup formTemplateFieldGroupEs = formTemplateFieldGroupSearchRepository.findOne(testFormTemplateFieldGroup.getId());
        assertThat(formTemplateFieldGroupEs).isEqualToComparingFieldByField(testFormTemplateFieldGroup);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldGroupRepository.findAll().size();

        // Create the FormTemplateFieldGroup with an existing ID
        formTemplateFieldGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateFieldGroupMockMvc.perform(post("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldGroup)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldGroupRepository.findAll().size();
        // set the field null
        formTemplateFieldGroup.setName(null);

        // Create the FormTemplateFieldGroup, which fails.

        restFormTemplateFieldGroupMockMvc.perform(post("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldGroup)))
            .andExpect(status().isBadRequest());

        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = formTemplateFieldGroupRepository.findAll().size();
        // set the field null
        formTemplateFieldGroup.setDescription(null);

        // Create the FormTemplateFieldGroup, which fails.

        restFormTemplateFieldGroupMockMvc.perform(post("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldGroup)))
            .andExpect(status().isBadRequest());

        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormTemplateFieldGroups() throws Exception {
        // Initialize the database
        formTemplateFieldGroupRepository.saveAndFlush(formTemplateFieldGroup);

        // Get all the formTemplateFieldGroupList
        restFormTemplateFieldGroupMockMvc.perform(get("/api/form-template-field-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplateFieldGroup() throws Exception {
        // Initialize the database
        formTemplateFieldGroupRepository.saveAndFlush(formTemplateFieldGroup);

        // Get the formTemplateFieldGroup
        restFormTemplateFieldGroupMockMvc.perform(get("/api/form-template-field-groups/{id}", formTemplateFieldGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplateFieldGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplateFieldGroup() throws Exception {
        // Get the formTemplateFieldGroup
        restFormTemplateFieldGroupMockMvc.perform(get("/api/form-template-field-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplateFieldGroup() throws Exception {
        // Initialize the database
        formTemplateFieldGroupService.save(formTemplateFieldGroup);

        int databaseSizeBeforeUpdate = formTemplateFieldGroupRepository.findAll().size();

        // Update the formTemplateFieldGroup
        FormTemplateFieldGroup updatedFormTemplateFieldGroup = formTemplateFieldGroupRepository.findOne(formTemplateFieldGroup.getId());
        updatedFormTemplateFieldGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFormTemplateFieldGroupMockMvc.perform(put("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplateFieldGroup)))
            .andExpect(status().isOk());

        // Validate the FormTemplateFieldGroup in the database
        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeUpdate);
        FormTemplateFieldGroup testFormTemplateFieldGroup = formTemplateFieldGroupList.get(formTemplateFieldGroupList.size() - 1);
        assertThat(testFormTemplateFieldGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormTemplateFieldGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FormTemplateFieldGroup in Elasticsearch
        FormTemplateFieldGroup formTemplateFieldGroupEs = formTemplateFieldGroupSearchRepository.findOne(testFormTemplateFieldGroup.getId());
        assertThat(formTemplateFieldGroupEs).isEqualToComparingFieldByField(testFormTemplateFieldGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplateFieldGroup() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateFieldGroupRepository.findAll().size();

        // Create the FormTemplateFieldGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateFieldGroupMockMvc.perform(put("/api/form-template-field-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldGroup)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldGroup in the database
        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplateFieldGroup() throws Exception {
        // Initialize the database
        formTemplateFieldGroupService.save(formTemplateFieldGroup);

        int databaseSizeBeforeDelete = formTemplateFieldGroupRepository.findAll().size();

        // Get the formTemplateFieldGroup
        restFormTemplateFieldGroupMockMvc.perform(delete("/api/form-template-field-groups/{id}", formTemplateFieldGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateFieldGroupExistsInEs = formTemplateFieldGroupSearchRepository.exists(formTemplateFieldGroup.getId());
        assertThat(formTemplateFieldGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplateFieldGroup> formTemplateFieldGroupList = formTemplateFieldGroupRepository.findAll();
        assertThat(formTemplateFieldGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplateFieldGroup() throws Exception {
        // Initialize the database
        formTemplateFieldGroupService.save(formTemplateFieldGroup);

        // Search the formTemplateFieldGroup
        restFormTemplateFieldGroupMockMvc.perform(get("/api/_search/form-template-field-groups?query=id:" + formTemplateFieldGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplateFieldGroup.class);
        FormTemplateFieldGroup formTemplateFieldGroup1 = new FormTemplateFieldGroup();
        formTemplateFieldGroup1.setId(1L);
        FormTemplateFieldGroup formTemplateFieldGroup2 = new FormTemplateFieldGroup();
        formTemplateFieldGroup2.setId(formTemplateFieldGroup1.getId());
        assertThat(formTemplateFieldGroup1).isEqualTo(formTemplateFieldGroup2);
        formTemplateFieldGroup2.setId(2L);
        assertThat(formTemplateFieldGroup1).isNotEqualTo(formTemplateFieldGroup2);
        formTemplateFieldGroup1.setId(null);
        assertThat(formTemplateFieldGroup1).isNotEqualTo(formTemplateFieldGroup2);
    }
}
