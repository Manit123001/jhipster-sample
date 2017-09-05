package club.eval.jhipster.service;

import club.eval.jhipster.domain.SysDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SysDict.
 */
public interface SysDictService {

    /**
     * Save a sysDict.
     *
     * @param sysDict the entity to save
     * @return the persisted entity
     */
    SysDict save(SysDict sysDict);

    /**
     *  Get all the sysDicts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysDict> findAll(Pageable pageable);

    /**
     *  Get the "id" sysDict.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysDict findOne(Long id);

    /**
     *  Delete the "id" sysDict.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sysDict corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysDict> search(String query, Pageable pageable);
}
