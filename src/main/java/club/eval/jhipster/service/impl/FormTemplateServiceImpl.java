package club.eval.jhipster.service.impl;

import club.eval.jhipster.service.FormTemplateService;
import club.eval.jhipster.domain.FormTemplate;
import club.eval.jhipster.repository.FormTemplateRepository;
import club.eval.jhipster.repository.search.FormTemplateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplate.
 */
@Service
@Transactional
public class FormTemplateServiceImpl implements FormTemplateService{

    private final Logger log = LoggerFactory.getLogger(FormTemplateServiceImpl.class);

    private final FormTemplateRepository formTemplateRepository;

    private final FormTemplateSearchRepository formTemplateSearchRepository;
    public FormTemplateServiceImpl(FormTemplateRepository formTemplateRepository, FormTemplateSearchRepository formTemplateSearchRepository) {
        this.formTemplateRepository = formTemplateRepository;
        this.formTemplateSearchRepository = formTemplateSearchRepository;
    }

    /**
     * Save a formTemplate.
     *
     * @param formTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public FormTemplate save(FormTemplate formTemplate) {
        log.debug("Request to save FormTemplate : {}", formTemplate);
        FormTemplate result = formTemplateRepository.save(formTemplate);
        formTemplateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplates");
        return formTemplateRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormTemplate findOne(Long id) {
        log.debug("Request to get FormTemplate : {}", id);
        return formTemplateRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormTemplate : {}", id);
        formTemplateRepository.delete(id);
        formTemplateSearchRepository.delete(id);
    }

    /**
     * Search for the formTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplates for query {}", query);
        Page<FormTemplate> result = formTemplateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
