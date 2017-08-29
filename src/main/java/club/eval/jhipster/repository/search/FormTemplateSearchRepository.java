package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplate entity.
 */
public interface FormTemplateSearchRepository extends ElasticsearchRepository<FormTemplate, Long> {
}
