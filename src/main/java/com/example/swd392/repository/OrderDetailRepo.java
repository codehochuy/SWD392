package com.example.swd392.repository;

import com.example.swd392.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrder_OrderId(int orderId);
}