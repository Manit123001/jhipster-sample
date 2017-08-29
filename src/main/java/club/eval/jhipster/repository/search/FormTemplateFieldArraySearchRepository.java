package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplateFieldArray;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplateFieldArray entity.
 */
public interface FormTemplateFieldArraySearchRepository extends ElasticsearchRepository<FormTemplateFieldArray, Long> {
}
