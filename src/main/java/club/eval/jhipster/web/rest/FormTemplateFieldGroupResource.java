package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.FormTemplateFieldGroup;
import club.eval.jhipster.service.FormTemplateFieldGroupService;
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
 * REST controller for managing FormTemplateFieldGroup.
 */
@RestController
@RequestMapping("/api")
public class FormTemplateFieldGroupResource {

    private final Logger log = LoggerFactory.getLogger(FormTemplateFieldGroupResource.class);

    private static final String ENTITY_NAME = "formTemplateFieldGroup";

    private final FormTemplateFieldGroupService formTemplateFieldGroupService;

    public FormTemplateFieldGroupResource(FormTemplateFieldGroupService formTemplateFieldGroupService) {
        this.formTemplateFieldGroupService = formTemplateFieldGroupService;
    }

    /**
     * POST  /form-template-field-groups : Create a new formTemplateFieldGroup.
     *
     * @param formTemplateFieldGroup the formTemplateFieldGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formTemplateFieldGroup, or with status 400 (Bad Request) if the formTemplateFieldGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/form-template-field-groups")
    @Timed
    public ResponseEntity<FormTemplateFieldGroup> createFormTemplateFieldGroup(@Valid @RequestBody FormTemplateFieldGroup formTemplateFieldGroup) throws URISyntaxException {
        log.debug("REST request to save FormTemplateFieldGroup : {}", formTemplateFieldGroup);
        if (formTemplateFieldGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new formTemplateFieldGroup cannot already have an ID")).body(null);
        }
        FormTemplateFieldGroup result = formTemplateFieldGroupService.save(formTemplateFieldGroup);
        return ResponseEntity.created(new URI("/api/form-template-field-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /form-template-field-groups : Updates an existing formTemplateFieldGroup.
     *
     * @param formTemplateFieldGroup the formTemplateFieldGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formTemplateFieldGroup,
     * or with status 400 (Bad Request) if the formTemplateFieldGroup is not valid,
     * or with status 500 (Internal Server Error) if the formTemplateFieldGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/form-template-field-groups")
    @Timed
    public ResponseEntity<FormTemplateFieldGroup> updateFormTemplateFieldGroup(@Valid @RequestBody FormTemplateFieldGroup formTemplateFieldGroup) throws URISyntaxException {
        log.debug("REST request to update FormTemplateFieldGroup : {}", formTemplateFieldGroup);
        if (formTemplateFieldGroup.getId() == null) {
            return createFormTemplateFieldGroup(formTemplateFieldGroup);
        }
        FormTemplateFieldGroup result = formTemplateFieldGroupService.save(formTemplateFieldGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formTemplateFieldGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /form-template-field-groups : get all the formTemplateFieldGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formTemplateFieldGroups in body
     */
    @GetMapping("/form-template-field-groups")
    @Timed
    public ResponseEntity<List<FormTemplateFieldGroup>> getAllFormTemplateFieldGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FormTemplateFieldGroups");
        Page<FormTemplateFieldGroup> page = formTemplateFieldGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/form-template-field-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /form-template-field-groups/:id : get the "id" formTemplateFieldGroup.
     *
     * @param id the id of the formTemplateFieldGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formTemplateFieldGroup, or with status 404 (Not Found)
     */
    @GetMapping("/form-template-field-groups/{id}")
    @Timed
    public ResponseEntity<FormTemplateFieldGroup> getFormTemplateFieldGroup(@PathVariable Long id) {
        log.debug("REST request to get FormTemplateFieldGroup : {}", id);
        FormTemplateFieldGroup formTemplateFieldGroup = formTemplateFieldGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formTemplateFieldGroup));
    }

    /**
     * DELETE  /form-template-field-groups/:id : delete the "id" formTemplateFieldGroup.
     *
     * @param id the id of the formTemplateFieldGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/form-template-field-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormTemplateFieldGroup(@PathVariable Long id) {
        log.debug("REST request to delete FormTemplateFieldGroup : {}", id);
        formTemplateFieldGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/form-template-field-groups?query=:query : search for the formTemplateFieldGroup corresponding
     * to the query.
     *
     * @param query the query of the formTemplateFieldGroup search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/form-template-field-groups")
    @Timed
    public ResponseEntity<List<FormTemplateFieldGroup>> searchFormTemplateFieldGroups(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FormTemplateFieldGroups for query {}", query);
        Page<FormTemplateFieldGroup> page = formTemplateFieldGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/form-template-field-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
