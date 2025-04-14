package com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tas")
public class TA extends User {

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] currentAssistingCourses;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] currentTakingCourses;

    Integer advisor; // instructor ID number

    private Integer totalWorkload;

    private Integer tcNumber;

    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] proctoringExams;

    public TA() {
        super();
        this.currentAssistingCourses = null;

        this.currentTakingCourses = null;
        this.advisor = 0;
        this.totalWorkload = 0;
        this.tcNumber = 0;
        this.proctoringExams = null;
    }

    // Too much variables?
    public TA(Integer id, String email, String userName, String password, Integer[] currentAssistingCourses,
            Integer[] currentTakingCourses,

            Integer advisor, Integer totalWorkload, Integer tcNumber,
            Integer[] proctoringExams) {
        super(id, email, userName, password);
        this.currentAssistingCourses = currentAssistingCourses;
        this.currentTakingCourses = currentTakingCourses;
        this.advisor = advisor;
        this.totalWorkload = totalWorkload;
        this.tcNumber = tcNumber;
        this.proctoringExams = proctoringExams;
    }

    // Get and set functions

    public Integer[] getCurrentAssistingCourses() {
        return currentAssistingCourses;
    }

    public void setCurrentAssistingCourses(Integer[] currentAssistingCourses) {
        this.currentAssistingCourses = currentAssistingCourses;
    }

    public Integer[] getCurrentTakingCourses() {
        return currentTakingCourses;
    }

    public void setCurrentTakingCourses(Integer[] currentTakingCourses) {
        this.currentTakingCourses = currentTakingCourses;
    }

    public Integer getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Integer advisor) {
        this.advisor = advisor;
    }

    public Integer getTotalWorkload() {
        return totalWorkload;
    }

    public void setTotalWorkload(Integer totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    public Integer getTcNumber() {
        return tcNumber;
    }

<<<<<<< HEAD
    // should we even allow this?
=======

    // should we even allow this? 
>>>>>>> 151bd4a (Initial Workings of TA class.)
    public void setTcNumber(Integer tcNumber) {
        this.tcNumber = tcNumber;
    }

    public Integer[] getProctoringExams() {
        return proctoringExams;
    }

    public void setProctoringExams(Integer[] proctoringExams) {
        this.proctoringExams = proctoringExams;
    }
}