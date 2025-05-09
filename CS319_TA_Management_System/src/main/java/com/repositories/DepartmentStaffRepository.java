package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.DepartmentStaff;

@Repository
public interface DepartmentStaffRepository extends JpaRepository<DepartmentStaff, Integer> {
}