package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplateFieldArray;
import club.eval.jhipster.repository.FormTemplateFieldArrayRepository;
import club.eval.jhipster.service.FormTemplateFieldArrayService;
import club.eval.jhipster.repository.search.FormTemplateFieldArraySearchRepository;
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
 * Test class for the FormTemplateFieldArrayResource REST controller.
 *
 * @see FormTemplateFieldArrayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateFieldArrayResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FormTemplateFieldArrayRepository formTemplateFieldArrayRepository;

    @Autowired
    private FormTemplateFieldArrayService formTemplateFieldArrayService;

    @Autowired
    private FormTemplateFieldArraySearchRepository formTemplateFieldArraySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateFieldArrayMockMvc;

    private FormTemplateFieldArray formTemplateFieldArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateFieldArrayResource formTemplateFieldArrayResource = new FormTemplateFieldArrayResource(formTemplateFieldArrayService);
        this.restFormTemplateFieldArrayMockMvc = MockMvcBuilders.standaloneSetup(formTemplateFieldArrayResource)
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
    public static FormTemplateFieldArray createEntity(EntityManager em) {
        FormTemplateFieldArray formTemplateFieldArray = new FormTemplateFieldArray()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return formTemplateFieldArray;
    }

    @Before
    public void initTest() {
        formTemplateFieldArraySearchRepository.deleteAll();
        formTemplateFieldArray = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldArray() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldArrayRepository.findAll().size();

        // Create the FormTemplateFieldArray
        restFormTemplateFieldArrayMockMvc.perform(post("/api/form-template-field-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldArray)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldArray in the database
        List<FormTemplateFieldArray> formTemplateFieldArrayList = formTemplateFieldArrayRepository.findAll();
        assertThat(formTemplateFieldArrayList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplateFieldArray testFormTemplateFieldArray = formTemplateFieldArrayList.get(formTemplateFieldArrayList.size() - 1);
        assertThat(testFormTemplateFieldArray.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormTemplateFieldArray.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FormTemplateFieldArray in Elasticsearch
        FormTemplateFieldArray formTemplateFieldArrayEs = formTemplateFieldArraySearchRepository.findOne(testFormTemplateFieldArray.getId());
        assertThat(formTemplateFieldArrayEs).isEqualToComparingFieldByField(testFormTemplateFieldArray);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldArrayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldArrayRepository.findAll().size();

        // Create the FormTemplateFieldArray with an existing ID
        formTemplateFieldArray.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateFieldArrayMockMvc.perform(post("/api/form-template-field-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldArray)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplateFieldArray> formTemplateFieldArrayList = formTemplateFieldArrayRepository.findAll();
        assertThat(formTemplateFieldArrayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormTemplateFieldArrays() throws Exception {
        // Initialize the database
        formTemplateFieldArrayRepository.saveAndFlush(formTemplateFieldArray);

        // Get all the formTemplateFieldArrayList
        restFormTemplateFieldArrayMockMvc.perform(get("/api/form-template-field-arrays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldArray.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplateFieldArray() throws Exception {
        // Initialize the database
        formTemplateFieldArrayRepository.saveAndFlush(formTemplateFieldArray);

        // Get the formTemplateFieldArray
        restFormTemplateFieldArrayMockMvc.perform(get("/api/form-template-field-arrays/{id}", formTemplateFieldArray.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplateFieldArray.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplateFieldArray() throws Exception {
        // Get the formTemplateFieldArray
        restFormTemplateFieldArrayMockMvc.perform(get("/api/form-template-field-arrays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplateFieldArray() throws Exception {
        // Initialize the database
        formTemplateFieldArrayService.save(formTemplateFieldArray);

        int databaseSizeBeforeUpdate = formTemplateFieldArrayRepository.findAll().size();

        // Update the formTemplateFieldArray
        FormTemplateFieldArray updatedFormTemplateFieldArray = formTemplateFieldArrayRepository.findOne(formTemplateFieldArray.getId());
        updatedFormTemplateFieldArray
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFormTemplateFieldArrayMockMvc.perform(put("/api/form-template-field-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplateFieldArray)))
            .andExpect(status().isOk());

        // Validate the FormTemplateFieldArray in the database
        List<FormTemplateFieldArray> formTemplateFieldArrayList = formTemplateFieldArrayRepository.findAll();
        assertThat(formTemplateFieldArrayList).hasSize(databaseSizeBeforeUpdate);
        FormTemplateFieldArray testFormTemplateFieldArray = formTemplateFieldArrayList.get(formTemplateFieldArrayList.size() - 1);
        assertThat(testFormTemplateFieldArray.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormTemplateFieldArray.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FormTemplateFieldArray in Elasticsearch
        FormTemplateFieldArray formTemplateFieldArrayEs = formTemplateFieldArraySearchRepository.findOne(testFormTemplateFieldArray.getId());
        assertThat(formTemplateFieldArrayEs).isEqualToComparingFieldByField(testFormTemplateFieldArray);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplateFieldArray() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateFieldArrayRepository.findAll().size();

        // Create the FormTemplateFieldArray

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateFieldArrayMockMvc.perform(put("/api/form-template-field-arrays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldArray)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldArray in the database
        List<FormTemplateFieldArray> formTemplateFieldArrayList = formTemplateFieldArrayRepository.findAll();
        assertThat(formTemplateFieldArrayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplateFieldArray() throws Exception {
        // Initialize the database
        formTemplateFieldArrayService.save(formTemplateFieldArray);

        int databaseSizeBeforeDelete = formTemplateFieldArrayRepository.findAll().size();

        // Get the formTemplateFieldArray
        restFormTemplateFieldArrayMockMvc.perform(delete("/api/form-template-field-arrays/{id}", formTemplateFieldArray.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateFieldArrayExistsInEs = formTemplateFieldArraySearchRepository.exists(formTemplateFieldArray.getId());
        assertThat(formTemplateFieldArrayExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplateFieldArray> formTemplateFieldArrayList = formTemplateFieldArrayRepository.findAll();
        assertThat(formTemplateFieldArrayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplateFieldArray() throws Exception {
        // Initialize the database
        formTemplateFieldArrayService.save(formTemplateFieldArray);

        // Search the formTemplateFieldArray
        restFormTemplateFieldArrayMockMvc.perform(get("/api/_search/form-template-field-arrays?query=id:" + formTemplateFieldArray.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldArray.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplateFieldArray.class);
        FormTemplateFieldArray formTemplateFieldArray1 = new FormTemplateFieldArray();
        formTemplateFieldArray1.setId(1L);
        FormTemplateFieldArray formTemplateFieldArray2 = new FormTemplateFieldArray();
        formTemplateFieldArray2.setId(formTemplateFieldArray1.getId());
        assertThat(formTemplateFieldArray1).isEqualTo(formTemplateFieldArray2);
        formTemplateFieldArray2.setId(2L);
        assertThat(formTemplateFieldArray1).isNotEqualTo(formTemplateFieldArray2);
        formTemplateFieldArray1.setId(null);
        assertThat(formTemplateFieldArray1).isNotEqualTo(formTemplateFieldArray2);
    }
}
