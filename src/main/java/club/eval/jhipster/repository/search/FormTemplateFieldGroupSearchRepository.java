package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplateFieldGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplateFieldGroup entity.
 */
public interface FormTemplateFieldGroupSearchRepository extends ElasticsearchRepository<FormTemplateFieldGroup, Long> {
}
