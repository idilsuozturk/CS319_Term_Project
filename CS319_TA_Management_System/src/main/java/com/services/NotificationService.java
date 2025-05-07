package com.services;


import org.springframework.stereotype.Service;

import com.entities.Notification;
import com.repositories.NotificationRepository;

import java.util.List;

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

    public void deleteNotificationById(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    public Notification updateNotification(Integer notificationId, Notification notification) {
        Notification existingNotification = notificationRepository.findById(notificationId).orElse(null);
        if (existingNotification != null) {
            existingNotification.setRequestId(notification.getRequestId());
        }
        return null;
    }
    
    public boolean sendNotification(Integer requestID) {
        try {
            Notification notification = new Notification();
            notification.setRequestId(requestID);
            notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
