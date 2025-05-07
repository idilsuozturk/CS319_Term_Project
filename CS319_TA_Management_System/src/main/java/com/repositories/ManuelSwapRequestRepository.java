package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.ManuelSwapRequest;

@Repository
public interface ManuelSwapRequestRepository extends JpaRepository<ManuelSwapRequest, Integer> {

}
