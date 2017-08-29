package club.eval.jhipster.service.impl;

import club.eval.jhipster.service.FormTemplateFieldService;
import club.eval.jhipster.domain.FormTemplateField;
import club.eval.jhipster.repository.FormTemplateFieldRepository;
import club.eval.jhipster.repository.search.FormTemplateFieldSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplateField.
 */
@Service
@Transactional
public class FormTemplateFieldServiceImpl implements FormTemplateFieldService{

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldServiceImpl.class);

    private final FormTemplateFieldRepository formTemplateFieldRepository;

    private final FormTemplateFieldSearchRepository formTemplateFieldSearchRepository;
    public FormTemplateFieldServiceImpl(FormTemplateFieldRepository formTemplateFieldRepository, FormTemplateFieldSearchRepository formTemplateFieldSearchRepository) {
        this.formTemplateFieldRepository = formTemplateFieldRepository;
        this.formTemplateFieldSearchRepository = formTemplateFieldSearchRepository;
    }

    /**
     * Save a formTemplateField.
     *
     * @param formTemplateField the entity to save
     * @return the persisted entity
     */
    @Override
    public FormTemplateField save(FormTemplateField formTemplateField) {
        log.debug("Request to save FormTemplateField : {}", formTemplateField);
        FormTemplateField result = formTemplateFieldRepository.save(formTemplateField);
        formTemplateFieldSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplateFields.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateField> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplateFields");
        return formTemplateFieldRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplateField by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormTemplateField findOne(Long id) {
        log.debug("Request to get FormTemplateField : {}", id);
        return formTemplateFieldRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplateField by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormTemplateField : {}", id);
        formTemplateFieldRepository.delete(id);
        formTemplateFieldSearchRepository.delete(id);
    }

    /**
     * Search for the formTemplateField corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateField> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplateFields for query {}", query);
        Page<FormTemplateField> result = formTemplateFieldSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
