package club.eval.jhipster.service;

import club.eval.jhipster.domain.FormTemplateFieldAttr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FormTemplateFieldAttr.
 */
public interface FormTemplateFieldAttrService {

    /**
     * Save a formTemplateFieldAttr.
     *
     * @param formTemplateFieldAttr the entity to save
     * @return the persisted entity
     */
    FormTemplateFieldAttr save(FormTemplateFieldAttr formTemplateFieldAttr);

    /**
     *  Get all the formTemplateFieldAttrs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldAttr> findAll(Pageable pageable);

    /**
     *  Get the "id" formTemplateFieldAttr.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FormTemplateFieldAttr findOne(Long id);

    /**
     *  Delete the "id" formTemplateFieldAttr.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the formTemplateFieldAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FormTemplateFieldAttr> search(String query, Pageable pageable);
}
