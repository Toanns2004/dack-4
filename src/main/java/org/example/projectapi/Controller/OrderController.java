package org.example.projectapi.Controller;

import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.Service.OrderService;
import org.example.projectapi.Service.RestaurantTableService;
import org.example.projectapi.dto.request.OrderRequest;
import org.example.projectapi.dto.response.MessageRespone;
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
    public ResponseEntity<Orders> createOrders(@PathVariable Long tableId, @RequestBody OrderRequest orderRequest) {
        Orders newOrder = new Orders();
        Optional<RestaurantTable> table = restaurantTableService.findById(tableId);
        if (table.isPresent() ) {
            newOrder.setRestaurantTable(table.get());
//            newOrder.setCustomer(orderRequest.getCustomerId());
            newOrder.setCreateAt(orderRequest.getCreateAt());
            newOrder.setBookingTime(orderRequest.getBookingTime());
            newOrder.setStatus(orderRequest.getStatus());
            newOrder.setPayment(orderRequest.getPayment());
            newOrder.setOriginalPrice(orderRequest.getOriginalPrice());
            newOrder.setTotalDiscount(orderRequest.getTotalDiscount());
            newOrder.setTotalPrice(orderRequest.getOriginalPrice(),orderRequest.getTotalDiscount());
            Orders saveOrder= orderService.save(newOrder);
            return ResponseEntity.ok(saveOrder);
        }

        return ResponseEntity.notFound().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Orders> updateOrders(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
//        Optional<Orders> orders = orderService.findById(id);
//        if (orders.isPresent()) {
//            Orders newOrder = orders.get();
//            newOrder.setCustomer(orderRequest.getCustomer());
//            newOrder.setCoupon(orderRequest.getCoupon());
////            updatedPublisher.setOrderItems(publisherDetails.getOrderItems());
//            newOrder.setStatus(orderRequest.getStatus());
//            newOrder.setBookingTime(orderRequest.getBookingTime());
//            newOrder.setCreateAt(orderRequest.getCreateAt());
//            newOrder.setOriginalPrice(orderRequest.getOriginalPrice());
//            newOrder.setTotalPrice(orderRequest.getTotalPrice());
//            newOrder.setRestaurantTable(orderRequest.getRestaurantTableId());
//            newOrder.setPayment(orderRequest.getPayment());
//            newOrder.setTotalDiscount(orderRequest.getTotalDiscount());
//            return ResponseEntity.ok(orderService.save(newOrder));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
