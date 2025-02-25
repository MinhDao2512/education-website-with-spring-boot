package com.toilamdev.stepbystep.entity;

import com.toilamdev.stepbystep.enums.PaymentMethod;
import com.toilamdev.stepbystep.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {
    private Integer count;
    private Double totalPrice;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;
}
