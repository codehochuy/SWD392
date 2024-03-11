package com.example.swd392.repository;

import com.example.swd392.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :fromDate AND :toDate")
    List<Order> findByOrderDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

}
