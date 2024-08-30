package org.example.projectapi.Repository;

import org.example.projectapi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByBillNumber(String bill);
}
