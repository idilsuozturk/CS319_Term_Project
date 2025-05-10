package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}
