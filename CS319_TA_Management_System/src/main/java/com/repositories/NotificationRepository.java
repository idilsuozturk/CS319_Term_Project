package com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findByRequestIDAndStatusNot(int requestID, int status);
}