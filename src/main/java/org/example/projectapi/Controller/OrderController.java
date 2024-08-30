package org.example.projectapi.Controller;

import jakarta.validation.Valid;
import org.example.projectapi.Service.CouponService;
import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.Service.OrderService;
import org.example.projectapi.Service.RestaurantTableService;
import org.example.projectapi.dto.request.OrderRequest;
import org.example.projectapi.dto.response.MessageRespone;
import org.example.projectapi.dto.response.OrderResponse;
import org.example.projectapi.model.Coupon;
import org.example.projectapi.model.Customer;
import org.example.projectapi.model.Orders;
import org.example.projectapi.model.RestaurantTable;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantTableService restaurantTableService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrdersById(@PathVariable Long id) {
        Optional<Orders> publisher = orderService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{tableId}")
    public ResponseEntity<OrderResponse> createOrders(@PathVariable Long tableId, @RequestBody OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        double totalDiscount= 0;

        Optional<RestaurantTable> table = restaurantTableService.findById(tableId);

        if (table.isPresent() ) {
            Orders newOrder = new Orders();

            newOrder.setRestaurantTable(table.get());
            Optional<Customer> customer = customerService.findById((long) 1);
            if (customer.isPresent()) {
                newOrder.setCustomer(customer.get());
                Optional<Coupon> coupon = couponService.findByName(orderRequest.getCoupon());
                if (coupon.isPresent()) {
                    newOrder.setCoupon(coupon.get());
                    totalDiscount = Math.abs(orderRequest.getOriginalPrice()*coupon.get().getDiscount());
                }
                newOrder.setBillNumber(orderService.createNextBillNumber());
                newOrder.setCreateAt(orderRequest.getCreateAt());
                newOrder.setBookingTime(orderRequest.getBookingTime());
                newOrder.setStatus(orderRequest.getStatus());
                newOrder.setPayment(orderRequest.getPayment());
                newOrder.setOriginalPrice(orderRequest.getOriginalPrice());
                newOrder.setTotalDiscount(totalDiscount);
                newOrder.setTotalPrice(orderRequest.getOriginalPrice(),totalDiscount);
                Orders saveOrder= orderService.save(newOrder);



                response.setBillNumber(saveOrder.getBillNumber());
                response.setCreateAt(saveOrder.getCreateAt());
                response.setNameCustomer(saveOrder.getCustomer().getName());
                response.setCoupon(saveOrder.getCoupon().getName());
                response.setNameTable(saveOrder.getRestaurantTable().getNameTable());
                response.setOriginalPrice(saveOrder.getOriginalPrice());
                response.setTotalPrice(saveOrder.getTotalPrice());
                response.setTotalDiscount(saveOrder.getTotalDiscount());
                return ResponseEntity.ok(response);
            }


        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrders(@PathVariable Long id, @RequestBody @Valid OrderRequest orderRequest) {
        Optional<Orders> existingOrder = orderService.findById(id);
        if (existingOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Orders updatedOrder = existingOrder.get();
        updatedOrder.setStatus(orderRequest.getStatus());
        updatedOrder.setBookingTime(orderRequest.getBookingTime());
        updatedOrder.setCreateAt(orderRequest.getCreateAt());
        updatedOrder.setOriginalPrice(orderRequest.getOriginalPrice());
        updatedOrder.setTotalPrice(orderRequest.getTotalPrice());
        updatedOrder.setPayment(orderRequest.getPayment());
        updatedOrder.setTotalDiscount(orderRequest.getTotalDiscount());

        Optional<RestaurantTable> table = restaurantTableService.findById(orderRequest.getRestaurantTableId());
        table.ifPresent(updatedOrder::setRestaurantTable);

        Optional<Customer> customer = customerService.findById(orderRequest.getCustomerId());
        customer.ifPresent(updatedOrder::setCustomer);

        Optional<Coupon> coupon = couponService.findByName(orderRequest.getCoupon());
        coupon.ifPresent(updatedOrder::setCoupon);

        Orders savedOrder = orderService.save(updatedOrder);

        OrderResponse response = new OrderResponse();
        response.setBillNumber(savedOrder.getBillNumber());
        response.setCreateAt(savedOrder.getCreateAt());
        response.setNameCustomer(savedOrder.getCustomer().getName());
        response.setCoupon(savedOrder.getCoupon() != null ? savedOrder.getCoupon().getName() : null);
        response.setNameTable(savedOrder.getRestaurantTable().getNameTable());
        response.setOriginalPrice(savedOrder.getOriginalPrice());
        response.setTotalPrice(savedOrder.getTotalPrice());
        response.setTotalDiscount(savedOrder.getTotalDiscount());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        Optional<Orders> publisher = orderService.findById(id);
        if (publisher.isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
