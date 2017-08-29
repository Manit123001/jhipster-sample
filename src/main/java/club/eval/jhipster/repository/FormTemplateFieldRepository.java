package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplateField;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplateField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateFieldRepository extends JpaRepository<FormTemplateField, Long> {

}
