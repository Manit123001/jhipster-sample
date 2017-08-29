package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplateFieldGroup.
 */
public interface FormTemplateFieldGroupService {

    /**
     * Save a formTemplateFieldGroup.
     *
     * @param formTemplateFieldGroup the entity to save
     * @return the persisted entity
     */
    FormTemplateFieldGroup save(FormTemplateFieldGroup formTemplateFieldGroup);

    /**
     *  Get all the formTemplateFieldGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldGroup> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplateFieldGroup.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplateFieldGroup findOne(Long id);

    /**
     *  Delete the "id" formTemplateFieldGroup.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplateFieldGroup corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldGroup> search(String query, Pageable pageable);
}
