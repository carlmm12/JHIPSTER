package com.libro.repository;

import com.libro.domain.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Autor entity.
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query(value = "select distinct autor from Autor autor left join fetch autor.libros",
        countQuery = "select count(distinct autor) from Autor autor")
    Page<Autor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct autor from Autor autor left join fetch autor.libros")
    List<Autor> findAllWithEagerRelationships();

    @Query("select autor from Autor autor left join fetch autor.libros where autor.id =:id")
    Optional<Autor> findOneWithEagerRelationships(@Param("id") Long id);

}
