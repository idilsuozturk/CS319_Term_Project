package com.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.DepartmentStaff;

import io.micrometer.common.lang.NonNull;

@Repository
public interface DepartmentStaffRepository extends JpaRepository<DepartmentStaff, Integer> {
    List<DepartmentStaff> findAll();

    Optional<DepartmentStaff> findById(@SuppressWarnings("null") @RequestParam(value = "id") Integer id);

    DepartmentStaff save(@NonNull DepartmentStaff departmentStaff);

    void deleteById(@RequestParam(value = "id") Integer id);

}