package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.ProctoringAssignment;

@Repository
public interface ProctoringAssignmentRepository extends JpaRepository<ProctoringAssignment, Integer> {

}