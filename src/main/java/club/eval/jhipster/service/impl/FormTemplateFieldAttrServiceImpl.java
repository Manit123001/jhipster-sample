package club.eval.jhipster.service.impl;

import club.eval.jhipster.service.FormTemplateFieldAttrService;
import club.eval.jhipster.domain.FormTemplateFieldAttr;
import club.eval.jhipster.repository.FormTemplateFieldAttrRepository;
import club.eval.jhipster.repository.search.FormTemplateFieldAttrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplateFieldAttr.
 */
@Service
@Transactional
public class FormTemplateFieldAttrServiceImpl implements FormTemplateFieldAttrService{

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldAttrServiceImpl.class);

    private final FormTemplateFieldAttrRepository formTemplateFieldAttrRepository;

    private final FormTemplateFieldAttrSearchRepository formTemplateFieldAttrSearchRepository;
    public FormTemplateFieldAttrServiceImpl(FormTemplateFieldAttrRepository formTemplateFieldAttrRepository, FormTemplateFieldAttrSearchRepository formTemplateFieldAttrSearchRepository) {
        this.formTemplateFieldAttrRepository = formTemplateFieldAttrRepository;
        this.formTemplateFieldAttrSearchRepository = formTemplateFieldAttrSearchRepository;
    }

    /**
     * Save a formTemplateFieldAttr.
     *
     * @param formTemplateFieldAttr the entity to save
     * @return the persisted entity
     */
    @Override
    public FormTemplateFieldAttr save(FormTemplateFieldAttr formTemplateFieldAttr) {
        log.debug("Request to save FormTemplateFieldAttr : {}", formTemplateFieldAttr);
        FormTemplateFieldAttr result = formTemplateFieldAttrRepository.save(formTemplateFieldAttr);
        formTemplateFieldAttrSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplateFieldAttrs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldAttr> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplateFieldAttrs");
        return formTemplateFieldAttrRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplateFieldAttr by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormTemplateFieldAttr findOne(Long id) {
        log.debug("Request to get FormTemplateFieldAttr : {}", id);
        return formTemplateFieldAttrRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplateFieldAttr by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormTemplateFieldAttr : {}", id);
        formTemplateFieldAttrRepository.delete(id);
        formTemplateFieldAttrSearchRepository.delete(id);
    }

    /**
     * Search for the formTemplateFieldAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldAttr> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplateFieldAttrs for query {}", query);
        Page<FormTemplateFieldAttr> result = formTemplateFieldAttrSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
