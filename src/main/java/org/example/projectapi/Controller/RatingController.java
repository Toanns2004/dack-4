package org.example.projectapi.Controller;

import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.Service.RatingService;
import org.example.projectapi.model.Customer;
import org.example.projectapi.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<Rating> getAllCustomer() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getCustomerById(@PathVariable Long id) {
        Optional<Rating> publisher = ratingService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rating createCustomer(@RequestBody Rating publisher) {
        return ratingService.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateCustomer(@PathVariable Long id, @RequestBody Rating publisherDetails) {
        Optional<Rating> publisher = ratingService.findById(id);
        if (publisher.isPresent()) {
            Rating updatedPublisher = publisher.get();
            updatedPublisher.setStar(publisherDetails.getStar());
            updatedPublisher.setDescription(publisherDetails.getDescription());
            return ResponseEntity.ok(ratingService.save(updatedPublisher));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Rating> publisher = ratingService.findById(id);
        if (publisher.isPresent()) {
            ratingService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
