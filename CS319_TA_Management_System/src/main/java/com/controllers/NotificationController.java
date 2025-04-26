package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.Notification;
import com.services.NotificationService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public List<Notification> listNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/notification/{notificationId}")
    public Notification getNotification(@PathVariable Integer notificationId) {
        return notificationService.getNotificationById(notificationId);
    }

    @DeleteMapping("/delete-notification/{notificationId}")
    public void deleteNotification(@PathVariable Integer notificationId) {
        notificationService.deleteNotificationById(notificationId);
    }

    @PutMapping("/update-notification/{notificationId}")
    public Notification updateNotification(@PathVariable Integer notificationId,
            @RequestBody Notification notification) {
        return notificationService.updateNotification(notificationId, notification);
    }

 
    @PostMapping("/send-notification")
    public boolean sendNotification(
            @RequestParam Integer userId,
            @RequestParam String description) {
        return notificationService.sendNotification(userId, description);
    }


    @PostMapping("/send-notification-with-email")
    public boolean sendNotificationWithEmail(
            @RequestParam Integer userId,
            @RequestParam String description, @RequestParam String email,

            @RequestParam String subject) {
        return notificationService.sendNotificationWithEmail(userId, description, email, subject);
    }

    @PostMapping("/mark-notification-read/{notificationId}")
    public boolean markNotificationAsRead(@PathVariable Integer notificationId) {
        return notificationService.markNotificationAsRead(notificationId);
    }


    // @GetMapping("/user/{userId}")
    // public List<Notification> getNotificationsByUser(@PathVariable Integer userId) {
    //     return notificationService.getNotificationsByUserId(userId);
    // }

    // @GetMapping("/user/{userId}/unread")
    // public List<Notification> getUnreadNotificationsByUser(@PathVariable Integer userId) {
    //     return notificationService.getUnreadNotificationsByUserId(userId);
    // }
}