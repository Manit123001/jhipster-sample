package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplateField.
 */
public interface FormTemplateFieldService {

    /**
     * Save a formTemplateField.
     *
     * @param formTemplateField the entity to save
     * @return the persisted entity
     */
    FormTemplateField save(FormTemplateField formTemplateField);

    /**
     *  Get all the formTemplateFields.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateField> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplateField.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplateField findOne(Long id);

    /**
     *  Delete the "id" formTemplateField.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplateField corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateField> search(String query, Pageable pageable);
}
