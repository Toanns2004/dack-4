package org.example.projectapi.Service;

import org.example.projectapi.Repository.CustomerRepository;
import org.example.projectapi.Repository.OrderRepository;
import org.example.projectapi.model.Customer;
import org.example.projectapi.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Orders> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Orders save(Orders customer) {
        return orderRepository.save(customer);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }


}