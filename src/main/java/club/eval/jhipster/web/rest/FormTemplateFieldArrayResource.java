package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplateFieldArray;
import club.eval.jhipster.service.FormTemplateFieldArrayService;
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
 * REST controller for managing FormTemplateFieldArray.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateFieldArrayResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldArrayResource.class);

    private static final String ENTITY_NAME = "formTemplateFieldArray";

    private final FormTemplateFieldArrayService formTemplateFieldArrayService;

    public FormTemplateFieldArrayResource(FormTemplateFieldArrayService formTemplateFieldArrayService) {
        this.formTemplateFieldArrayService = formTemplateFieldArrayService;
    }

    /**
     * POST  /form-template-field-arrays : Create a new formTemplateFieldArray.
     *
     * @param formTemplateFieldArray the formTemplateFieldArray to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplateFieldArray, or with status 400 (Bad Request) if the formTemplateFieldArray has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-template-field-arrays")
    @Timed
    public ResponseEntity<FormTemplateFieldArray> createFormTemplateFieldArray(@RequestBody FormTemplateFieldArray formTemplateFieldArray) throws URISyntaxException {
        log.debug("REST request to save FormTemplateFieldArray : {}", formTemplateFieldArray);
        if (formTemplateFieldArray.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplateFieldArray cannot already have an ID")).body(null);
        }
        FormTemplateFieldArray result = formTemplateFieldArrayService.save(formTemplateFieldArray);
        return ResponseEntity.created(new URI("/api/form-template-field-arrays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-template-field-arrays : Updates an existing formTemplateFieldArray.
     *
     * @param formTemplateFieldArray the formTemplateFieldArray to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplateFieldArray,
     * or with status 400 (Bad Request) if the formTemplateFieldArray is not valid,
     * or with status 500 (Internal Server Error) if the formTemplateFieldArray couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-template-field-arrays")
    @Timed
    public ResponseEntity<FormTemplateFieldArray> updateFormTemplateFieldArray(@RequestBody FormTemplateFieldArray formTemplateFieldArray) throws URISyntaxException {
        log.debug("REST request to update FormTemplateFieldArray : {}", formTemplateFieldArray);
        if (formTemplateFieldArray.getId() == null) {
            return createFormTemplateFieldArray(formTemplateFieldArray);
        }
        FormTemplateFieldArray result = formTemplateFieldArrayService.save(formTemplateFieldArray);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplateFieldArray.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-template-field-arrays : get all the formTemplateFieldArrays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplateFieldArrays in body
     */
    @GetMapping("/form-template-field-arrays")
    @Timed
    public ResponseEntity<List<FormTemplateFieldArray>> getAllFormTemplateFieldArrays(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplateFieldArrays");
        Page<FormTemplateFieldArray> page = formTemplateFieldArrayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-template-field-arrays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-template-field-arrays/:id : get the "id" formTemplateFieldArray.
     *
     * @param id the id of the formTemplateFieldArray to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplateFieldArray, or with status 404 (Not Found)
     */
    @GetMapping("/form-template-field-arrays/{id}")
    @Timed
    public ResponseEntity<FormTemplateFieldArray> getFormTemplateFieldArray(@PathVariable Long id) {
        log.debug("REST request to get FormTemplateFieldArray : {}", id);
        FormTemplateFieldArray formTemplateFieldArray = formTemplateFieldArrayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplateFieldArray));
    }

    /**
     * DELETE  /form-template-field-arrays/:id : delete the "id" formTemplateFieldArray.
     *
     * @param id the id of the formTemplateFieldArray to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-template-field-arrays/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplateFieldArray(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplateFieldArray : {}", id);
        formTemplateFieldArrayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-template-field-arrays?query=:query : search for the formTemplateFieldArray corresponding
     * to the query.
     *
     * @param query the query of the formTemplateFieldArray search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-template-field-arrays")
    @Timed
    public ResponseEntity<List<FormTemplateFieldArray>> searchFormTemplateFieldArrays(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplateFieldArrays for query {}", query);
        Page<FormTemplateFieldArray> page = formTemplateFieldArrayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-template-field-arrays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
