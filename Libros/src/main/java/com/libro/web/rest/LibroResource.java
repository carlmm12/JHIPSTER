package com.libro.web.rest;

import com.libro.domain.Libro;
import com.libro.service.LibroService;
import com.libro.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.libro.domain.Libro}.
 */
@RestController
@RequestMapping("/api")
public class LibroResource {

    private final Logger log = LoggerFactory.getLogger(LibroResource.class);

    private static final String ENTITY_NAME = "librosLibro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LibroService libroService;

    public LibroResource(LibroService libroService) {
        this.libroService = libroService;
    }

    /**
     * {@code POST  /libros} : Create a new libro.
     *
     * @param libro the libro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new libro, or with status {@code 400 (Bad Request)} if the libro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/libros")
    public ResponseEntity<Libro> createLibro(@Valid @RequestBody Libro libro) throws URISyntaxException {
        log.debug("REST request to save Libro : {}", libro);
        if (libro.getId() != null) {
            throw new BadRequestAlertException("A new libro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Libro result = libroService.save(libro);
        return ResponseEntity.created(new URI("/api/libros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /libros} : Updates an existing libro.
     *
     * @param libro the libro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated libro,
     * or with status {@code 400 (Bad Request)} if the libro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the libro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/libros")
    public ResponseEntity<Libro> updateLibro(@Valid @RequestBody Libro libro) throws URISyntaxException {
        log.debug("REST request to update Libro : {}", libro);
        if (libro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Libro result = libroService.save(libro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, libro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /libros} : get all the libros.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of libros in body.
     */
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> getAllLibros(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Libros");
        Page<Libro> page = libroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /libros/:id} : get the "id" libro.
     *
     * @param id the id of the libro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the libro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long id) {
        log.debug("REST request to get Libro : {}", id);
        Optional<Libro> libro = libroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(libro);
    }

    /**
     * {@code DELETE  /libros/:id} : delete the "id" libro.
     *
     * @param id the id of the libro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        log.debug("REST request to delete Libro : {}", id);
        libroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
