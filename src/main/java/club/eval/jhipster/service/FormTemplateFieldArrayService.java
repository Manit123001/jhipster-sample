package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldArray;
import club.eval.jhipster.repository.FormTemplateFieldArrayRepository;
import club.eval.jhipster.repository.search.FormTemplateFieldArraySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplateFieldArray.
 */
@Service
@Transactional
public class FormTemplateFieldArrayService {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldArrayService.class);

    private final FormTemplateFieldArrayRepository formTemplateFieldArrayRepository;

    private final FormTemplateFieldArraySearchRepository formTemplateFieldArraySearchRepository;
    public FormTemplateFieldArrayService(FormTemplateFieldArrayRepository formTemplateFieldArrayRepository, FormTemplateFieldArraySearchRepository formTemplateFieldArraySearchRepository) {
        this.formTemplateFieldArrayRepository = formTemplateFieldArrayRepository;
        this.formTemplateFieldArraySearchRepository = formTemplateFieldArraySearchRepository;
    }

    /**
     * Save a formTemplateFieldArray.
     *
     * @param formTemplateFieldArray the entity to save
     * @return the persisted entity
     */
    public FormTemplateFieldArray save(FormTemplateFieldArray formTemplateFieldArray) {
        log.debug("Request to save FormTemplateFieldArray : {}", formTemplateFieldArray);
        FormTemplateFieldArray result = formTemplateFieldArrayRepository.save(formTemplateFieldArray);
        formTemplateFieldArraySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplateFieldArrays.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldArray> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplateFieldArrays");
        return formTemplateFieldArrayRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplateFieldArray by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FormTemplateFieldArray findOne(Long id) {
        log.debug("Request to get FormTemplateFieldArray : {}", id);
        return formTemplateFieldArrayRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplateFieldArray by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FormTemplateFieldArray : {}", id);
        formTemplateFieldArrayRepository.delete(id);
        formTemplateFieldArraySearchRepository.delete(id);
    }

    /**
     * Search for the formTemplateFieldArray corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldArray> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplateFieldArrays for query {}", query);
        Page<FormTemplateFieldArray> result = formTemplateFieldArraySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
