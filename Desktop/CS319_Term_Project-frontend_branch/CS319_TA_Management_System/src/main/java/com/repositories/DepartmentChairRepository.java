package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.entities.DepartmentChair;

@Repository
public interface DepartmentChairRepository extends JpaRepository<DepartmentChair, Integer> {
    
    List<DepartmentChair> findAll();
    
    Optional<DepartmentChair> findById(Integer id);
    
    DepartmentChair save(DepartmentChair departmentChair);    
    
    void deleteById(Integer id);

    
    
  
}