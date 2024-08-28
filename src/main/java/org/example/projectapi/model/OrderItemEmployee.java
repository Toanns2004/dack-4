package org.example.projectapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class OrderItemEmployee {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrderItem orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employeeId;

    @Column(nullable = false )
    private String Status;

    public OrderItemEmployee() {

    }


}
