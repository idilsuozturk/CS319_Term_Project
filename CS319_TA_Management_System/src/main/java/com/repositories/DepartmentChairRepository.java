package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entities.DepartmentChair;

@Repository
public interface DepartmentChairRepository extends JpaRepository<DepartmentChair, Integer> {
}