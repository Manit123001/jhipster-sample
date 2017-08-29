package club.eval.jhipster.repository;

import club.eval.jhipster.domain.FormTemplateFieldGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormTemplateFieldGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormTemplateFieldGroupRepository extends JpaRepository<FormTemplateFieldGroup, Long> {

}
