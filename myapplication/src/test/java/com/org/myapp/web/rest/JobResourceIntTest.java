package com.org.myapp.web.rest;

import com.org.myapp.MyappApp;

import com.org.myapp.domain.Job;
import com.org.myapp.repository.JobRepository;
import com.org.myapp.service.JobService;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.org.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class JobResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_WORKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_WORKTYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ONLINE = false;
    private static final Boolean UPDATED_ONLINE = true;

    private static final Boolean DEFAULT_TELE = false;
    private static final Boolean UPDATED_TELE = true;

    private static final Boolean DEFAULT_FTF = false;
    private static final Boolean UPDATED_FTF = true;

    private static final Boolean DEFAULT_HR = false;
    private static final Boolean UPDATED_HR = true;

    private static final Boolean DEFAULT_PSYCHOMETRIC = false;
    private static final Boolean UPDATED_PSYCHOMETRIC = true;

    private static final String DEFAULT_DESIREDSKILLS = "AAAAAAAAAA";
    private static final String UPDATED_DESIREDSKILLS = "BBBBBBBBBB";

    @Autowired
    private JobRepository jobRepository;

    

    @Autowired
    private JobService jobService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobMockMvc;

    private Job job;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobResource jobResource = new JobResource(jobService);
        this.restJobMockMvc = MockMvcBuilders.standaloneSetup(jobResource)
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
    public static Job createEntity(EntityManager em) {
        Job job = new Job()
            .title(DEFAULT_TITLE)
            .company(DEFAULT_COMPANY)
            .location(DEFAULT_LOCATION)
            .skills(DEFAULT_SKILLS)
            .desc(DEFAULT_DESC)
            .worktype(DEFAULT_WORKTYPE)
            .online(DEFAULT_ONLINE)
            .tele(DEFAULT_TELE)
            .ftf(DEFAULT_FTF)
            .hr(DEFAULT_HR)
            .psychometric(DEFAULT_PSYCHOMETRIC)
            .desiredskills(DEFAULT_DESIREDSKILLS);
        return job;
    }

    @Before
    public void initTest() {
        job = createEntity(em);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJob.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testJob.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJob.getSkills()).isEqualTo(DEFAULT_SKILLS);
        assertThat(testJob.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testJob.getWorktype()).isEqualTo(DEFAULT_WORKTYPE);
        assertThat(testJob.isOnline()).isEqualTo(DEFAULT_ONLINE);
        assertThat(testJob.isTele()).isEqualTo(DEFAULT_TELE);
        assertThat(testJob.isFtf()).isEqualTo(DEFAULT_FTF);
        assertThat(testJob.isHr()).isEqualTo(DEFAULT_HR);
        assertThat(testJob.isPsychometric()).isEqualTo(DEFAULT_PSYCHOMETRIC);
        assertThat(testJob.getDesiredskills()).isEqualTo(DEFAULT_DESIREDSKILLS);
    }

    @Test
    @Transactional
    public void createJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job with an existing ID
        job.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setTitle(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSkillsIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setSkills(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setDesc(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWorktypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setWorktype(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].skills").value(hasItem(DEFAULT_SKILLS.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].worktype").value(hasItem(DEFAULT_WORKTYPE.toString())))
            .andExpect(jsonPath("$.[*].online").value(hasItem(DEFAULT_ONLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].tele").value(hasItem(DEFAULT_TELE.booleanValue())))
            .andExpect(jsonPath("$.[*].ftf").value(hasItem(DEFAULT_FTF.booleanValue())))
            .andExpect(jsonPath("$.[*].hr").value(hasItem(DEFAULT_HR.booleanValue())))
            .andExpect(jsonPath("$.[*].psychometric").value(hasItem(DEFAULT_PSYCHOMETRIC.booleanValue())))
            .andExpect(jsonPath("$.[*].desiredskills").value(hasItem(DEFAULT_DESIREDSKILLS.toString())));
    }
    

    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.skills").value(DEFAULT_SKILLS.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.worktype").value(DEFAULT_WORKTYPE.toString()))
            .andExpect(jsonPath("$.online").value(DEFAULT_ONLINE.booleanValue()))
            .andExpect(jsonPath("$.tele").value(DEFAULT_TELE.booleanValue()))
            .andExpect(jsonPath("$.ftf").value(DEFAULT_FTF.booleanValue()))
            .andExpect(jsonPath("$.hr").value(DEFAULT_HR.booleanValue()))
            .andExpect(jsonPath("$.psychometric").value(DEFAULT_PSYCHOMETRIC.booleanValue()))
            .andExpect(jsonPath("$.desiredskills").value(DEFAULT_DESIREDSKILLS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobService.save(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        Job updatedJob = jobRepository.findById(job.getId()).get();
        // Disconnect from session so that the updates on updatedJob are not directly saved in db
        em.detach(updatedJob);
        updatedJob
            .title(UPDATED_TITLE)
            .company(UPDATED_COMPANY)
            .location(UPDATED_LOCATION)
            .skills(UPDATED_SKILLS)
            .desc(UPDATED_DESC)
            .worktype(UPDATED_WORKTYPE)
            .online(UPDATED_ONLINE)
            .tele(UPDATED_TELE)
            .ftf(UPDATED_FTF)
            .hr(UPDATED_HR)
            .psychometric(UPDATED_PSYCHOMETRIC)
            .desiredskills(UPDATED_DESIREDSKILLS);

        restJobMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJob)))
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJob.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testJob.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJob.getSkills()).isEqualTo(UPDATED_SKILLS);
        assertThat(testJob.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testJob.getWorktype()).isEqualTo(UPDATED_WORKTYPE);
        assertThat(testJob.isOnline()).isEqualTo(UPDATED_ONLINE);
        assertThat(testJob.isTele()).isEqualTo(UPDATED_TELE);
        assertThat(testJob.isFtf()).isEqualTo(UPDATED_FTF);
        assertThat(testJob.isHr()).isEqualTo(UPDATED_HR);
        assertThat(testJob.isPsychometric()).isEqualTo(UPDATED_PSYCHOMETRIC);
        assertThat(testJob.getDesiredskills()).isEqualTo(UPDATED_DESIREDSKILLS);
    }

    @Test
    @Transactional
    public void updateNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Create the Job

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobService.save(job);

        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Job.class);
        Job job1 = new Job();
        job1.setId(1L);
        Job job2 = new Job();
        job2.setId(job1.getId());
        assertThat(job1).isEqualTo(job2);
        job2.setId(2L);
        assertThat(job1).isNotEqualTo(job2);
        job1.setId(null);
        assertThat(job1).isNotEqualTo(job2);
    }
}
