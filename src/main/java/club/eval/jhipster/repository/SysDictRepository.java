package club.eval.jhipster.repository;

import club.eval.jhipster.domain.SysDict;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SysDict entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDictRepository extends JpaRepository<SysDict, Long> {

}
