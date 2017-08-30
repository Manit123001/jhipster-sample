package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplateField;
import club.eval.jhipster.service.FormTemplateFieldService;
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
 * REST controller for managing FormTemplateField.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateFieldResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldResource.class);

    private static final String ENTITY_NAME = "formTemplateField";

    private final FormTemplateFieldService formTemplateFieldService;

    public FormTemplateFieldResource(FormTemplateFieldService formTemplateFieldService) {
        this.formTemplateFieldService = formTemplateFieldService;
    }

    /**
     * POST  /form-template-fields : Create a new formTemplateField.
     *
     * @param formTemplateField the formTemplateField to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplateField, or with status 400 (Bad Request) if the formTemplateField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-template-fields")
    @Timed
    public ResponseEntity<FormTemplateField> createFormTemplateField(@Valid @RequestBody FormTemplateField formTemplateField) throws URISyntaxException {
        log.debug("REST request to save FormTemplateField : {}", formTemplateField);
        if (formTemplateField.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplateField cannot already have an ID")).body(null);
        }
        FormTemplateField result = formTemplateFieldService.save(formTemplateField);
        return ResponseEntity.created(new URI("/api/form-template-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-template-fields : Updates an existing formTemplateField.
     *
     * @param formTemplateField the formTemplateField to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplateField,
     * or with status 400 (Bad Request) if the formTemplateField is not valid,
     * or with status 500 (Internal Server Error) if the formTemplateField couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-template-fields")
    @Timed
    public ResponseEntity<FormTemplateField> updateFormTemplateField(@Valid @RequestBody FormTemplateField formTemplateField) throws URISyntaxException {
        log.debug("REST request to update FormTemplateField : {}", formTemplateField);
        if (formTemplateField.getId() == null) {
            return createFormTemplateField(formTemplateField);
        }
        FormTemplateField result = formTemplateFieldService.save(formTemplateField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplateField.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-template-fields : get all the formTemplateFields.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplateFields in body
     */
    @GetMapping("/form-template-fields")
    @Timed
    public ResponseEntity<List<FormTemplateField>> getAllFormTemplateFields(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplateFields");
        Page<FormTemplateField> page = formTemplateFieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-template-fields");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-template-fields/:id : get the "id" formTemplateField.
     *
     * @param id the id of the formTemplateField to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplateField, or with status 404 (Not Found)
     */
    @GetMapping("/form-template-fields/{id}")
    @Timed
    public ResponseEntity<FormTemplateField> getFormTemplateField(@PathVariable Long id) {
        log.debug("REST request to get FormTemplateField : {}", id);
        FormTemplateField formTemplateField = formTemplateFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplateField));
    }

    /**
     * DELETE  /form-template-fields/:id : delete the "id" formTemplateField.
     *
     * @param id the id of the formTemplateField to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-template-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplateField(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplateField : {}", id);
        formTemplateFieldService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-template-fields?query=:query : search for the formTemplateField corresponding
     * to the query.
     *
     * @param query the query of the formTemplateField search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-template-fields")
    @Timed
    public ResponseEntity<List<FormTemplateField>> searchFormTemplateFields(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplateFields for query {}", query);
        Page<FormTemplateField> page = formTemplateFieldService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-template-fields");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
