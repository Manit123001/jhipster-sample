package club.eval.jhipster.service.impl;

import club.eval.jhipster.service.FormTemplateFieldGroupService;
import club.eval.jhipster.domain.FormTemplateFieldGroup;
import club.eval.jhipster.repository.FormTemplateFieldGroupRepository;
import club.eval.jhipster.repository.search.FormTemplateFieldGroupSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FormTemplateFieldGroup.
 */
@Service
@Transactional
public class FormTemplateFieldGroupServiceImpl implements FormTemplateFieldGroupService{

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldGroupServiceImpl.class);

    private final FormTemplateFieldGroupRepository formTemplateFieldGroupRepository;

    private final FormTemplateFieldGroupSearchRepository formTemplateFieldGroupSearchRepository;
    public FormTemplateFieldGroupServiceImpl(FormTemplateFieldGroupRepository formTemplateFieldGroupRepository, FormTemplateFieldGroupSearchRepository formTemplateFieldGroupSearchRepository) {
        this.formTemplateFieldGroupRepository = formTemplateFieldGroupRepository;
        this.formTemplateFieldGroupSearchRepository = formTemplateFieldGroupSearchRepository;
    }

    /**
     * Save a formTemplateFieldGroup.
     *
     * @param formTemplateFieldGroup the entity to save
     * @return the persisted entity
     */
    @Override
    public FormTemplateFieldGroup save(FormTemplateFieldGroup formTemplateFieldGroup) {
        log.debug("Request to save FormTemplateFieldGroup : {}", formTemplateFieldGroup);
        FormTemplateFieldGroup result = formTemplateFieldGroupRepository.save(formTemplateFieldGroup);
        formTemplateFieldGroupSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the formTemplateFieldGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldGroup> findAll(Pageable pageable) {
        log.debug("Request to get all FormTemplateFieldGroups");
        return formTemplateFieldGroupRepository.findAll(pageable);
    }

    /**
     *  Get one formTemplateFieldGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FormTemplateFieldGroup findOne(Long id) {
        log.debug("Request to get FormTemplateFieldGroup : {}", id);
        return formTemplateFieldGroupRepository.findOne(id);
    }

    /**
     *  Delete the  formTemplateFieldGroup by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormTemplateFieldGroup : {}", id);
        formTemplateFieldGroupRepository.delete(id);
        formTemplateFieldGroupSearchRepository.delete(id);
    }

    /**
     * Search for the formTemplateFieldGroup corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FormTemplateFieldGroup> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FormTemplateFieldGroups for query {}", query);
        Page<FormTemplateFieldGroup> result = formTemplateFieldGroupSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
