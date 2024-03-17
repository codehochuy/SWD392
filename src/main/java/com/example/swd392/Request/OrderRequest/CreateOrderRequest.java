package com.example.swd392.Request.OrderRequest;

import com.example.swd392.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private int audience;
    private double orderPrice;

}
