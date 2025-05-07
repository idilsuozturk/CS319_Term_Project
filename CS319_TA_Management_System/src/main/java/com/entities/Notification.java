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
    private Integer requestID;

    public Notification() {
        requestID = -1;
    }


    public Notification(Integer requestID) {
        this.requestID = requestID;
    }

    // getters and 
    public Integer getID() {
        return notificationID;
    }

    public Integer getRequestId() {
        return requestID;
    }

    public void setRequestId(Integer requestID) {
        this.requestID = requestID;
    }
}
