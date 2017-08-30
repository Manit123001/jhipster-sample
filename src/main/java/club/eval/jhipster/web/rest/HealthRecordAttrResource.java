package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.HealthRecordAttr;
import club.eval.jhipster.service.HealthRecordAttrService;
import club.eval.jhipster.web.rest.util.HeaderUtil;
import club.eval.jhipster.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HealthRecordAttr.
 */
@RestController
@RequestMapping("/api")
public class HealthRecordAttrResource {

    private final Logger log = LoggerFactory.getLogger(HealthRecordAttrResource.class);

    private static final String ENTITY_NAME = "healthRecordAttr";

    private final HealthRecordAttrService healthRecordAttrService;

    public HealthRecordAttrResource(HealthRecordAttrService healthRecordAttrService) {
        this.healthRecordAttrService = healthRecordAttrService;
    }

    /**
     * POST  /health-record-attrs : Create a new healthRecordAttr.
     *
     * @param healthRecordAttr the healthRecordAttr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new healthRecordAttr, or with status 400 (Bad Request) if the healthRecordAttr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/health-record-attrs")
    @Timed
    public ResponseEntity<HealthRecordAttr> createHealthRecordAttr(@Valid @RequestBody HealthRecordAttr healthRecordAttr) throws URISyntaxException {
        log.debug("REST request to save HealthRecordAttr : {}", healthRecordAttr);
        if (healthRecordAttr.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new healthRecordAttr cannot already have an ID")).body(null);
        }
        HealthRecordAttr result = healthRecordAttrService.save(healthRecordAttr);
        return ResponseEntity.created(new URI("/api/health-record-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /health-record-attrs : Updates an existing healthRecordAttr.
     *
     * @param healthRecordAttr the healthRecordAttr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated healthRecordAttr,
     * or with status 400 (Bad Request) if the healthRecordAttr is not valid,
     * or with status 500 (Internal Server Error) if the healthRecordAttr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/health-record-attrs")
    @Timed
    public ResponseEntity<HealthRecordAttr> updateHealthRecordAttr(@Valid @RequestBody HealthRecordAttr healthRecordAttr) throws URISyntaxException {
        log.debug("REST request to update HealthRecordAttr : {}", healthRecordAttr);
        if (healthRecordAttr.getId() == null) {
            return createHealthRecordAttr(healthRecordAttr);
        }
        HealthRecordAttr result = healthRecordAttrService.save(healthRecordAttr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, healthRecordAttr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /health-record-attrs : get all the healthRecordAttrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of healthRecordAttrs in body
     */
    @GetMapping("/health-record-attrs")
    @Timed
    public ResponseEntity<List<HealthRecordAttr>> getAllHealthRecordAttrs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HealthRecordAttrs");
        Page<HealthRecordAttr> page = healthRecordAttrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/health-record-attrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /health-record-attrs/:id : get the "id" healthRecordAttr.
     *
     * @param id the id of the healthRecordAttr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the healthRecordAttr, or with status 404 (Not Found)
     */
    @GetMapping("/health-record-attrs/{id}")
    @Timed
    public ResponseEntity<HealthRecordAttr> getHealthRecordAttr(@PathVariable Long id) {
        log.debug("REST request to get HealthRecordAttr : {}", id);
        HealthRecordAttr healthRecordAttr = healthRecordAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthRecordAttr));
    }

    /**
     * DELETE  /health-record-attrs/:id : delete the "id" healthRecordAttr.
     *
     * @param id the id of the healthRecordAttr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/health-record-attrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHealthRecordAttr(@PathVariable Long id) {
        log.debug("REST request to delete HealthRecordAttr : {}", id);
        healthRecordAttrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/health-record-attrs?query=:query : search for the healthRecordAttr corresponding
     * to the query.
     *
     * @param query the query of the healthRecordAttr search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/health-record-attrs")
    @Timed
    public ResponseEntity<List<HealthRecordAttr>> searchHealthRecordAttrs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of HealthRecordAttrs for query {}", query);
        Page<HealthRecordAttr> page = healthRecordAttrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/health-record-attrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
