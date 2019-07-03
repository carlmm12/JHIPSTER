package com.libro.web.rest;

import com.libro.LibrosApp;
import com.libro.config.SecurityBeanOverrideConfiguration;
import com.libro.domain.Ejemplar;
import com.libro.repository.EjemplarRepository;
import com.libro.service.EjemplarService;
import com.libro.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.libro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link EjemplarResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LibrosApp.class})
public class EjemplarResourceIT {

    private static final String DEFAULT_EDICION = "AAAAAAAAAA";
    private static final String UPDATED_EDICION = "BBBBBBBBBB";

    private static final String DEFAULT_ENCUADERNACION = "AAAAAAAAAA";
    private static final String UPDATED_ENCUADERNACION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Autowired
    private EjemplarService ejemplarService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEjemplarMockMvc;

    private Ejemplar ejemplar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EjemplarResource ejemplarResource = new EjemplarResource(ejemplarService);
        this.restEjemplarMockMvc = MockMvcBuilders.standaloneSetup(ejemplarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ejemplar createEntity(EntityManager em) {
        Ejemplar ejemplar = new Ejemplar()
            .edicion(DEFAULT_EDICION)
            .encuadernacion(DEFAULT_ENCUADERNACION)
            .precio(DEFAULT_PRECIO);
        return ejemplar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ejemplar createUpdatedEntity(EntityManager em) {
        Ejemplar ejemplar = new Ejemplar()
            .edicion(UPDATED_EDICION)
            .encuadernacion(UPDATED_ENCUADERNACION)
            .precio(UPDATED_PRECIO);
        return ejemplar;
    }

    @BeforeEach
    public void initTest() {
        ejemplar = createEntity(em);
    }

    @Test
    @Transactional
    public void createEjemplar() throws Exception {
        int databaseSizeBeforeCreate = ejemplarRepository.findAll().size();

        // Create the Ejemplar
        restEjemplarMockMvc.perform(post("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isCreated());

        // Validate the Ejemplar in the database
        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeCreate + 1);
        Ejemplar testEjemplar = ejemplarList.get(ejemplarList.size() - 1);
        assertThat(testEjemplar.getEdicion()).isEqualTo(DEFAULT_EDICION);
        assertThat(testEjemplar.getEncuadernacion()).isEqualTo(DEFAULT_ENCUADERNACION);
        assertThat(testEjemplar.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createEjemplarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ejemplarRepository.findAll().size();

        // Create the Ejemplar with an existing ID
        ejemplar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEjemplarMockMvc.perform(post("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isBadRequest());

        // Validate the Ejemplar in the database
        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEdicionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ejemplarRepository.findAll().size();
        // set the field null
        ejemplar.setEdicion(null);

        // Create the Ejemplar, which fails.

        restEjemplarMockMvc.perform(post("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isBadRequest());

        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncuadernacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ejemplarRepository.findAll().size();
        // set the field null
        ejemplar.setEncuadernacion(null);

        // Create the Ejemplar, which fails.

        restEjemplarMockMvc.perform(post("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isBadRequest());

        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = ejemplarRepository.findAll().size();
        // set the field null
        ejemplar.setPrecio(null);

        // Create the Ejemplar, which fails.

        restEjemplarMockMvc.perform(post("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isBadRequest());

        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEjemplars() throws Exception {
        // Initialize the database
        ejemplarRepository.saveAndFlush(ejemplar);

        // Get all the ejemplarList
        restEjemplarMockMvc.perform(get("/api/ejemplars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ejemplar.getId().intValue())))
            .andExpect(jsonPath("$.[*].edicion").value(hasItem(DEFAULT_EDICION.toString())))
            .andExpect(jsonPath("$.[*].encuadernacion").value(hasItem(DEFAULT_ENCUADERNACION.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getEjemplar() throws Exception {
        // Initialize the database
        ejemplarRepository.saveAndFlush(ejemplar);

        // Get the ejemplar
        restEjemplarMockMvc.perform(get("/api/ejemplars/{id}", ejemplar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ejemplar.getId().intValue()))
            .andExpect(jsonPath("$.edicion").value(DEFAULT_EDICION.toString()))
            .andExpect(jsonPath("$.encuadernacion").value(DEFAULT_ENCUADERNACION.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEjemplar() throws Exception {
        // Get the ejemplar
        restEjemplarMockMvc.perform(get("/api/ejemplars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEjemplar() throws Exception {
        // Initialize the database
        ejemplarService.save(ejemplar);

        int databaseSizeBeforeUpdate = ejemplarRepository.findAll().size();

        // Update the ejemplar
        Ejemplar updatedEjemplar = ejemplarRepository.findById(ejemplar.getId()).get();
        // Disconnect from session so that the updates on updatedEjemplar are not directly saved in db
        em.detach(updatedEjemplar);
        updatedEjemplar
            .edicion(UPDATED_EDICION)
            .encuadernacion(UPDATED_ENCUADERNACION)
            .precio(UPDATED_PRECIO);

        restEjemplarMockMvc.perform(put("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEjemplar)))
            .andExpect(status().isOk());

        // Validate the Ejemplar in the database
        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeUpdate);
        Ejemplar testEjemplar = ejemplarList.get(ejemplarList.size() - 1);
        assertThat(testEjemplar.getEdicion()).isEqualTo(UPDATED_EDICION);
        assertThat(testEjemplar.getEncuadernacion()).isEqualTo(UPDATED_ENCUADERNACION);
        assertThat(testEjemplar.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingEjemplar() throws Exception {
        int databaseSizeBeforeUpdate = ejemplarRepository.findAll().size();

        // Create the Ejemplar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEjemplarMockMvc.perform(put("/api/ejemplars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ejemplar)))
            .andExpect(status().isBadRequest());

        // Validate the Ejemplar in the database
        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEjemplar() throws Exception {
        // Initialize the database
        ejemplarService.save(ejemplar);

        int databaseSizeBeforeDelete = ejemplarRepository.findAll().size();

        // Delete the ejemplar
        restEjemplarMockMvc.perform(delete("/api/ejemplars/{id}", ejemplar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ejemplar> ejemplarList = ejemplarRepository.findAll();
        assertThat(ejemplarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ejemplar.class);
        Ejemplar ejemplar1 = new Ejemplar();
        ejemplar1.setId(1L);
        Ejemplar ejemplar2 = new Ejemplar();
        ejemplar2.setId(ejemplar1.getId());
        assertThat(ejemplar1).isEqualTo(ejemplar2);
        ejemplar2.setId(2L);
        assertThat(ejemplar1).isNotEqualTo(ejemplar2);
        ejemplar1.setId(null);
        assertThat(ejemplar1).isNotEqualTo(ejemplar2);
    }
}
