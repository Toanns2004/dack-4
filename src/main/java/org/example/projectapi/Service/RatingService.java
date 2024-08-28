package org.example.projectapi.Service;

import org.example.projectapi.Repository.RatingRepository;
import org.example.projectapi.model.Coupon;
import org.example.projectapi.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(Long id) {
        return ratingRepository.findById(id);
    }

    public Rating save(Rating customer) {
        return ratingRepository.save(customer);
    }

    public void deleteById(Long id) {
        ratingRepository.deleteById(id);
    }
}
