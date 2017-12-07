package club.eval.jhipster.repository;

import club.eval.jhipster.domain.ImportRecordPatient;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ImportRecordPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImportRecordPatientRepository extends JpaRepository<ImportRecordPatient, Long> {

}
