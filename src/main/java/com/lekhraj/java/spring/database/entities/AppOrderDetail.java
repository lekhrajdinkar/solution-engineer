package com.lekhraj.java.spring.database.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@Entity
@Table(name = "app_order_detail", schema = "public")

public class AppOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private AppOrder order;

    private String productName;

    private int quantity;

    private BigDecimal price;

}
