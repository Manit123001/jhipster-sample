package club.eval.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import club.eval.jhipster.domain.ImportRecordPatient;

import club.eval.jhipster.repository.ImportRecordPatientRepository;
import club.eval.jhipster.repository.search.ImportRecordPatientSearchRepository;
import club.eval.jhipster.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ImportRecordPatient.
 */
@RestController
@RequestMapping("/api")
public class ImportRecordPatientResource {

    private final Logger log = LoggerFactory.getLogger(ImportRecordPatientResource.class);

    private static final String ENTITY_NAME = "importRecordPatient";

    private final ImportRecordPatientRepository importRecordPatientRepository;

    private final ImportRecordPatientSearchRepository importRecordPatientSearchRepository;
    public ImportRecordPatientResource(ImportRecordPatientRepository importRecordPatientRepository, ImportRecordPatientSearchRepository importRecordPatientSearchRepository) {
        this.importRecordPatientRepository = importRecordPatientRepository;
        this.importRecordPatientSearchRepository = importRecordPatientSearchRepository;
    }

    /**
     * POST  /import-record-patients : Create a new importRecordPatient.
     *
     * @param importRecordPatient the importRecordPatient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new importRecordPatient, or with status 400 (Bad Request) if the importRecordPatient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/import-record-patients")
    @Timed
    public ResponseEntity<ImportRecordPatient> createImportRecordPatient(@Valid @RequestBody ImportRecordPatient importRecordPatient) throws URISyntaxException {
        log.debug("REST request to save ImportRecordPatient : {}", importRecordPatient);
        if (importRecordPatient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new importRecordPatient cannot already have an ID")).body(null);
        }
        ImportRecordPatient result = importRecordPatientRepository.save(importRecordPatient);
        importRecordPatientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/import-record-patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /import-record-patients : Updates an existing importRecordPatient.
     *
     * @param importRecordPatient the importRecordPatient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated importRecordPatient,
     * or with status 400 (Bad Request) if the importRecordPatient is not valid,
     * or with status 500 (Internal Server Error) if the importRecordPatient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/import-record-patients")
    @Timed
    public ResponseEntity<ImportRecordPatient> updateImportRecordPatient(@Valid @RequestBody ImportRecordPatient importRecordPatient) throws URISyntaxException {
        log.debug("REST request to update ImportRecordPatient : {}", importRecordPatient);
        if (importRecordPatient.getId() == null) {
            return createImportRecordPatient(importRecordPatient);
        }
        ImportRecordPatient result = importRecordPatientRepository.save(importRecordPatient);
        importRecordPatientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, importRecordPatient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /import-record-patients : get all the importRecordPatients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of importRecordPatients in body
     */
    @GetMapping("/import-record-patients")
    @Timed
    public List<ImportRecordPatient> getAllImportRecordPatients() {
        log.debug("REST request to get all ImportRecordPatients");
        return importRecordPatientRepository.findAll();
        }

    /**
     * GET  /import-record-patients/:id : get the "id" importRecordPatient.
     *
     * @param id the id of the importRecordPatient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the importRecordPatient, or with status 404 (Not Found)
     */
    @GetMapping("/import-record-patients/{id}")
    @Timed
    public ResponseEntity<ImportRecordPatient> getImportRecordPatient(@PathVariable Long id) {
        log.debug("REST request to get ImportRecordPatient : {}", id);
        ImportRecordPatient importRecordPatient = importRecordPatientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(importRecordPatient));
    }

    /**
     * DELETE  /import-record-patients/:id : delete the "id" importRecordPatient.
     *
     * @param id the id of the importRecordPatient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/import-record-patients/{id}")
    @Timed
    public ResponseEntity<Void> deleteImportRecordPatient(@PathVariable Long id) {
        log.debug("REST request to delete ImportRecordPatient : {}", id);
        importRecordPatientRepository.delete(id);
        importRecordPatientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/import-record-patients?query=:query : search for the importRecordPatient corresponding
     * to the query.
     *
     * @param query the query of the importRecordPatient search
     * @return the result of the search
     */
    @GetMapping("/_search/import-record-patients")
    @Timed
    public List<ImportRecordPatient> searchImportRecordPatients(@RequestParam String query) {
        log.debug("REST request to search ImportRecordPatients for query {}", query);
        return StreamSupport
            .stream(importRecordPatientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
