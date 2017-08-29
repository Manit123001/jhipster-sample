package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateRepository extends JpaRepository<FormTemplate, Long> {

}
