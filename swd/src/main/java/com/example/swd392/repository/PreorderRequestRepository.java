package com.example.swd392.repository;

import com.example.swd392.model.PreorderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreorderRequestRepository extends JpaRepository<PreorderRequest, Integer> {

}