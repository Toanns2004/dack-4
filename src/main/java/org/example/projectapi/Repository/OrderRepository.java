package org.example.projectapi.Repository;

import org.example.projectapi.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT b.billNumber FROM Orders b ORDER BY b.id DESC")
    List<String> findLastBillNumber();
}
