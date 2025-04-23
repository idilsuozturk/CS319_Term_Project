package com.services;


import org.springframework.stereotype.Service;

import com.entities.Notification;
import com.repositories.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public NotificationService(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
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
            existingNotification.setIsRead(notification.getIsRead());

            existingNotification.setUserId(notification.getUserId());
            existingNotification.setDescription(notification.getDescription());
            return notificationRepository.save(existingNotification);
        }
        return null;
    }

 


    public boolean sendNotification(Integer id, String description) {
        try {
            // saves the created notif
            Notification notification = new Notification();
            notification.setUserId(id);
            notification.setDescription(description);
            notification.setIsRead(false);

            notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getUnreadNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false);
    }

    // sends notification and email boh at the same time
    public boolean sendNotificationWithEmail(Integer userId, String description, String email, String subject) {
        boolean notificationSent = sendNotification(userId, description);

        if (notificationSent && email != null && !email.isEmpty()) {
            emailService.sendEmail(email, subject, description);
        }

        return notificationSent;
    }

    // func to set a notification as read. But where and when to use? 
    public boolean markNotificationAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setIsRead(true);
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }
}