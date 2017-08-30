package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplateFieldOptions;
import club.eval.jhipster.service.FormTemplateFieldOptionsService;
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
 * REST controller for managing FormTemplateFieldOptions.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateFieldOptionsResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldOptionsResource.class);

    private static final String ENTITY_NAME = "formTemplateFieldOptions";

    private final FormTemplateFieldOptionsService formTemplateFieldOptionsService;

    public FormTemplateFieldOptionsResource(FormTemplateFieldOptionsService formTemplateFieldOptionsService) {
        this.formTemplateFieldOptionsService = formTemplateFieldOptionsService;
    }

    /**
     * POST  /form-template-field-options : Create a new formTemplateFieldOptions.
     *
     * @param formTemplateFieldOptions the formTemplateFieldOptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplateFieldOptions, or with status 400 (Bad Request) if the formTemplateFieldOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-template-field-options")
    @Timed
    public ResponseEntity<FormTemplateFieldOptions> createFormTemplateFieldOptions(@Valid @RequestBody FormTemplateFieldOptions formTemplateFieldOptions) throws URISyntaxException {
        log.debug("REST request to save FormTemplateFieldOptions : {}", formTemplateFieldOptions);
        if (formTemplateFieldOptions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplateFieldOptions cannot already have an ID")).body(null);
        }
        FormTemplateFieldOptions result = formTemplateFieldOptionsService.save(formTemplateFieldOptions);
        return ResponseEntity.created(new URI("/api/form-template-field-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-template-field-options : Updates an existing formTemplateFieldOptions.
     *
     * @param formTemplateFieldOptions the formTemplateFieldOptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplateFieldOptions,
     * or with status 400 (Bad Request) if the formTemplateFieldOptions is not valid,
     * or with status 500 (Internal Server Error) if the formTemplateFieldOptions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-template-field-options")
    @Timed
    public ResponseEntity<FormTemplateFieldOptions> updateFormTemplateFieldOptions(@Valid @RequestBody FormTemplateFieldOptions formTemplateFieldOptions) throws URISyntaxException {
        log.debug("REST request to update FormTemplateFieldOptions : {}", formTemplateFieldOptions);
        if (formTemplateFieldOptions.getId() == null) {
            return createFormTemplateFieldOptions(formTemplateFieldOptions);
        }
        FormTemplateFieldOptions result = formTemplateFieldOptionsService.save(formTemplateFieldOptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplateFieldOptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-template-field-options : get all the formTemplateFieldOptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplateFieldOptions in body
     */
    @GetMapping("/form-template-field-options")
    @Timed
    public ResponseEntity<List<FormTemplateFieldOptions>> getAllFormTemplateFieldOptions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplateFieldOptions");
        Page<FormTemplateFieldOptions> page = formTemplateFieldOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-template-field-options");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-template-field-options/:id : get the "id" formTemplateFieldOptions.
     *
     * @param id the id of the formTemplateFieldOptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplateFieldOptions, or with status 404 (Not Found)
     */
    @GetMapping("/form-template-field-options/{id}")
    @Timed
    public ResponseEntity<FormTemplateFieldOptions> getFormTemplateFieldOptions(@PathVariable Long id) {
        log.debug("REST request to get FormTemplateFieldOptions : {}", id);
        FormTemplateFieldOptions formTemplateFieldOptions = formTemplateFieldOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplateFieldOptions));
    }

    /**
     * DELETE  /form-template-field-options/:id : delete the "id" formTemplateFieldOptions.
     *
     * @param id the id of the formTemplateFieldOptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-template-field-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplateFieldOptions(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplateFieldOptions : {}", id);
        formTemplateFieldOptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-template-field-options?query=:query : search for the formTemplateFieldOptions corresponding
     * to the query.
     *
     * @param query the query of the formTemplateFieldOptions search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-template-field-options")
    @Timed
    public ResponseEntity<List<FormTemplateFieldOptions>> searchFormTemplateFieldOptions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplateFieldOptions for query {}", query);
        Page<FormTemplateFieldOptions> page = formTemplateFieldOptionsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-template-field-options");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
