package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Notification;
import com.repositories.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Integer notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    public Notification createNotification(String requestDate, Integer requestID, int status, String description, boolean isRead, Integer userId) {
        Notification notification = new Notification(requestDate, requestID, status, description, isRead, userId);
        return notificationRepository.save(notification);
    }

    public void deleteNotificationById(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public Notification updateNotification(Integer notificationId, Notification notification) {
        Notification existingNotification = notificationRepository.findById(notificationId).orElse(null);
        if (existingNotification != null) {

            existingNotification.setRequestDate(notification.getRequestDate());
            existingNotification.setRequestID(notification.getRequestID());
            existingNotification.setDescription(notification.getDescription());
            existingNotification.setIsRead(notification.getIsRead());
            existingNotification.setUserId(notification.getUserId());
            existingNotification.setStatus(notification.getStatus());
            return notificationRepository.save(existingNotification);
        }
        return null;
    }


    public boolean sendNotification(Integer requestID, String description, boolean isRead, Integer userId) {
        try {

            Notification notification = new Notification();
            notification.setRequestID(requestID);
            notification.setDescription(description);
            notification.setIsRead(isRead);
            notification.setUserId(userId);
            notification.setRequestDate("2025-05-08");
            notification.setStatus(0); 

            notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
