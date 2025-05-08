package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
}
