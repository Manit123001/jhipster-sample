package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplateFieldArray.
 */
public interface FormTemplateFieldArrayService {

    /**
     * Save a formTemplateFieldArray.
     *
     * @param formTemplateFieldArray the entity to save
     * @return the persisted entity
     */
    FormTemplateFieldArray save(FormTemplateFieldArray formTemplateFieldArray);

    /**
     *  Get all the formTemplateFieldArrays.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldArray> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplateFieldArray.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplateFieldArray findOne(Long id);

    /**
     *  Delete the "id" formTemplateFieldArray.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplateFieldArray corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldArray> search(String query, Pageable pageable);
}
