package com.org.myapp.web.rest;

import com.org.myapp.MyappApp;

import com.org.myapp.domain.Applicant;
import com.org.myapp.repository.ApplicantRepository;
import com.org.myapp.service.ApplicantService;
import com.org.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.org.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApplicantResource REST controller.
 *
 * @see ApplicantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class ApplicantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "SJ@A.uO";
    private static final String UPDATED_EMAIL = "NU@p.bU";

    private static final byte[] DEFAULT_RESUME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESUME = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_RESUME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESUME_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHONE = "0462538843";
    private static final String UPDATED_PHONE = "1300406882";

    @Autowired
    private ApplicantRepository applicantRepository;

    

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicantMockMvc;

    private Applicant applicant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicantResource applicantResource = new ApplicantResource(applicantService);
        this.restApplicantMockMvc = MockMvcBuilders.standaloneSetup(applicantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Applicant createEntity(EntityManager em) {
        Applicant applicant = new Applicant()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .resume(DEFAULT_RESUME)
            .resumeContentType(DEFAULT_RESUME_CONTENT_TYPE)
            .phone(DEFAULT_PHONE);
        return applicant;
    }

    @Before
    public void initTest() {
        applicant = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicant() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isCreated());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate + 1);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testApplicant.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testApplicant.getResumeContentType()).isEqualTo(DEFAULT_RESUME_CONTENT_TYPE);
        assertThat(testApplicant.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createApplicantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicantRepository.findAll().size();

        // Create the Applicant with an existing ID
        applicant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setName(null);

        // Create the Applicant, which fails.

        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setEmail(null);

        // Create the Applicant, which fails.

        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicantRepository.findAll().size();
        // set the field null
        applicant.setPhone(null);

        // Create the Applicant, which fails.

        restApplicantMockMvc.perform(post("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApplicants() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get all the applicantList
        restApplicantMockMvc.perform(get("/api/applicants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].resumeContentType").value(hasItem(DEFAULT_RESUME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESUME))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    

    @Test
    @Transactional
    public void getApplicant() throws Exception {
        // Initialize the database
        applicantRepository.saveAndFlush(applicant);

        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", applicant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.resumeContentType").value(DEFAULT_RESUME_CONTENT_TYPE))
            .andExpect(jsonPath("$.resume").value(Base64Utils.encodeToString(DEFAULT_RESUME)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingApplicant() throws Exception {
        // Get the applicant
        restApplicantMockMvc.perform(get("/api/applicants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicant() throws Exception {
        // Initialize the database
        applicantService.save(applicant);

        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Update the applicant
        Applicant updatedApplicant = applicantRepository.findById(applicant.getId()).get();
        // Disconnect from session so that the updates on updatedApplicant are not directly saved in db
        em.detach(updatedApplicant);
        updatedApplicant
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE)
            .phone(UPDATED_PHONE);

        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicant)))
            .andExpect(status().isOk());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
        Applicant testApplicant = applicantList.get(applicantList.size() - 1);
        assertThat(testApplicant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testApplicant.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testApplicant.getResumeContentType()).isEqualTo(UPDATED_RESUME_CONTENT_TYPE);
        assertThat(testApplicant.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicant() throws Exception {
        int databaseSizeBeforeUpdate = applicantRepository.findAll().size();

        // Create the Applicant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplicantMockMvc.perform(put("/api/applicants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicant)))
            .andExpect(status().isBadRequest());

        // Validate the Applicant in the database
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicant() throws Exception {
        // Initialize the database
        applicantService.save(applicant);

        int databaseSizeBeforeDelete = applicantRepository.findAll().size();

        // Get the applicant
        restApplicantMockMvc.perform(delete("/api/applicants/{id}", applicant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Applicant> applicantList = applicantRepository.findAll();
        assertThat(applicantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicant.class);
        Applicant applicant1 = new Applicant();
        applicant1.setId(1L);
        Applicant applicant2 = new Applicant();
        applicant2.setId(applicant1.getId());
        assertThat(applicant1).isEqualTo(applicant2);
        applicant2.setId(2L);
        assertThat(applicant1).isNotEqualTo(applicant2);
        applicant1.setId(null);
        assertThat(applicant1).isNotEqualTo(applicant2);
    }
}
