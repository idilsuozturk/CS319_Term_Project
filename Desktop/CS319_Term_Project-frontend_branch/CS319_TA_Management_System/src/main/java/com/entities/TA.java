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



    @Column(columnDefinition = "json")
    @Convert(converter = IntegerArrayToJsonConverter.class)
    private Integer[] proctoringExams;

    public TA() {
        super();
        this.currentAssistingCourses = null;

        this.currentTakingCourses = null;
        this.advisor = 0;
        this.totalWorkload = 0;
    
        this.proctoringExams = null;
    }

    // Too much variables?
    public TA(String name, String email, String userName, String password, Integer[] currentAssistingCourses,
            Integer[] currentTakingCourses, Integer advisor, Integer totalWorkload,
            Integer[] proctoringExams) {
        super( name, email, userName, password, Roles.TA);
        this.currentAssistingCourses = currentAssistingCourses;
        this.currentTakingCourses = currentTakingCourses;
        this.advisor = advisor;
        this.totalWorkload = totalWorkload;
        this.proctoringExams = proctoringExams;
    }

    // Get and set functions
    public Integer getId() {
        return super.getId();
    }

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

    public Integer[] getProctoringExams() {
        return proctoringExams;
    }

    public void setProctoringExams(Integer[] proctoringExams) {
        this.proctoringExams = proctoringExams;
    }
}