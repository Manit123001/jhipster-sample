package club.eval.jhipster.service;

import club.eval.jhipster.domain.HealthRecordAttr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing HealthRecordAttr.
 */
public interface HealthRecordAttrService {

    /**
     * Save a healthRecordAttr.
     *
     * @param healthRecordAttr the entity to save
     * @return the persisted entity
     */
    HealthRecordAttr save(HealthRecordAttr healthRecordAttr);

    /**
     *  Get all the healthRecordAttrs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HealthRecordAttr> findAll(Pageable pageable);

    /**
     *  Get the "id" healthRecordAttr.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HealthRecordAttr findOne(Long id);

    /**
     *  Delete the "id" healthRecordAttr.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the healthRecordAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HealthRecordAttr> search(String query, Pageable pageable);
}
