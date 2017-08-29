package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplate;
import club.eval.jhipster.service.FormTemplateService;
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
 * REST controller for managing FormTemplate.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateResource.class);

    private static final String ENTITY_NAME = "formTemplate";

    private final FormTemplateService formTemplateService;

    public FormTemplateResource(FormTemplateService formTemplateService) {
        this.formTemplateService = formTemplateService;
    }

    /**
     * POST  /form-templates : Create a new formTemplate.
     *
     * @param formTemplate the formTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplate, or with status 400 (Bad Request) if the formTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-templates")
    @Timed
    public ResponseEntity<FormTemplate> createFormTemplate(@RequestBody FormTemplate formTemplate) throws URISyntaxException {
        log.debug("REST request to save FormTemplate : {}", formTemplate);
        if (formTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplate cannot already have an ID")).body(null);
        }
        FormTemplate result = formTemplateService.save(formTemplate);
        return ResponseEntity.created(new URI("/api/form-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-templates : Updates an existing formTemplate.
     *
     * @param formTemplate the formTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplate,
     * or with status 400 (Bad Request) if the formTemplate is not valid,
     * or with status 500 (Internal Server Error) if the formTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-templates")
    @Timed
    public ResponseEntity<FormTemplate> updateFormTemplate(@RequestBody FormTemplate formTemplate) throws URISyntaxException {
        log.debug("REST request to update FormTemplate : {}", formTemplate);
        if (formTemplate.getId() == null) {
            return createFormTemplate(formTemplate);
        }
        FormTemplate result = formTemplateService.save(formTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-templates : get all the formTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplates in body
     */
    @GetMapping("/form-templates")
    @Timed
    public ResponseEntity<List<FormTemplate>> getAllFormTemplates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplates");
        Page<FormTemplate> page = formTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-templates/:id : get the "id" formTemplate.
     *
     * @param id the id of the formTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/form-templates/{id}")
    @Timed
    public ResponseEntity<FormTemplate> getFormTemplate(@PathVariable Long id) {
        log.debug("REST request to get FormTemplate : {}", id);
        FormTemplate formTemplate = formTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplate));
    }

    /**
     * DELETE  /form-templates/:id : delete the "id" formTemplate.
     *
     * @param id the id of the formTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplate(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplate : {}", id);
        formTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-templates?query=:query : search for the formTemplate corresponding
     * to the query.
     *
     * @param query the query of the formTemplate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-templates")
    @Timed
    public ResponseEntity<List<FormTemplate>> searchFormTemplates(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplates for query {}", query);
        Page<FormTemplate> page = formTemplateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
