package org.example.projectapi.Controller;

import org.example.projectapi.Service.CouponService;
import org.example.projectapi.Service.CustomerService;
import org.example.projectapi.dto.request.CouponRequest;
import org.example.projectapi.dto.response.MessageRespone;
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
    public Coupon createCoupon(@RequestBody CouponRequest couponRequest) {
        Coupon newCoupon = new Coupon();
        newCoupon.setName(couponRequest.getName());
        newCoupon.setDiscount(couponRequest.getDiscount());
        return couponService.save(newCoupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest couponRequest) {
        Optional<Coupon> couponOptional = couponService.findById(id);
        if (couponOptional.isPresent()) {
            Coupon couponUpdate = couponOptional.get();
            couponUpdate.setName(couponRequest.getName());
            couponUpdate.setDiscount(couponRequest.getDiscount());
            return ResponseEntity.ok(couponService.save(couponUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageRespone> deleteCoupon(@PathVariable Long id) {
        Optional<Coupon> publisher = couponService.findById(id);
        if (publisher.isPresent()) {
            couponService.deleteById(id);
            return ResponseEntity.ok(new MessageRespone("Coupon deleted successfully"));
        } else {
            return ResponseEntity.ok(new MessageRespone("Coupon could not be deleted"));
        }
    }
}
