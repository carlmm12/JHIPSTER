package com.libro.repository;

import com.libro.domain.Libro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Libro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

}
