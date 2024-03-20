package com.example.swd392.repository;

import com.example.swd392.model.Artwork;
import com.example.swd392.model.Order;
import com.example.swd392.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrder_OrderId(int orderId);
    List<OrderDetail> findOrderDetailByOrder(Order order);
    List<OrderDetail> findOrderDetailByArtwork(Artwork artwork);
}
