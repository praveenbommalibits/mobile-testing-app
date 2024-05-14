package com.praveen.mobiletest.service;

import com.praveen.mobiletest.PhoneRepository;
import com.praveen.mobiletest.entity.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PhoneService {
    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public Phone bookPhone(Long phoneId, String bookedBy) {
        log.info("Initiated the Booking phone with phoneId {} and bookedBy {} started", phoneId, bookedBy);
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (!phone.isAvailability()) {
            throw new IllegalStateException("Phone is already booked");
        }

        phone.setAvailability(false);
        phone.setBookedBy(bookedBy);
        phone.setBookingDate(LocalDateTime.now());
        log.info("Initiated the Booking phone with phoneId {} and bookedBy {} end", phoneId, bookedBy);
        try {
            Phone savedPhone = phoneRepository.save(phone);
            log.info("Booking phone with phoneId {} completed successfully", phoneId);
            return savedPhone;
        } catch (OptimisticLockingFailureException ex) {
            log.error("Returning phone with phoneId {} failed due to optimistic locking exception", phoneId, ex);
            throw ex;
        }
    }

    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Transactional
    public Phone returnPhone(Long phoneId) {
        log.info("Initiated the Returning phone with phoneId {} started", phoneId);
        Phone phone = phoneRepository.findById(phoneId)
                .orElseThrow(() -> new IllegalArgumentException("Phone not found"));

        if (phone.isAvailability()) {
            throw new IllegalStateException("Phone is not booked");
        }

        phone.setAvailability(true);
        phone.setBookedBy(null);
        phone.setBookingDate(null);
        log.info("Initiated the Returning phone with phoneId {} started", phoneId);
        try {
            Phone savedPhone = phoneRepository.save(phone);
            log.info("Returning phone with phoneId {} completed successfully", phoneId);
            return savedPhone;
        } catch (OptimisticLockingFailureException ex) {
            log.error("Returning phone with phoneId {} failed due to optimistic locking exception", phoneId, ex);
            throw ex;
        }
    }



    @Transactional(readOnly = true)
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }
}
