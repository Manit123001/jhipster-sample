package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.ImportRecordPatient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ImportRecordPatient entity.
 */
public interface ImportRecordPatientSearchRepository extends ElasticsearchRepository<ImportRecordPatient, Long> {
}
