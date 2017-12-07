package club.eval.jhipster.service;

import club.eval.jhipster.domain.ImportRecordPatient;
import club.eval.jhipster.repository.ImportRecordPatientRepository;
import club.eval.jhipster.repository.search.ImportRecordPatientSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ImportRecordPatient.
 */
@Service
@Transactional
public class ImportRecordPatientService {

    private final Logger log = LoggerFactory.getLogger(ImportRecordPatientService.class);

    private final ImportRecordPatientRepository importRecordPatientRepository;

    private final ImportRecordPatientSearchRepository importRecordPatientSearchRepository;
    public ImportRecordPatientService(ImportRecordPatientRepository importRecordPatientRepository, ImportRecordPatientSearchRepository importRecordPatientSearchRepository) {
        this.importRecordPatientRepository = importRecordPatientRepository;
        this.importRecordPatientSearchRepository = importRecordPatientSearchRepository;
    }

    /**
     * Save a importRecordPatient.
     *
     * @param importRecordPatient the entity to save
     * @return the persisted entity
     */
    public ImportRecordPatient save(ImportRecordPatient importRecordPatient) {
        log.debug("Request to save ImportRecordPatient : {}", importRecordPatient);
        ImportRecordPatient result = importRecordPatientRepository.save(importRecordPatient);
        importRecordPatientSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the importRecordPatients.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ImportRecordPatient> findAll() {
        log.debug("Request to get all ImportRecordPatients");
        return importRecordPatientRepository.findAll();
    }

    /**
     *  Get one importRecordPatient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ImportRecordPatient findOne(Long id) {
        log.debug("Request to get ImportRecordPatient : {}", id);
        return importRecordPatientRepository.findOne(id);
    }

    /**
     *  Delete the  importRecordPatient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ImportRecordPatient : {}", id);
        importRecordPatientRepository.delete(id);
        importRecordPatientSearchRepository.delete(id);
    }

    /**
     * Search for the importRecordPatient corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ImportRecordPatient> search(String query) {
        log.debug("Request to search ImportRecordPatients for query {}", query);
        return StreamSupport
            .stream(importRecordPatientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
