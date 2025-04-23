package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Absence;
import com.entities.TA;

import io.micrometer.common.lang.NonNull;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
    List<Absence> findAll();

    Optional<Absence> findById(@SuppressWarnings("null") @RequestParam(value = "id") Integer id);

    Absence save(@NonNull Absence absence);

    void deleteById(@RequestParam(value = "id") Integer id);

    List<Absence> findByTaId(Integer taId);

    // filter by status
    List<Absence> findByStatus(String status);

}