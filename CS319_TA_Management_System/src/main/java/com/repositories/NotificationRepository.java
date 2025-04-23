package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Notification;
import io.micrometer.common.lang.NonNull;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAll();

    Optional<Notification> findById(
            @SuppressWarnings("null") @RequestParam(value = "notificationId") Integer notificationId);

    Notification save(@NonNull Notification notification);

    void deleteById(@RequestParam(value = "notificationId") Integer notificationId);

    // might need
    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndIsRead(Integer userId, Boolean isRead);
}