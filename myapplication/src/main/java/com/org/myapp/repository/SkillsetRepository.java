package com.org.myapp.repository;

import com.org.myapp.domain.Skillset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Skillset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillsetRepository extends JpaRepository<Skillset, Long> {

}
