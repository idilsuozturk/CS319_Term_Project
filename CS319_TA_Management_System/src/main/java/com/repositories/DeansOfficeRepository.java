package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.DeansOffice;

@Repository
public interface DeansOfficeRepository extends JpaRepository<DeansOffice, Integer> {
}
