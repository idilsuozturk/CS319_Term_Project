package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Instructor;

import io.micrometer.common.lang.NonNull;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    List<Instructor> findAll();

    Optional<Instructor> findById(@SuppressWarnings("null") @RequestParam(value = "id") Integer id);

    Instructor save(@NonNull Instructor instructor);

    void deleteById(@RequestParam(value = "id") Integer id);

}