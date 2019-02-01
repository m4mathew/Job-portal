package com.org.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "skills", nullable = false)
    private String skills;

    @NotNull
    @Column(name = "jhi_desc", nullable = false)
    private String desc;

    @NotNull
    @Column(name = "worktype", nullable = false)
    private String worktype;

    @Column(name = "jhi_online")
    private Boolean online;

    @Column(name = "tele")
    private Boolean tele;

    @Column(name = "ftf")
    private Boolean ftf;

    @Column(name = "hr")
    private Boolean hr;

    @Column(name = "psychometric")
    private Boolean psychometric;

    @Column(name = "desiredskills")
    private String desiredskills;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Job title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public Job company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public Job location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public Job skills(String skills) {
        this.skills = skills;
        return this;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDesc() {
        return desc;
    }

    public Job desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWorktype() {
        return worktype;
    }

    public Job worktype(String worktype) {
        this.worktype = worktype;
        return this;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public Boolean isOnline() {
        return online;
    }

    public Job online(Boolean online) {
        this.online = online;
        return this;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean isTele() {
        return tele;
    }

    public Job tele(Boolean tele) {
        this.tele = tele;
        return this;
    }

    public void setTele(Boolean tele) {
        this.tele = tele;
    }

    public Boolean isFtf() {
        return ftf;
    }

    public Job ftf(Boolean ftf) {
        this.ftf = ftf;
        return this;
    }

    public void setFtf(Boolean ftf) {
        this.ftf = ftf;
    }

    public Boolean isHr() {
        return hr;
    }

    public Job hr(Boolean hr) {
        this.hr = hr;
        return this;
    }

    public void setHr(Boolean hr) {
        this.hr = hr;
    }

    public Boolean isPsychometric() {
        return psychometric;
    }

    public Job psychometric(Boolean psychometric) {
        this.psychometric = psychometric;
        return this;
    }

    public void setPsychometric(Boolean psychometric) {
        this.psychometric = psychometric;
    }

    public String getDesiredskills() {
        return desiredskills;
    }

    public Job desiredskills(String desiredskills) {
        this.desiredskills = desiredskills;
        return this;
    }

    public void setDesiredskills(String desiredskills) {
        this.desiredskills = desiredskills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        if (job.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", company='" + getCompany() + "'" +
            ", location='" + getLocation() + "'" +
            ", skills='" + getSkills() + "'" +
            ", desc='" + getDesc() + "'" +
            ", worktype='" + getWorktype() + "'" +
            ", online='" + isOnline() + "'" +
            ", tele='" + isTele() + "'" +
            ", ftf='" + isFtf() + "'" +
            ", hr='" + isHr() + "'" +
            ", psychometric='" + isPsychometric() + "'" +
            ", desiredskills='" + getDesiredskills() + "'" +
            "}";
    }
}
