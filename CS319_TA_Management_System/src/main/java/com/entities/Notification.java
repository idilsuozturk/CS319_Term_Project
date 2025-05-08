package com.entities;

import jakarta.persistence.Column;
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
    @Column(name = "notification_id") // Specify the exact column name
    private Integer notificationID;

    @Column(name = "request_date") // Specify the exact column name
    private String requestDate;

    @Column(name = "requestid") // Specify the exact column name
    private Integer requestID;

    @Column(name = "status") // Specify the exact column name
    private int status;

    @Column(name = "description") // Add the missing field for description
    private String description;

    @Column(name = "is_read") // Add the missing field for is_read
    private boolean isRead;

    @Column(name = "user_id") // Add the missing field for user_id
    private Integer userId;

    public Notification() {
        this.requestDate = null;
        this.requestID = -1;
        this.status = -1;
        this.description = null;
        this.isRead = false;
        this.userId = null;
    }

    public Notification(String requestDate, Integer requestID, int status, String description, boolean isRead, Integer userId) {
        this.requestDate = requestDate;
        this.requestID = requestID;
        this.status = status;
        this.description = description;
        this.isRead = isRead;
        this.userId = userId;
    }

    // Getters and setters
    public Integer getNotificationID() {
        return notificationID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
