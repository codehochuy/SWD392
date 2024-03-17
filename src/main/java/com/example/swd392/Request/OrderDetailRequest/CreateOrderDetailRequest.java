package com.example.swd392.Request.OrderDetailRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailRequest {
    private int orderId;
    private int audience;

}
