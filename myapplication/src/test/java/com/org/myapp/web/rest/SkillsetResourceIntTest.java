package com.org.myapp.web.rest;

import com.org.myapp.MyappApp;

import com.org.myapp.domain.Skillset;
import com.org.myapp.repository.SkillsetRepository;
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
 * Test class for the SkillsetResource REST controller.
 *
 * @see SkillsetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyappApp.class)
public class SkillsetResourceIntTest {

    private static final String DEFAULT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBB";

    @Autowired
    private SkillsetRepository skillsetRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillsetMockMvc;

    private Skillset skillset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillsetResource skillsetResource = new SkillsetResource(skillsetRepository);
        this.restSkillsetMockMvc = MockMvcBuilders.standaloneSetup(skillsetResource)
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
    public static Skillset createEntity(EntityManager em) {
        Skillset skillset = new Skillset()
            .skill(DEFAULT_SKILL);
        return skillset;
    }

    @Before
    public void initTest() {
        skillset = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillset() throws Exception {
        int databaseSizeBeforeCreate = skillsetRepository.findAll().size();

        // Create the Skillset
        restSkillsetMockMvc.perform(post("/api/skillsets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillset)))
            .andExpect(status().isCreated());

        // Validate the Skillset in the database
        List<Skillset> skillsetList = skillsetRepository.findAll();
        assertThat(skillsetList).hasSize(databaseSizeBeforeCreate + 1);
        Skillset testSkillset = skillsetList.get(skillsetList.size() - 1);
        assertThat(testSkillset.getSkill()).isEqualTo(DEFAULT_SKILL);
    }

    @Test
    @Transactional
    public void createSkillsetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillsetRepository.findAll().size();

        // Create the Skillset with an existing ID
        skillset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillsetMockMvc.perform(post("/api/skillsets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillset)))
            .andExpect(status().isBadRequest());

        // Validate the Skillset in the database
        List<Skillset> skillsetList = skillsetRepository.findAll();
        assertThat(skillsetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSkillsets() throws Exception {
        // Initialize the database
        skillsetRepository.saveAndFlush(skillset);

        // Get all the skillsetList
        restSkillsetMockMvc.perform(get("/api/skillsets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillset.getId().intValue())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())));
    }
    

    @Test
    @Transactional
    public void getSkillset() throws Exception {
        // Initialize the database
        skillsetRepository.saveAndFlush(skillset);

        // Get the skillset
        restSkillsetMockMvc.perform(get("/api/skillsets/{id}", skillset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillset.getId().intValue()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSkillset() throws Exception {
        // Get the skillset
        restSkillsetMockMvc.perform(get("/api/skillsets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillset() throws Exception {
        // Initialize the database
        skillsetRepository.saveAndFlush(skillset);

        int databaseSizeBeforeUpdate = skillsetRepository.findAll().size();

        // Update the skillset
        Skillset updatedSkillset = skillsetRepository.findById(skillset.getId()).get();
        // Disconnect from session so that the updates on updatedSkillset are not directly saved in db
        em.detach(updatedSkillset);
        updatedSkillset
            .skill(UPDATED_SKILL);

        restSkillsetMockMvc.perform(put("/api/skillsets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillset)))
            .andExpect(status().isOk());

        // Validate the Skillset in the database
        List<Skillset> skillsetList = skillsetRepository.findAll();
        assertThat(skillsetList).hasSize(databaseSizeBeforeUpdate);
        Skillset testSkillset = skillsetList.get(skillsetList.size() - 1);
        assertThat(testSkillset.getSkill()).isEqualTo(UPDATED_SKILL);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillset() throws Exception {
        int databaseSizeBeforeUpdate = skillsetRepository.findAll().size();

        // Create the Skillset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillsetMockMvc.perform(put("/api/skillsets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillset)))
            .andExpect(status().isBadRequest());

        // Validate the Skillset in the database
        List<Skillset> skillsetList = skillsetRepository.findAll();
        assertThat(skillsetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillset() throws Exception {
        // Initialize the database
        skillsetRepository.saveAndFlush(skillset);

        int databaseSizeBeforeDelete = skillsetRepository.findAll().size();

        // Get the skillset
        restSkillsetMockMvc.perform(delete("/api/skillsets/{id}", skillset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Skillset> skillsetList = skillsetRepository.findAll();
        assertThat(skillsetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skillset.class);
        Skillset skillset1 = new Skillset();
        skillset1.setId(1L);
        Skillset skillset2 = new Skillset();
        skillset2.setId(skillset1.getId());
        assertThat(skillset1).isEqualTo(skillset2);
        skillset2.setId(2L);
        assertThat(skillset1).isNotEqualTo(skillset2);
        skillset1.setId(null);
        assertThat(skillset1).isNotEqualTo(skillset2);
    }
}
