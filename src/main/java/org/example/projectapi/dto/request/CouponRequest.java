package org.example.projectapi.dto.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CouponRequest {

    @Column(nullable = false )
    private String name;

    @Column(nullable = false )
    private double discount;


}
