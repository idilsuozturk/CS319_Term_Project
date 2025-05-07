package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Admin;

import io.micrometer.common.lang.NonNull;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findAll();

    Optional<Admin> findById(@SuppressWarnings("null") @RequestParam(value = "id") Integer id);

    Admin save(@NonNull Admin admin);

    void deleteById(@RequestParam(value = "id") Integer id);

}