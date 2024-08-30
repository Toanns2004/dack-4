package org.example.projectapi.dto.response;

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

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String billNumber;

    private String nameCustomer;

    private String nameTable;


    private String coupon;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

//    @NotNull
//    private PaymentMethod payment;
//    @NotNull
//    private StatusOrder status;

    @NotNull
    private double originalPrice;

    private double totalDiscount;
    @NotNull
    private double totalPrice;

    private String error;

}
