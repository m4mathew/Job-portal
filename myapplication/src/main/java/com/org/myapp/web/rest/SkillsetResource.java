package com.org.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.myapp.domain.Skillset;
import com.org.myapp.repository.SkillsetRepository;
import com.org.myapp.web.rest.errors.BadRequestAlertException;
import com.org.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Skillset.
 */
@RestController
@RequestMapping("/api")
public class SkillsetResource {

    private final Logger log = LoggerFactory.getLogger(SkillsetResource.class);

    private static final String ENTITY_NAME = "skillset";

    private final SkillsetRepository skillsetRepository;

    public SkillsetResource(SkillsetRepository skillsetRepository) {
        this.skillsetRepository = skillsetRepository;
    }

    /**
     * POST  /skillsets : Create a new skillset.
     *
     * @param skillset the skillset to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillset, or with status 400 (Bad Request) if the skillset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skillsets")
    @Timed
    public ResponseEntity<Skillset> createSkillset(@RequestBody Skillset skillset) throws URISyntaxException {
        log.debug("REST request to save Skillset : {}", skillset);
        if (skillset.getId() != null) {
            throw new BadRequestAlertException("A new skillset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skillset result = skillsetRepository.save(skillset);
        return ResponseEntity.created(new URI("/api/skillsets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skillsets : Updates an existing skillset.
     *
     * @param skillset the skillset to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillset,
     * or with status 400 (Bad Request) if the skillset is not valid,
     * or with status 500 (Internal Server Error) if the skillset couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skillsets")
    @Timed
    public ResponseEntity<Skillset> updateSkillset(@RequestBody Skillset skillset) throws URISyntaxException {
        log.debug("REST request to update Skillset : {}", skillset);
        if (skillset.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skillset result = skillsetRepository.save(skillset);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillset.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skillsets : get all the skillsets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillsets in body
     */
    @GetMapping("/skillsets")
    @Timed
    public List<Skillset> getAllSkillsets() {
        log.debug("REST request to get all Skillsets");
        return skillsetRepository.findAll();
    }

    /**
     * GET  /skillsets/:id : get the "id" skillset.
     *
     * @param id the id of the skillset to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillset, or with status 404 (Not Found)
     */
    @GetMapping("/skillsets/{id}")
    @Timed
    public ResponseEntity<Skillset> getSkillset(@PathVariable Long id) {
        log.debug("REST request to get Skillset : {}", id);
        Optional<Skillset> skillset = skillsetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skillset);
    }

    /**
     * DELETE  /skillsets/:id : delete the "id" skillset.
     *
     * @param id the id of the skillset to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skillsets/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillset(@PathVariable Long id) {
        log.debug("REST request to delete Skillset : {}", id);

        skillsetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
