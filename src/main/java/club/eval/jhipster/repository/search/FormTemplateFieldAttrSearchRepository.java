package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.FormTemplateFieldAttr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormTemplateFieldAttr entity.
 */
public interface FormTemplateFieldAttrSearchRepository extends ElasticsearchRepository<FormTemplateFieldAttr, Long> {
}
