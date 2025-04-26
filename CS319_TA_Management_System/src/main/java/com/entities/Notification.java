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
    private Integer notificationId;

    private Boolean isRead;
    private Integer userId;
    private String description;

    // same thing
    public Notification() {
        this.notificationId = null;
        this.isRead = false;;
        this.userId = null;


        this.description = null;
    }


    public Notification(Integer notificationId, Boolean isRead, Integer userId, String description) {
        this.notificationId = notificationId;
        this.isRead = isRead;
        this.userId = userId;
        this.description = description;
    }

    // getters and 
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {

        // questinable to implement
        this.isRead = isRead;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}