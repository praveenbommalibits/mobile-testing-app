package com.praveen.mobiletest.service;

import com.praveen.mobiletest.PhoneRepository;
import com.praveen.mobiletest.entity.Phone;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public Phone bookPhone(Long phoneId, String bookedBy) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (!phone.isAvailability()) {
            throw new IllegalStateException("Phone is already booked");
        }

        phone.setAvailability(false);
        phone.setBookedBy(bookedBy);
        phone.setBookingDate(LocalDateTime.now());

        try {
            return phoneRepository.save(phone);
        } catch (OptimisticLockingFailureException ex) {
            throw ex;
        }
    }

    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public Phone returnPhone(Long phoneId) {
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (phone.isAvailability()) {
            throw new IllegalStateException("Phone is not booked");
        }

        phone.setAvailability(true);
        phone.setBookedBy(null);
        phone.setBookingDate(null);

        try {
            return phoneRepository.save(phone);
        } catch (OptimisticLockingFailureException ex) {
            throw ex;
        }
    }



    @Transactional(readOnly = true)
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }
}
