package com.example.swd392.Response.OrderDetailResponse;

import com.example.swd392.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private String status;
    private String message;
//    private String orderID;
    private List<OrderDetail> orderDetails;
}
