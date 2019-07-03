package com.libro.web.rest;

import com.libro.domain.Ejemplar;
import com.libro.service.EjemplarService;
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
 * REST controller for managing {@link com.libro.domain.Ejemplar}.
 */
@RestController
@RequestMapping("/api")
public class EjemplarResource {

    private final Logger log = LoggerFactory.getLogger(EjemplarResource.class);

    private static final String ENTITY_NAME = "librosEjemplar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EjemplarService ejemplarService;

    public EjemplarResource(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    /**
     * {@code POST  /ejemplars} : Create a new ejemplar.
     *
     * @param ejemplar the ejemplar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ejemplar, or with status {@code 400 (Bad Request)} if the ejemplar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ejemplars")
    public ResponseEntity<Ejemplar> createEjemplar(@Valid @RequestBody Ejemplar ejemplar) throws URISyntaxException {
        log.debug("REST request to save Ejemplar : {}", ejemplar);
        if (ejemplar.getId() != null) {
            throw new BadRequestAlertException("A new ejemplar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ejemplar result = ejemplarService.save(ejemplar);
        return ResponseEntity.created(new URI("/api/ejemplars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ejemplars} : Updates an existing ejemplar.
     *
     * @param ejemplar the ejemplar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ejemplar,
     * or with status {@code 400 (Bad Request)} if the ejemplar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ejemplar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ejemplars")
    public ResponseEntity<Ejemplar> updateEjemplar(@Valid @RequestBody Ejemplar ejemplar) throws URISyntaxException {
        log.debug("REST request to update Ejemplar : {}", ejemplar);
        if (ejemplar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ejemplar result = ejemplarService.save(ejemplar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ejemplar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ejemplars} : get all the ejemplars.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ejemplars in body.
     */
    @GetMapping("/ejemplars")
    public ResponseEntity<List<Ejemplar>> getAllEjemplars(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Ejemplars");
        Page<Ejemplar> page = ejemplarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ejemplars/:id} : get the "id" ejemplar.
     *
     * @param id the id of the ejemplar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ejemplar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ejemplars/{id}")
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Long id) {
        log.debug("REST request to get Ejemplar : {}", id);
        Optional<Ejemplar> ejemplar = ejemplarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ejemplar);
    }

    /**
     * {@code DELETE  /ejemplars/:id} : delete the "id" ejemplar.
     *
     * @param id the id of the ejemplar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ejemplars/{id}")
    public ResponseEntity<Void> deleteEjemplar(@PathVariable Long id) {
        log.debug("REST request to delete Ejemplar : {}", id);
        ejemplarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
