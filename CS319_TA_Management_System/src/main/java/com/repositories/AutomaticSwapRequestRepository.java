package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.AutomaticSwapRequest;

@Repository
public interface AutomaticSwapRequestRepository extends JpaRepository<AutomaticSwapRequest, Integer> {

}
