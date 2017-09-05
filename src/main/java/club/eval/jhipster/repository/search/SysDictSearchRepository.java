package club.eval.jhipster.repository.search;

import club.eval.jhipster.domain.SysDict;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SysDict entity.
 */
public interface SysDictSearchRepository extends ElasticsearchRepository<SysDict, Long> {
}
