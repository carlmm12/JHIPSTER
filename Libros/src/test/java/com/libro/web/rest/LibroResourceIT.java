package com.libro.web.rest;

import com.libro.LibrosApp;
import com.libro.config.SecurityBeanOverrideConfiguration;
import com.libro.domain.Libro;
import com.libro.repository.LibroRepository;
import com.libro.service.LibroService;
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
 * Integration tests for the {@Link LibroResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LibrosApp.class})
public class LibroResourceIT {

    private static final String DEFAULT_EDITORIAL = "AAAAAAAAAA";
    private static final String UPDATED_EDITORIAL = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

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

    private MockMvc restLibroMockMvc;

    private Libro libro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LibroResource libroResource = new LibroResource(libroService);
        this.restLibroMockMvc = MockMvcBuilders.standaloneSetup(libroResource)
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
    public static Libro createEntity(EntityManager em) {
        Libro libro = new Libro()
            .editorial(DEFAULT_EDITORIAL)
            .titulo(DEFAULT_TITULO);
        return libro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Libro createUpdatedEntity(EntityManager em) {
        Libro libro = new Libro()
            .editorial(UPDATED_EDITORIAL)
            .titulo(UPDATED_TITULO);
        return libro;
    }

    @BeforeEach
    public void initTest() {
        libro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibro() throws Exception {
        int databaseSizeBeforeCreate = libroRepository.findAll().size();

        // Create the Libro
        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isCreated());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeCreate + 1);
        Libro testLibro = libroList.get(libroList.size() - 1);
        assertThat(testLibro.getEditorial()).isEqualTo(DEFAULT_EDITORIAL);
        assertThat(testLibro.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createLibroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libroRepository.findAll().size();

        // Create the Libro with an existing ID
        libro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEditorialIsRequired() throws Exception {
        int databaseSizeBeforeTest = libroRepository.findAll().size();
        // set the field null
        libro.setEditorial(null);

        // Create the Libro, which fails.

        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = libroRepository.findAll().size();
        // set the field null
        libro.setTitulo(null);

        // Create the Libro, which fails.

        restLibroMockMvc.perform(post("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibros() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        // Get all the libroList
        restLibroMockMvc.perform(get("/api/libros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libro.getId().intValue())))
            .andExpect(jsonPath("$.[*].editorial").value(hasItem(DEFAULT_EDITORIAL.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())));
    }
    
    @Test
    @Transactional
    public void getLibro() throws Exception {
        // Initialize the database
        libroRepository.saveAndFlush(libro);

        // Get the libro
        restLibroMockMvc.perform(get("/api/libros/{id}", libro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libro.getId().intValue()))
            .andExpect(jsonPath("$.editorial").value(DEFAULT_EDITORIAL.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibro() throws Exception {
        // Get the libro
        restLibroMockMvc.perform(get("/api/libros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibro() throws Exception {
        // Initialize the database
        libroService.save(libro);

        int databaseSizeBeforeUpdate = libroRepository.findAll().size();

        // Update the libro
        Libro updatedLibro = libroRepository.findById(libro.getId()).get();
        // Disconnect from session so that the updates on updatedLibro are not directly saved in db
        em.detach(updatedLibro);
        updatedLibro
            .editorial(UPDATED_EDITORIAL)
            .titulo(UPDATED_TITULO);

        restLibroMockMvc.perform(put("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLibro)))
            .andExpect(status().isOk());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeUpdate);
        Libro testLibro = libroList.get(libroList.size() - 1);
        assertThat(testLibro.getEditorial()).isEqualTo(UPDATED_EDITORIAL);
        assertThat(testLibro.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingLibro() throws Exception {
        int databaseSizeBeforeUpdate = libroRepository.findAll().size();

        // Create the Libro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLibroMockMvc.perform(put("/api/libros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libro)))
            .andExpect(status().isBadRequest());

        // Validate the Libro in the database
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLibro() throws Exception {
        // Initialize the database
        libroService.save(libro);

        int databaseSizeBeforeDelete = libroRepository.findAll().size();

        // Delete the libro
        restLibroMockMvc.perform(delete("/api/libros/{id}", libro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Libro> libroList = libroRepository.findAll();
        assertThat(libroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Libro.class);
        Libro libro1 = new Libro();
        libro1.setId(1L);
        Libro libro2 = new Libro();
        libro2.setId(libro1.getId());
        assertThat(libro1).isEqualTo(libro2);
        libro2.setId(2L);
        assertThat(libro1).isNotEqualTo(libro2);
        libro1.setId(null);
        assertThat(libro1).isNotEqualTo(libro2);
    }
}
