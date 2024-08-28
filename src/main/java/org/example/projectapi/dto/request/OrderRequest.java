package org.example.projectapi.dto.request;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.projectapi.enums.PaymentMethod;
import org.example.projectapi.enums.StatusOrder;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long id;


    private Long customerId;

    @NotBlank
    private Long restaurantTableId;

    private Date bookingTime;

    private Long couponId;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    @NotNull
    private PaymentMethod payment;
    @NotNull
    private StatusOrder status;
    @NotNull
    private double originalPrice;
    @NotNull
    private double totalDiscount;
    @NotNull
    private double totalPrice;


}
