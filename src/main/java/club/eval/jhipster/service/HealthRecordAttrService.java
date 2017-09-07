package club.eval.jhipster.service;

import club.eval.jhipster.domain.HealthRecordAttr;
import club.eval.jhipster.repository.HealthRecordAttrRepository;
import club.eval.jhipster.repository.search.HealthRecordAttrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HealthRecordAttr.
 */
@Service
@Transactional
public class HealthRecordAttrService {

    private final Logger log = LoggerFactory.getLogger(HealthRecordAttrService.class);

    private final HealthRecordAttrRepository healthRecordAttrRepository;

    private final HealthRecordAttrSearchRepository healthRecordAttrSearchRepository;
    public HealthRecordAttrService(HealthRecordAttrRepository healthRecordAttrRepository, HealthRecordAttrSearchRepository healthRecordAttrSearchRepository) {
        this.healthRecordAttrRepository = healthRecordAttrRepository;
        this.healthRecordAttrSearchRepository = healthRecordAttrSearchRepository;
    }

    /**
     * Save a healthRecordAttr.
     *
     * @param healthRecordAttr the entity to save
     * @return the persisted entity
     */
    public HealthRecordAttr save(HealthRecordAttr healthRecordAttr) {
        log.debug("Request to save HealthRecordAttr : {}", healthRecordAttr);
        HealthRecordAttr result = healthRecordAttrRepository.save(healthRecordAttr);
        healthRecordAttrSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the healthRecordAttrs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HealthRecordAttr> findAll(Pageable pageable) {
        log.debug("Request to get all HealthRecordAttrs");
        return healthRecordAttrRepository.findAll(pageable);
    }

    /**
     *  Get one healthRecordAttr by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HealthRecordAttr findOne(Long id) {
        log.debug("Request to get HealthRecordAttr : {}", id);
        return healthRecordAttrRepository.findOne(id);
    }

    /**
     *  Delete the  healthRecordAttr by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HealthRecordAttr : {}", id);
        healthRecordAttrRepository.delete(id);
        healthRecordAttrSearchRepository.delete(id);
    }

    /**
     * Search for the healthRecordAttr corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HealthRecordAttr> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HealthRecordAttrs for query {}", query);
        Page<HealthRecordAttr> result = healthRecordAttrSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
