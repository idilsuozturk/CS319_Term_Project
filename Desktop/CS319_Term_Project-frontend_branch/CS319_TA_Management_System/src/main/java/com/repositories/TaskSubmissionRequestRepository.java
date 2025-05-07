package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.TaskSubmissionRequest;

@Repository
public interface TaskSubmissionRequestRepository extends JpaRepository<TaskSubmissionRequest, Integer> {

}
