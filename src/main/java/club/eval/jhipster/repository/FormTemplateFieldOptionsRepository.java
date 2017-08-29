package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplateFieldOptions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplateFieldOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateFieldOptionsRepository extends JpaRepository<FormTemplateFieldOptions, Long> {

}
