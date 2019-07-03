package com.libro.web.rest;

import com.libro.LibrosApp;
import com.libro.config.SecurityBeanOverrideConfiguration;
import com.libro.domain.Autor;
import com.libro.repository.AutorRepository;
import com.libro.service.AutorService;
import com.libro.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.libro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AutorResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LibrosApp.class})
public class AutorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "2@g.gdk";
    private static final String UPDATED_EMAIL = "d@f4.bwf";

    @Autowired
    private AutorRepository autorRepository;

    @Mock
    private AutorRepository autorRepositoryMock;

    @Mock
    private AutorService autorServiceMock;

    @Autowired
    private AutorService autorService;

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

    private MockMvc restAutorMockMvc;

    private Autor autor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutorResource autorResource = new AutorResource(autorService);
        this.restAutorMockMvc = MockMvcBuilders.standaloneSetup(autorResource)
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
    public static Autor createEntity(EntityManager em) {
        Autor autor = new Autor()
            .nombre(DEFAULT_NOMBRE)
            .web(DEFAULT_WEB)
            .email(DEFAULT_EMAIL);
        return autor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autor createUpdatedEntity(EntityManager em) {
        Autor autor = new Autor()
            .nombre(UPDATED_NOMBRE)
            .web(UPDATED_WEB)
            .email(UPDATED_EMAIL);
        return autor;
    }

    @BeforeEach
    public void initTest() {
        autor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutor() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isCreated());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate + 1);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutor.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testAutor.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createAutorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autorRepository.findAll().size();

        // Create the Autor with an existing ID
        autor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorRepository.findAll().size();
        // set the field null
        autor.setNombre(null);

        // Create the Autor, which fails.

        restAutorMockMvc.perform(post("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutors() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get all the autorList
        restAutorMockMvc.perform(get("/api/autors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAutorsWithEagerRelationshipsIsEnabled() throws Exception {
        AutorResource autorResource = new AutorResource(autorServiceMock);
        when(autorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAutorMockMvc = MockMvcBuilders.standaloneSetup(autorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAutorMockMvc.perform(get("/api/autors?eagerload=true"))
        .andExpect(status().isOk());

        verify(autorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAutorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AutorResource autorResource = new AutorResource(autorServiceMock);
            when(autorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAutorMockMvc = MockMvcBuilders.standaloneSetup(autorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAutorMockMvc.perform(get("/api/autors?eagerload=true"))
        .andExpect(status().isOk());

            verify(autorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAutor() throws Exception {
        // Initialize the database
        autorRepository.saveAndFlush(autor);

        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", autor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutor() throws Exception {
        // Get the autor
        restAutorMockMvc.perform(get("/api/autors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutor() throws Exception {
        // Initialize the database
        autorService.save(autor);

        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Update the autor
        Autor updatedAutor = autorRepository.findById(autor.getId()).get();
        // Disconnect from session so that the updates on updatedAutor are not directly saved in db
        em.detach(updatedAutor);
        updatedAutor
            .nombre(UPDATED_NOMBRE)
            .web(UPDATED_WEB)
            .email(UPDATED_EMAIL);

        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAutor)))
            .andExpect(status().isOk());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);
        Autor testAutor = autorList.get(autorList.size() - 1);
        assertThat(testAutor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutor.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testAutor.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAutor() throws Exception {
        int databaseSizeBeforeUpdate = autorRepository.findAll().size();

        // Create the Autor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorMockMvc.perform(put("/api/autors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autor)))
            .andExpect(status().isBadRequest());

        // Validate the Autor in the database
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutor() throws Exception {
        // Initialize the database
        autorService.save(autor);

        int databaseSizeBeforeDelete = autorRepository.findAll().size();

        // Delete the autor
        restAutorMockMvc.perform(delete("/api/autors/{id}", autor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Autor> autorList = autorRepository.findAll();
        assertThat(autorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Autor.class);
        Autor autor1 = new Autor();
        autor1.setId(1L);
        Autor autor2 = new Autor();
        autor2.setId(autor1.getId());
        assertThat(autor1).isEqualTo(autor2);
        autor2.setId(2L);
        assertThat(autor1).isNotEqualTo(autor2);
        autor1.setId(null);
        assertThat(autor1).isNotEqualTo(autor2);
    }
}
