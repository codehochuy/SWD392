package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int orderId;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "OrderPrice")
    private double orderPrice;

    @ManyToOne
    @JoinColumn(name = "AudienceID")
    private User audience;

    @ManyToOne
    @JoinColumn(name = "UsersID")
    private User creator;
}
