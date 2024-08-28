package org.example.projectapi.Controller;

import org.example.projectapi.Service.BookingService;
import org.example.projectapi.Service.CategoryService;
import org.example.projectapi.model.Booking;
import org.example.projectapi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllCategory() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getCategoryById(@PathVariable Long id) {
        Optional<Booking> publisher = bookingService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Booking createCategory(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateCategory(@PathVariable Long id, @RequestBody Booking publisherDetails) {
        Optional<Booking> publisher = bookingService.findById(id);
        if (publisher.isPresent()) {
            Booking updatedCategory = publisher.get();
            updatedCategory.setPeople(publisherDetails.getPeople());
            updatedCategory.setDate(publisherDetails.getDate());
            return ResponseEntity.ok(bookingService.save(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Booking> publisher = bookingService.findById(id);
        if (publisher.isPresent()) {
            bookingService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}