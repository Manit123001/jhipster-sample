package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplateFieldAttr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplateFieldAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateFieldAttrRepository extends JpaRepository<FormTemplateFieldAttr, Long> {

}
