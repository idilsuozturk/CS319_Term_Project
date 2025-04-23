package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Task;
import com.entities.Course;
import com.entities.TA;

import io.micrometer.common.lang.NonNull;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll();

    Optional<Task> findById(@SuppressWarnings("null") @RequestParam(value = "taskId") Integer taskId);

    Task save(@NonNull Task task);

    void deleteById(@RequestParam(value = "taskId") Integer taskId);

}