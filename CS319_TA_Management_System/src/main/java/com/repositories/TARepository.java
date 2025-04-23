package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.TA;

import io.micrometer.common.lang.NonNull;

// Literally a copy paste from CourseRepository,java

@Repository
public interface TARepository extends JpaRepository<TA, Integer> {
    List<TA> findAll();

    Optional<TA> findById(Integer id);

    TA save(@NonNull TA ta);

    void deleteById(Integer id);

    /*
     * For future additional functions might be needed such as findByname or findByEmail etc.
     */
}
