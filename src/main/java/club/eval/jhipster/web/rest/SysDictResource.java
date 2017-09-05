package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.SysDict;
import club.eval.jhipster.service.SysDictService;
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
 * REST controller for managing SysDict.
 */
@RestController
@RequestMapping("/api")
public class SysDictResource {

    private final Logger log = LoggerFactory.getLogger(SysDictResource.class);

    private static final String ENTITY_NAME = "sysDict";

    private final SysDictService sysDictService;

    public SysDictResource(SysDictService sysDictService) {
        this.sysDictService = sysDictService;
    }

    /**
     * POST  /sys-dicts : Create a new sysDict.
     *
     * @param sysDict the sysDict to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysDict, or with status 400 (Bad Request) if the sysDict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-dicts")
    @Timed
    public ResponseEntity<SysDict> createSysDict(@Valid @RequestBody SysDict sysDict) throws URISyntaxException {
        log.debug("REST request to save SysDict : {}", sysDict);
        if (sysDict.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysDict cannot already have an ID")).body(null);
        }
        SysDict result = sysDictService.save(sysDict);
        return ResponseEntity.created(new URI("/api/sys-dicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sys-dicts : Updates an existing sysDict.
     *
     * @param sysDict the sysDict to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysDict,
     * or with status 400 (Bad Request) if the sysDict is not valid,
     * or with status 500 (Internal Server Error) if the sysDict couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-dicts")
    @Timed
    public ResponseEntity<SysDict> updateSysDict(@Valid @RequestBody SysDict sysDict) throws URISyntaxException {
        log.debug("REST request to update SysDict : {}", sysDict);
        if (sysDict.getId() == null) {
            return createSysDict(sysDict);
        }
        SysDict result = sysDictService.save(sysDict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sysDict.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sys-dicts : get all the sysDicts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sysDicts in body
     */
    @GetMapping("/sys-dicts")
    @Timed
    public ResponseEntity<List<SysDict>> getAllSysDicts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SysDicts");
        Page<SysDict> page = sysDictService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-dicts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sys-dicts/:id : get the "id" sysDict.
     *
     * @param id the id of the sysDict to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysDict, or with status 404 (Not Found)
     */
    @GetMapping("/sys-dicts/{id}")
    @Timed
    public ResponseEntity<SysDict> getSysDict(@PathVariable Long id) {
        log.debug("REST request to get SysDict : {}", id);
        SysDict sysDict = sysDictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sysDict));
    }

    /**
     * DELETE  /sys-dicts/:id : delete the "id" sysDict.
     *
     * @param id the id of the sysDict to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sys-dicts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSysDict(@PathVariable Long id) {
        log.debug("REST request to delete SysDict : {}", id);
        sysDictService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sys-dicts?query=:query : search for the sysDict corresponding
     * to the query.
     *
     * @param query the query of the sysDict search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sys-dicts")
    @Timed
    public ResponseEntity<List<SysDict>> searchSysDicts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SysDicts for query {}", query);
        Page<SysDict> page = sysDictService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sys-dicts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
