package com.praveen.mobiletest.service;

import com.praveen.mobiletest.PhoneRepository;
import com.praveen.mobiletest.entity.Phone;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    public Phone bookPhone(Long phoneId, String bookedBy) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (!phone.isAvailability()) {
            throw new IllegalStateException("Phone is already booked");
        }

        phone.setAvailability(false);
        phone.setBookedBy(bookedBy);
        phone.setBookingDate(LocalDateTime.now());

        return phoneRepository.save(phone);
    }

    public Phone returnPhone(Long phoneId) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (phone.isAvailability()) {
            throw new IllegalStateException("Phone is not booked");
        }

        phone.setAvailability(true);
        phone.setBookedBy(null);
        phone.setBookingDate(null);

        return phoneRepository.save(phone);
    }
}

