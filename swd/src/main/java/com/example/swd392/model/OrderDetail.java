package com.example.swd392.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orderdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderdetailID")
    private int orderDetailId;

    @ManyToOne
    @JoinColumn(name = "ArtworkID")
    private Artwork artwork;

    @Column(name = "OrderdetailPrice")
    private double orderDetailPrice;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

}
