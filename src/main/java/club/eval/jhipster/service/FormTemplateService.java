package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplate.
 */
public interface FormTemplateService {

    /**
     * Save a formTemplate.
     *
     * @param formTemplate the entity to save
     * @return the persisted entity
     */
    FormTemplate save(FormTemplate formTemplate);

    /**
     *  Get all the formTemplates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplate> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplate findOne(Long id);

    /**
     *  Delete the "id" formTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplate corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplate> search(String query, Pageable pageable);
}
