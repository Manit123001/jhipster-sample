package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.HealthRecordAttr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HealthRecordAttr entity.
 */
public interface HealthRecordAttrSearchRepository extends ElasticsearchRepository<HealthRecordAttr, Long> {
}
