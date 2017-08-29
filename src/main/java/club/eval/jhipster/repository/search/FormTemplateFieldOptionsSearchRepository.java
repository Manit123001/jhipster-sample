package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplateFieldOptions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplateFieldOptions entity.
 */
public interface FormTemplateFieldOptionsSearchRepository extends ElasticsearchRepository<FormTemplateFieldOptions, Long> {
}
