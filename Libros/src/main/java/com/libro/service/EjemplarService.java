package com.libro.service;

import com.libro.domain.Ejemplar;
import com.libro.repository.EjemplarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ejemplar}.
 */
@Service
@Transactional
public class EjemplarService {

    private final Logger log = LoggerFactory.getLogger(EjemplarService.class);

    private final EjemplarRepository ejemplarRepository;

    public EjemplarService(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    /**
     * Save a ejemplar.
     *
     * @param ejemplar the entity to save.
     * @return the persisted entity.
     */
    public Ejemplar save(Ejemplar ejemplar) {
        log.debug("Request to save Ejemplar : {}", ejemplar);
        return ejemplarRepository.save(ejemplar);
    }

    /**
     * Get all the ejemplars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ejemplar> findAll(Pageable pageable) {
        log.debug("Request to get all Ejemplars");
        return ejemplarRepository.findAll(pageable);
    }


    /**
     * Get one ejemplar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ejemplar> findOne(Long id) {
        log.debug("Request to get Ejemplar : {}", id);
        return ejemplarRepository.findById(id);
    }

    /**
     * Delete the ejemplar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ejemplar : {}", id);
        ejemplarRepository.deleteById(id);
    }
}
