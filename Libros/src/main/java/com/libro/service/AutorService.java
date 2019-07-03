package com.libro.service;

import com.libro.domain.Autor;
import com.libro.repository.AutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Autor}.
 */
@Service
@Transactional
public class AutorService {

    private final Logger log = LoggerFactory.getLogger(AutorService.class);

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * Save a autor.
     *
     * @param autor the entity to save.
     * @return the persisted entity.
     */
    public Autor save(Autor autor) {
        log.debug("Request to save Autor : {}", autor);
        return autorRepository.save(autor);
    }

    /**
     * Get all the autors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Autor> findAll(Pageable pageable) {
        log.debug("Request to get all Autors");
        return autorRepository.findAll(pageable);
    }

    /**
     * Get all the autors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Autor> findAllWithEagerRelationships(Pageable pageable) {
        return autorRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one autor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autor> findOne(Long id) {
        log.debug("Request to get Autor : {}", id);
        return autorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the autor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Autor : {}", id);
        autorRepository.deleteById(id);
    }
}
