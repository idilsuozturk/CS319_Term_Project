package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;

    private String requestDate;
    private Integer requestID;
    private int status;

    public Notification() {
        this.requestDate = null;
        this.requestID = -1;
        this.status = -1;
    }


    public Notification(String requestDate, Integer requestID, int status) {
        this.requestDate = requestDate;
        this.requestID = requestID;
        this.status = status;
    }

    // getters and 
    public Integer getID() {
        return this.notificationID;
    }

    public String getRequestDate(){
        return this.requestDate;
    }

    public void setRequestDate(String requestDate){
        this.requestDate = requestDate;
    }

    public Integer getRequestId() {
        return this.requestID;
    }

    public void setRequestId(Integer requestID) {
        this.requestID = requestID;
    }

    public int getStatus(){
        return this.status;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
