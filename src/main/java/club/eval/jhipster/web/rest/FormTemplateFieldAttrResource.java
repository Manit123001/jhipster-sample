package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplateFieldAttr;
import club.eval.jhipster.service.FormTemplateFieldAttrService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FormTemplateFieldAttr.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateFieldAttrResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldAttrResource.class);

    private static final String ENTITY_NAME = "formTemplateFieldAttr";

    private final FormTemplateFieldAttrService formTemplateFieldAttrService;

    public FormTemplateFieldAttrResource(FormTemplateFieldAttrService formTemplateFieldAttrService) {
        this.formTemplateFieldAttrService = formTemplateFieldAttrService;
    }

    /**
     * POST  /form-template-field-attrs : Create a new formTemplateFieldAttr.
     *
     * @param formTemplateFieldAttr the formTemplateFieldAttr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplateFieldAttr, or with status 400 (Bad Request) if the formTemplateFieldAttr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-template-field-attrs")
    @Timed
    public ResponseEntity<FormTemplateFieldAttr> createFormTemplateFieldAttr(@RequestBody FormTemplateFieldAttr formTemplateFieldAttr) throws URISyntaxException {
        log.debug("REST request to save FormTemplateFieldAttr : {}", formTemplateFieldAttr);
        if (formTemplateFieldAttr.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplateFieldAttr cannot already have an ID")).body(null);
        }
        FormTemplateFieldAttr result = formTemplateFieldAttrService.save(formTemplateFieldAttr);
        return ResponseEntity.created(new URI("/api/form-template-field-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-template-field-attrs : Updates an existing formTemplateFieldAttr.
     *
     * @param formTemplateFieldAttr the formTemplateFieldAttr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplateFieldAttr,
     * or with status 400 (Bad Request) if the formTemplateFieldAttr is not valid,
     * or with status 500 (Internal Server Error) if the formTemplateFieldAttr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-template-field-attrs")
    @Timed
    public ResponseEntity<FormTemplateFieldAttr> updateFormTemplateFieldAttr(@RequestBody FormTemplateFieldAttr formTemplateFieldAttr) throws URISyntaxException {
        log.debug("REST request to update FormTemplateFieldAttr : {}", formTemplateFieldAttr);
        if (formTemplateFieldAttr.getId() == null) {
            return createFormTemplateFieldAttr(formTemplateFieldAttr);
        }
        FormTemplateFieldAttr result = formTemplateFieldAttrService.save(formTemplateFieldAttr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplateFieldAttr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-template-field-attrs : get all the formTemplateFieldAttrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplateFieldAttrs in body
     */
    @GetMapping("/form-template-field-attrs")
    @Timed
    public ResponseEntity<List<FormTemplateFieldAttr>> getAllFormTemplateFieldAttrs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplateFieldAttrs");
        Page<FormTemplateFieldAttr> page = formTemplateFieldAttrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-template-field-attrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-template-field-attrs/:id : get the "id" formTemplateFieldAttr.
     *
     * @param id the id of the formTemplateFieldAttr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplateFieldAttr, or with status 404 (Not Found)
     */
    @GetMapping("/form-template-field-attrs/{id}")
    @Timed
    public ResponseEntity<FormTemplateFieldAttr> getFormTemplateFieldAttr(@PathVariable Long id) {
        log.debug("REST request to get FormTemplateFieldAttr : {}", id);
        FormTemplateFieldAttr formTemplateFieldAttr = formTemplateFieldAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplateFieldAttr));
    }

    /**
     * DELETE  /form-template-field-attrs/:id : delete the "id" formTemplateFieldAttr.
     *
     * @param id the id of the formTemplateFieldAttr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-template-field-attrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplateFieldAttr(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplateFieldAttr : {}", id);
        formTemplateFieldAttrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-template-field-attrs?query=:query : search for the formTemplateFieldAttr corresponding
     * to the query.
     *
     * @param query the query of the formTemplateFieldAttr search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-template-field-attrs")
    @Timed
    public ResponseEntity<List<FormTemplateFieldAttr>> searchFormTemplateFieldAttrs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplateFieldAttrs for query {}", query);
        Page<FormTemplateFieldAttr> page = formTemplateFieldAttrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-template-field-attrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
