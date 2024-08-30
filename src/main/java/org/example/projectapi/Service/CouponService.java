package org.example.projectapi.Service;

import org.example.projectapi.Repository.CouponRepository;
import org.example.projectapi.model.Coupon;
import org.example.projectapi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Optional<Coupon> findByName(String name){
        return couponRepository.findByName(name);
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    public Coupon save(Coupon customer) {
        return couponRepository.save(customer);
    }

    public void deleteById(Long id) {
        couponRepository.deleteById(id);
    }
}
