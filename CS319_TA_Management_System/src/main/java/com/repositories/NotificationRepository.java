package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAll();

    Optional<Notification> findById(Integer notificationId);

    Notification save(Notification notification);

    void deleteById(Integer notificationId);

    List<Notification> findByUserIdAndIsRead(Integer userId, Boolean isRead);
}
