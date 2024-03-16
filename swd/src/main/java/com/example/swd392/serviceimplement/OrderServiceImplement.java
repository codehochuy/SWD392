package com.example.swd392.serviceimplement;

import com.example.swd392.Response.OrderResponse.OrderResponse;
import com.example.swd392.Response.PackageResponse.PackageResponse;
import com.example.swd392.model.Order;
import com.example.swd392.model.Package;
import com.example.swd392.repository.OrderRepo;
import com.example.swd392.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplement implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Override
    public ResponseEntity<OrderResponse> getAll() {
        List<Order> orders = orderRepo.findAll();
        OrderResponse response = new OrderResponse();
        response.setStatus("success");
        response.setMessage("Orders retrieved successfully");
        response.setOrders(orders);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<OrderResponse> deleteOrder(int orderId) {
        // Check if the package exists
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            // Package found, delete it
            orderRepo.deleteById(orderId);

            // Prepare the response
            OrderResponse response = OrderResponse.builder()
                    .status("Success")
                    .message("Order deleted successfully")
                    .build();

            return ResponseEntity.ok(response);
        } else {
            // Package not found, return an appropriate error response
            OrderResponse response = OrderResponse.builder()
                    .status("Error")
                    .message("Package not found")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


        @Override
        public ResponseEntity<OrderResponse> searchOrders(LocalDate fromDate, LocalDate toDate) {
            List<Order> orders = orderRepo.findByOrderDateBetween(fromDate.atStartOfDay(), toDate.atTime(23, 59, 59));

            OrderResponse response;
            if (!orders.isEmpty()) {
                response = OrderResponse.builder()
                        .status("Success")
                        .message("Orders found within the date range")
                        .orders(orders)
                        .build();
                return ResponseEntity.ok(response);
            } else {
                response = OrderResponse.builder()
                        .status("Error")
                        .message("No orders found within the date range")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }


}
