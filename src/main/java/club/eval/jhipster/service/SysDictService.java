package club.eval.jhipster.service;

import club.eval.jhipster.domain.SysDict;
import club.eval.jhipster.repository.SysDictRepository;
import club.eval.jhipster.repository.search.SysDictSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SysDict.
 */
@Service
@Transactional
public class SysDictService {

    private final Logger log = LoggerFactory.getLogger(SysDictService.class);

    private final SysDictRepository sysDictRepository;

    private final SysDictSearchRepository sysDictSearchRepository;
    public SysDictService(SysDictRepository sysDictRepository, SysDictSearchRepository sysDictSearchRepository) {
        this.sysDictRepository = sysDictRepository;
        this.sysDictSearchRepository = sysDictSearchRepository;
    }

    /**
     * Save a sysDict.
     *
     * @param sysDict the entity to save
     * @return the persisted entity
     */
    public SysDict save(SysDict sysDict) {
        log.debug("Request to save SysDict : {}", sysDict);
        SysDict result = sysDictRepository.save(sysDict);
        sysDictSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the sysDicts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SysDict> findAll(Pageable pageable) {
        log.debug("Request to get all SysDicts");
        return sysDictRepository.findAll(pageable);
    }

    /**
     *  Get one sysDict by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SysDict findOne(Long id) {
        log.debug("Request to get SysDict : {}", id);
        return sysDictRepository.findOne(id);
    }

    /**
     *  Delete the  sysDict by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SysDict : {}", id);
        sysDictRepository.delete(id);
        sysDictSearchRepository.delete(id);
    }

    /**
     * Search for the sysDict corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SysDict> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SysDicts for query {}", query);
        Page<SysDict> result = sysDictSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
