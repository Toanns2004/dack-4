package org.example.projectapi.Controller;

import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.Service.RestaurantTableService;
import org.example.projectapi.model.Customer;
import org.example.projectapi.model.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/restaurantTables")
public class RestaurantTableController {
    @Autowired
    private RestaurantTableService restaurantTableService;

    @GetMapping
    public List<RestaurantTable> getAllRestaurantTable() {
        return restaurantTableService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTable> getRestaurantTableById(@PathVariable Long id) {
        Optional<RestaurantTable> publisher = restaurantTableService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RestaurantTable createRestaurantTable(@RequestBody RestaurantTable publisher) {
        return restaurantTableService.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTable> updateRestaurantTable(@PathVariable Long id, @RequestBody RestaurantTable publisherDetails) {
        Optional<RestaurantTable> publisher = restaurantTableService.findById(id);
        if (publisher.isPresent()) {
            RestaurantTable updatedPublisher = publisher.get();
            updatedPublisher.setChairs(publisherDetails.getChairs());
            updatedPublisher.setOrder(publisherDetails.getOrder());
            updatedPublisher.setQrCode(publisherDetails.getQrCode());
            updatedPublisher.setStatus(publisherDetails.getStatus());
            return ResponseEntity.ok(restaurantTableService.save(updatedPublisher));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantTable(@PathVariable Long id) {
        Optional<RestaurantTable> publisher = restaurantTableService.findById(id);
        if (publisher.isPresent()) {
            restaurantTableService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
