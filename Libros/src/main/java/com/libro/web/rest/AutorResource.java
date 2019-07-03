package com.libro.web.rest;

import com.libro.domain.Autor;
import com.libro.service.AutorService;
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
 * REST controller for managing {@link com.libro.domain.Autor}.
 */
@RestController
@RequestMapping("/api")
public class AutorResource {

    private final Logger log = LoggerFactory.getLogger(AutorResource.class);

    private static final String ENTITY_NAME = "librosAutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorService autorService;

    public AutorResource(AutorService autorService) {
        this.autorService = autorService;
    }

    /**
     * {@code POST  /autors} : Create a new autor.
     *
     * @param autor the autor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autor, or with status {@code 400 (Bad Request)} if the autor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autors")
    public ResponseEntity<Autor> createAutor(@Valid @RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to save Autor : {}", autor);
        if (autor.getId() != null) {
            throw new BadRequestAlertException("A new autor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Autor result = autorService.save(autor);
        return ResponseEntity.created(new URI("/api/autors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autors} : Updates an existing autor.
     *
     * @param autor the autor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autor,
     * or with status {@code 400 (Bad Request)} if the autor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autors")
    public ResponseEntity<Autor> updateAutor(@Valid @RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to update Autor : {}", autor);
        if (autor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Autor result = autorService.save(autor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autors} : get all the autors.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autors in body.
     */
    @GetMapping("/autors")
    public ResponseEntity<List<Autor>> getAllAutors(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Autors");
        Page<Autor> page;
        if (eagerload) {
            page = autorService.findAllWithEagerRelationships(pageable);
        } else {
            page = autorService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autors/:id} : get the "id" autor.
     *
     * @param id the id of the autor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autors/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Long id) {
        log.debug("REST request to get Autor : {}", id);
        Optional<Autor> autor = autorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autor);
    }

    /**
     * {@code DELETE  /autors/:id} : delete the "id" autor.
     *
     * @param id the id of the autor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autors/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        log.debug("REST request to delete Autor : {}", id);
        autorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
