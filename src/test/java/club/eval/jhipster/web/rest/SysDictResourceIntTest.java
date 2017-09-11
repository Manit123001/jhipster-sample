package club.eval.jhipster.web.rest;

import club.eval.jhipster.JhipsterSampleApp;

import club.eval.jhipster.config.SecurityBeanOverrideConfiguration;

import club.eval.jhipster.domain.SysDict;
import club.eval.jhipster.repository.SysDictRepository;
import club.eval.jhipster.service.SysDictService;
import club.eval.jhipster.repository.search.SysDictSearchRepository;
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
 * Test class for the SysDictResource REST controller.
 *
 * @see SysDictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApp.class)
public class SysDictResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CN = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SysDictRepository sysDictRepository;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysDictSearchRepository sysDictSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSysDictMockMvc;

    private SysDict sysDict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysDictResource sysDictResource = new SysDictResource(sysDictService);
        this.restSysDictMockMvc = MockMvcBuilders.standaloneSetup(sysDictResource)
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
    public static SysDict createEntity(EntityManager em) {
        SysDict sysDict = new SysDict()
            .type(DEFAULT_TYPE)
            .typeCN(DEFAULT_TYPE_CN)
            .code(DEFAULT_CODE)
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return sysDict;
    }

    @Before
    public void initTest() {
        sysDictSearchRepository.deleteAll();
        sysDict = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysDict() throws Exception {
        int databaseSizeBeforeCreate = sysDictRepository.findAll().size();

        // Create the SysDict
        restSysDictMockMvc.perform(post("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isCreated());

        // Validate the SysDict in the database
        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeCreate + 1);
        SysDict testSysDict = sysDictList.get(sysDictList.size() - 1);
        assertThat(testSysDict.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSysDict.getTypeCN()).isEqualTo(DEFAULT_TYPE_CN);
        assertThat(testSysDict.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSysDict.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSysDict.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the SysDict in Elasticsearch
        SysDict sysDictEs = sysDictSearchRepository.findOne(testSysDict.getId());
        assertThat(sysDictEs).isEqualToComparingFieldByField(testSysDict);
    }

    @Test
    @Transactional
    public void createSysDictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysDictRepository.findAll().size();

        // Create the SysDict with an existing ID
        sysDict.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysDictMockMvc.perform(post("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDictRepository.findAll().size();
        // set the field null
        sysDict.setType(null);

        // Create the SysDict, which fails.

        restSysDictMockMvc.perform(post("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isBadRequest());

        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeCNIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDictRepository.findAll().size();
        // set the field null
        sysDict.setTypeCN(null);

        // Create the SysDict, which fails.

        restSysDictMockMvc.perform(post("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isBadRequest());

        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = sysDictRepository.findAll().size();
        // set the field null
        sysDict.setCode(null);

        // Create the SysDict, which fails.

        restSysDictMockMvc.perform(post("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isBadRequest());

        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSysDicts() throws Exception {
        // Initialize the database
        sysDictRepository.saveAndFlush(sysDict);

        // Get all the sysDictList
        restSysDictMockMvc.perform(get("/api/sys-dicts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDict.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeCN").value(hasItem(DEFAULT_TYPE_CN.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSysDict() throws Exception {
        // Initialize the database
        sysDictRepository.saveAndFlush(sysDict);

        // Get the sysDict
        restSysDictMockMvc.perform(get("/api/sys-dicts/{id}", sysDict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysDict.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.typeCN").value(DEFAULT_TYPE_CN.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysDict() throws Exception {
        // Get the sysDict
        restSysDictMockMvc.perform(get("/api/sys-dicts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysDict() throws Exception {
        // Initialize the database
        sysDictService.save(sysDict);

        int databaseSizeBeforeUpdate = sysDictRepository.findAll().size();

        // Update the sysDict
        SysDict updatedSysDict = sysDictRepository.findOne(sysDict.getId());
        updatedSysDict
            .type(UPDATED_TYPE)
            .typeCN(UPDATED_TYPE_CN)
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION);

        restSysDictMockMvc.perform(put("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysDict)))
            .andExpect(status().isOk());

        // Validate the SysDict in the database
        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeUpdate);
        SysDict testSysDict = sysDictList.get(sysDictList.size() - 1);
        assertThat(testSysDict.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysDict.getTypeCN()).isEqualTo(UPDATED_TYPE_CN);
        assertThat(testSysDict.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSysDict.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSysDict.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the SysDict in Elasticsearch
        SysDict sysDictEs = sysDictSearchRepository.findOne(testSysDict.getId());
        assertThat(sysDictEs).isEqualToComparingFieldByField(testSysDict);
    }

    @Test
    @Transactional
    public void updateNonExistingSysDict() throws Exception {
        int databaseSizeBeforeUpdate = sysDictRepository.findAll().size();

        // Create the SysDict

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSysDictMockMvc.perform(put("/api/sys-dicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysDict)))
            .andExpect(status().isCreated());

        // Validate the SysDict in the database
        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSysDict() throws Exception {
        // Initialize the database
        sysDictService.save(sysDict);

        int databaseSizeBeforeDelete = sysDictRepository.findAll().size();

        // Get the sysDict
        restSysDictMockMvc.perform(delete("/api/sys-dicts/{id}", sysDict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sysDictExistsInEs = sysDictSearchRepository.exists(sysDict.getId());
        assertThat(sysDictExistsInEs).isFalse();

        // Validate the database is empty
        List<SysDict> sysDictList = sysDictRepository.findAll();
        assertThat(sysDictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSysDict() throws Exception {
        // Initialize the database
        sysDictService.save(sysDict);

        // Search the sysDict
        restSysDictMockMvc.perform(get("/api/_search/sys-dicts?query=id:" + sysDict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDict.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].typeCN").value(hasItem(DEFAULT_TYPE_CN.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDict.class);
        SysDict sysDict1 = new SysDict();
        sysDict1.setId(1L);
        SysDict sysDict2 = new SysDict();
        sysDict2.setId(sysDict1.getId());
        assertThat(sysDict1).isEqualTo(sysDict2);
        sysDict2.setId(2L);
        assertThat(sysDict1).isNotEqualTo(sysDict2);
        sysDict1.setId(null);
        assertThat(sysDict1).isNotEqualTo(sysDict2);
    }
}
