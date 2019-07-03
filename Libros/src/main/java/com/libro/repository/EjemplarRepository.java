package com.libro.repository;

import com.libro.domain.Ejemplar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ejemplar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

}
