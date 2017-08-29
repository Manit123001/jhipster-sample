package club.eval.jhipster.repository;

import club.eval.jhipster.domain.HealthRecordAttr;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HealthRecordAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthRecordAttrRepository extends JpaRepository<HealthRecordAttr, Long> {

}
