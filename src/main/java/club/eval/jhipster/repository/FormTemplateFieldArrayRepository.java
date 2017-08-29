package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplateFieldArray;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplateFieldArray entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateFieldArrayRepository extends JpaRepository<FormTemplateFieldArray, Long> {

}
