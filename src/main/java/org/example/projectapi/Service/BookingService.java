package org.example.projectapi.Service;

import org.example.projectapi.Repository.BookingRepository;
import org.example.projectapi.Repository.CategoryRepository;
import org.example.projectapi.model.Booking;
import org.example.projectapi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking customer) {
        return bookingRepository.save(customer);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}