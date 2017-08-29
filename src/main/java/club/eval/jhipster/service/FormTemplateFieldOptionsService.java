package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplateFieldOptions.
 */
public interface FormTemplateFieldOptionsService {

    /**
     * Save a formTemplateFieldOptions.
     *
     * @param formTemplateFieldOptions the entity to save
     * @return the persisted entity
     */
    FormTemplateFieldOptions save(FormTemplateFieldOptions formTemplateFieldOptions);

    /**
     *  Get all the formTemplateFieldOptions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldOptions> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplateFieldOptions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplateFieldOptions findOne(Long id);

    /**
     *  Delete the "id" formTemplateFieldOptions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplateFieldOptions corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldOptions> search(String query, Pageable pageable);
}
