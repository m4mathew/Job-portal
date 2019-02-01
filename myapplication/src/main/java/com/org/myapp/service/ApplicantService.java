package com.org.myapp.service;

import com.org.myapp.domain.Applicant;
import com.org.myapp.repository.ApplicantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Applicant.
 */
@Service
@Transactional
public class ApplicantService {

    private final Logger log = LoggerFactory.getLogger(ApplicantService.class);

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    /**
     * Save a applicant.
     *
     * @param applicant the entity to save
     * @return the persisted entity
     */
    public Applicant save(Applicant applicant) {
        log.debug("Request to save Applicant : {}", applicant);        return applicantRepository.save(applicant);
    }

    /**
     * Get all the applicants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Applicant> findAll() {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll();
    }


    /**
     * Get one applicant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Applicant> findOne(Long id) {
        log.debug("Request to get Applicant : {}", id);
        return applicantRepository.findById(id);
    }

    /**
     * Delete the applicant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
    }
}
