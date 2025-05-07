package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "absences")
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String date;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private TA ta;

    private String reason;

    // Status info regarding the absence requrest
    private String status; //  "APPROVED", "REJECTED"

    public Absence() {
        this.id = null;
        this.date = null;
        this.ta = null;
        this.reason = null;

        this.status = "PENDING";
    }

    // Parameterized constructor
    public Absence(Integer id, String date, TA ta, String reason) {
        this.id = id;
        this.date = date;
        this.ta = ta;
        this.reason = reason;
        this.status = "PENDING";
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TA getTa() {
        return ta;
    }

    public void setTa(TA ta) {
        this.ta = ta;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}