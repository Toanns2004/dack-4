package org.example.projectapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.projectapi.enums.PaymentMethod;
import org.example.projectapi.enums.StatusOrder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @Column(nullable = false)
    private Date bookingTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrder status;

    @Column(nullable = false)
    private double originalPrice;

    private double totalDiscount;

    @Column(nullable = false)
    private double totalPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderItem> orderItems;



    public void setTotalPrice(double totalDiscount, double originalPrice) {
        this.totalPrice = Math.abs(originalPrice-totalDiscount);
    }


}
