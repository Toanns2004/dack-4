package org.example.projectapi.Controller;

import org.example.projectapi.Service.CouponService;
import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.model.Coupon;
import org.example.projectapi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    public List<Coupon> getAllCoupon() {
        return couponService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Optional<Coupon> publisher = couponService.findById(id);
        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon publisher) {
        return couponService.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon publisherDetails) {
        Optional<Coupon> publisher = couponService.findById(id);
        if (publisher.isPresent()) {
            Coupon updatedPublisher = publisher.get();
            updatedPublisher.setName(publisherDetails.getName());
            updatedPublisher.setDiscount(publisherDetails.getDiscount());
            return ResponseEntity.ok(couponService.save(updatedPublisher));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        Optional<Coupon> publisher = couponService.findById(id);
        if (publisher.isPresent()) {
            couponService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
