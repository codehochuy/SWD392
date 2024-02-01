package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @Column(name = "PaymentAmount")
    private double paymentAmount;

    @Column(name = "PaymentDate")
    private LocalDateTime paymentDate;

    @Column(name = "PaymentStatus", length = 50)
    private String paymentStatus;
}
