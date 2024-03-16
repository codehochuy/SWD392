package com.example.swd392.Response.OrderResponse;

import com.example.swd392.model.Order;
import com.example.swd392.model.Package;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String status;
    private String message;
    private List<Order> orders;
}
