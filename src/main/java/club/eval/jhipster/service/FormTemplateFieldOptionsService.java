package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldOptions;
import club.eval.jhipster.repository.FormTemplateFieldOptionsRepository;
import club.eval.jhipster.repository.search.FormTemplateFieldOptionsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplateFieldOptions.
 */
@Service
@Transactional
public class FormTemplateFieldOptionsService {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldOptionsService.class);

    private final FormTemplateFieldOptionsRepository formTemplateFieldOptionsRepository;

    private final FormTemplateFieldOptionsSearchRepository formTemplateFieldOptionsSearchRepository;
    public FormTemplateFieldOptionsService(FormTemplateFieldOptionsRepository formTemplateFieldOptionsRepository, FormTemplateFieldOptionsSearchRepository formTemplateFieldOptionsSearchRepository) {
        this.formTemplateFieldOptionsRepository = formTemplateFieldOptionsRepository;
        this.formTemplateFieldOptionsSearchRepository = formTemplateFieldOptionsSearchRepository;
    }

    /**
     * Save a formTemplateFieldOptions.
     *
     * @param formTemplateFieldOptions the entity to save
     * @return the persisted entity
     */
    public FormTemplateFieldOptions save(FormTemplateFieldOptions formTemplateFieldOptions) {
        log.debug("Request to save FormTemplateFieldOptions : {}", formTemplateFieldOptions);
        FormTemplateFieldOptions result = formTemplateFieldOptionsRepository.save(formTemplateFieldOptions);
        formTemplateFieldOptionsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplateFieldOptions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldOptions> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplateFieldOptions");
        return formTemplateFieldOptionsRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplateFieldOptions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FormTemplateFieldOptions findOne(Long id) {
        log.debug("Request to get FormTemplateFieldOptions : {}", id);
        return formTemplateFieldOptionsRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplateFieldOptions by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FormTemplateFieldOptions : {}", id);
        formTemplateFieldOptionsRepository.delete(id);
        formTemplateFieldOptionsSearchRepository.delete(id);
    }

    /**
     * Search for the formTemplateFieldOptions corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldOptions> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplateFieldOptions for query {}", query);
        Page<FormTemplateFieldOptions> result = formTemplateFieldOptionsSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
