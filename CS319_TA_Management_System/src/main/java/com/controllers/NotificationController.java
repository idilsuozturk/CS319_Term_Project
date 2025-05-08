package com.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Notification;
import com.services.NotificationService;

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

 
    /*
     * @PostMapping("/send-notification")
    public boolean sendNotification(
            @RequestParam Integer requestID) {
        return notificationService.sendNotification(requestID, );
    }
     */
}