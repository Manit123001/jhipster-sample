package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.FormTemplateFieldAttr;
import club.eval.jhipster.repository.FormTemplateFieldAttrRepository;
import club.eval.jhipster.service.FormTemplateFieldAttrService;
import club.eval.jhipster.repository.search.FormTemplateFieldAttrSearchRepository;
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
 * Test class for the FormTemplateFieldAttrResource REST controller.
 *
 * @see FormTemplateFieldAttrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class FormTemplateFieldAttrResourceIntTest {

    private static final Long DEFAULT_FIELDID = 1L;
    private static final Long UPDATED_FIELDID = 2L;

    private static final String DEFAULT_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTEVALUE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTEVALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FormTemplateFieldAttrRepository formTemplateFieldAttrRepository;

    @Autowired
    private FormTemplateFieldAttrService formTemplateFieldAttrService;

    @Autowired
    private FormTemplateFieldAttrSearchRepository formTemplateFieldAttrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormTemplateFieldAttrMockMvc;

    private FormTemplateFieldAttr formTemplateFieldAttr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormTemplateFieldAttrResource formTemplateFieldAttrResource = new FormTemplateFieldAttrResource(formTemplateFieldAttrService);
        this.restFormTemplateFieldAttrMockMvc = MockMvcBuilders.standaloneSetup(formTemplateFieldAttrResource)
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
    public static FormTemplateFieldAttr createEntity(EntityManager em) {
        FormTemplateFieldAttr formTemplateFieldAttr = new FormTemplateFieldAttr()
            .fieldid(DEFAULT_FIELDID)
            .attribute(DEFAULT_ATTRIBUTE)
            .attributevalue(DEFAULT_ATTRIBUTEVALUE)
            .description(DEFAULT_DESCRIPTION);
        return formTemplateFieldAttr;
    }

    @Before
    public void initTest() {
        formTemplateFieldAttrSearchRepository.deleteAll();
        formTemplateFieldAttr = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldAttr() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldAttrRepository.findAll().size();

        // Create the FormTemplateFieldAttr
        restFormTemplateFieldAttrMockMvc.perform(post("/api/form-template-field-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldAttr)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldAttr in the database
        List<FormTemplateFieldAttr> formTemplateFieldAttrList = formTemplateFieldAttrRepository.findAll();
        assertThat(formTemplateFieldAttrList).hasSize(databaseSizeBeforeCreate + 1);
        FormTemplateFieldAttr testFormTemplateFieldAttr = formTemplateFieldAttrList.get(formTemplateFieldAttrList.size() - 1);
        assertThat(testFormTemplateFieldAttr.getFieldid()).isEqualTo(DEFAULT_FIELDID);
        assertThat(testFormTemplateFieldAttr.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
        assertThat(testFormTemplateFieldAttr.getAttributevalue()).isEqualTo(DEFAULT_ATTRIBUTEVALUE);
        assertThat(testFormTemplateFieldAttr.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FormTemplateFieldAttr in Elasticsearch
        FormTemplateFieldAttr formTemplateFieldAttrEs = formTemplateFieldAttrSearchRepository.findOne(testFormTemplateFieldAttr.getId());
        assertThat(formTemplateFieldAttrEs).isEqualToComparingFieldByField(testFormTemplateFieldAttr);
    }

    @Test
    @Transactional
    public void createFormTemplateFieldAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formTemplateFieldAttrRepository.findAll().size();

        // Create the FormTemplateFieldAttr with an existing ID
        formTemplateFieldAttr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormTemplateFieldAttrMockMvc.perform(post("/api/form-template-field-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldAttr)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FormTemplateFieldAttr> formTemplateFieldAttrList = formTemplateFieldAttrRepository.findAll();
        assertThat(formTemplateFieldAttrList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormTemplateFieldAttrs() throws Exception {
        // Initialize the database
        formTemplateFieldAttrRepository.saveAndFlush(formTemplateFieldAttr);

        // Get all the formTemplateFieldAttrList
        restFormTemplateFieldAttrMockMvc.perform(get("/api/form-template-field-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldid").value(hasItem(DEFAULT_FIELDID.intValue())))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())))
            .andExpect(jsonPath("$.[*].attributevalue").value(hasItem(DEFAULT_ATTRIBUTEVALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFormTemplateFieldAttr() throws Exception {
        // Initialize the database
        formTemplateFieldAttrRepository.saveAndFlush(formTemplateFieldAttr);

        // Get the formTemplateFieldAttr
        restFormTemplateFieldAttrMockMvc.perform(get("/api/form-template-field-attrs/{id}", formTemplateFieldAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formTemplateFieldAttr.getId().intValue()))
            .andExpect(jsonPath("$.fieldid").value(DEFAULT_FIELDID.intValue()))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE.toString()))
            .andExpect(jsonPath("$.attributevalue").value(DEFAULT_ATTRIBUTEVALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormTemplateFieldAttr() throws Exception {
        // Get the formTemplateFieldAttr
        restFormTemplateFieldAttrMockMvc.perform(get("/api/form-template-field-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormTemplateFieldAttr() throws Exception {
        // Initialize the database
        formTemplateFieldAttrService.save(formTemplateFieldAttr);

        int databaseSizeBeforeUpdate = formTemplateFieldAttrRepository.findAll().size();

        // Update the formTemplateFieldAttr
        FormTemplateFieldAttr updatedFormTemplateFieldAttr = formTemplateFieldAttrRepository.findOne(formTemplateFieldAttr.getId());
        updatedFormTemplateFieldAttr
            .fieldid(UPDATED_FIELDID)
            .attribute(UPDATED_ATTRIBUTE)
            .attributevalue(UPDATED_ATTRIBUTEVALUE)
            .description(UPDATED_DESCRIPTION);

        restFormTemplateFieldAttrMockMvc.perform(put("/api/form-template-field-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormTemplateFieldAttr)))
            .andExpect(status().isOk());

        // Validate the FormTemplateFieldAttr in the database
        List<FormTemplateFieldAttr> formTemplateFieldAttrList = formTemplateFieldAttrRepository.findAll();
        assertThat(formTemplateFieldAttrList).hasSize(databaseSizeBeforeUpdate);
        FormTemplateFieldAttr testFormTemplateFieldAttr = formTemplateFieldAttrList.get(formTemplateFieldAttrList.size() - 1);
        assertThat(testFormTemplateFieldAttr.getFieldid()).isEqualTo(UPDATED_FIELDID);
        assertThat(testFormTemplateFieldAttr.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testFormTemplateFieldAttr.getAttributevalue()).isEqualTo(UPDATED_ATTRIBUTEVALUE);
        assertThat(testFormTemplateFieldAttr.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FormTemplateFieldAttr in Elasticsearch
        FormTemplateFieldAttr formTemplateFieldAttrEs = formTemplateFieldAttrSearchRepository.findOne(testFormTemplateFieldAttr.getId());
        assertThat(formTemplateFieldAttrEs).isEqualToComparingFieldByField(testFormTemplateFieldAttr);
    }

    @Test
    @Transactional
    public void updateNonExistingFormTemplateFieldAttr() throws Exception {
        int databaseSizeBeforeUpdate = formTemplateFieldAttrRepository.findAll().size();

        // Create the FormTemplateFieldAttr

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormTemplateFieldAttrMockMvc.perform(put("/api/form-template-field-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formTemplateFieldAttr)))
            .andExpect(status().isCreated());

        // Validate the FormTemplateFieldAttr in the database
        List<FormTemplateFieldAttr> formTemplateFieldAttrList = formTemplateFieldAttrRepository.findAll();
        assertThat(formTemplateFieldAttrList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormTemplateFieldAttr() throws Exception {
        // Initialize the database
        formTemplateFieldAttrService.save(formTemplateFieldAttr);

        int databaseSizeBeforeDelete = formTemplateFieldAttrRepository.findAll().size();

        // Get the formTemplateFieldAttr
        restFormTemplateFieldAttrMockMvc.perform(delete("/api/form-template-field-attrs/{id}", formTemplateFieldAttr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean formTemplateFieldAttrExistsInEs = formTemplateFieldAttrSearchRepository.exists(formTemplateFieldAttr.getId());
        assertThat(formTemplateFieldAttrExistsInEs).isFalse();

        // Validate the database is empty
        List<FormTemplateFieldAttr> formTemplateFieldAttrList = formTemplateFieldAttrRepository.findAll();
        assertThat(formTemplateFieldAttrList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFormTemplateFieldAttr() throws Exception {
        // Initialize the database
        formTemplateFieldAttrService.save(formTemplateFieldAttr);

        // Search the formTemplateFieldAttr
        restFormTemplateFieldAttrMockMvc.perform(get("/api/_search/form-template-field-attrs?query=id:" + formTemplateFieldAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formTemplateFieldAttr.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldid").value(hasItem(DEFAULT_FIELDID.intValue())))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())))
            .andExpect(jsonPath("$.[*].attributevalue").value(hasItem(DEFAULT_ATTRIBUTEVALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormTemplateFieldAttr.class);
        FormTemplateFieldAttr formTemplateFieldAttr1 = new FormTemplateFieldAttr();
        formTemplateFieldAttr1.setId(1L);
        FormTemplateFieldAttr formTemplateFieldAttr2 = new FormTemplateFieldAttr();
        formTemplateFieldAttr2.setId(formTemplateFieldAttr1.getId());
        assertThat(formTemplateFieldAttr1).isEqualTo(formTemplateFieldAttr2);
        formTemplateFieldAttr2.setId(2L);
        assertThat(formTemplateFieldAttr1).isNotEqualTo(formTemplateFieldAttr2);
        formTemplateFieldAttr1.setId(null);
        assertThat(formTemplateFieldAttr1).isNotEqualTo(formTemplateFieldAttr2);
    }
}
