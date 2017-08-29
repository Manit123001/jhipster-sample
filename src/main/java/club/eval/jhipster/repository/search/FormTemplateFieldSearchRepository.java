package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplateField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplateField entity.
 */
public interface FormTemplateFieldSearchRepository extends ElasticsearchRepository<FormTemplateField, Long> {
}
