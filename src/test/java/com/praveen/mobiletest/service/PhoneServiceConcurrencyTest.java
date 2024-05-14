package com.praveen.mobiletest.service;

import com.praveen.mobiletest.entity.Phone;
import com.praveen.mobiletest.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PhoneServiceConcurrencyTest {

    @Mock
    private PhoneRepository phoneRepositoryMock;

    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneService = new PhoneService(phoneRepositoryMock);
    }

    @Test
    void concurrentBooking_Success() throws InterruptedException {
        Long phoneId = 1L;
        String bookedBy = "Praveen";
        LocalDateTime now = LocalDateTime.now();

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(true);

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));

        Thread thread1 = new Thread(() -> {
            phoneService.bookPhone(phoneId, bookedBy);
        });

        Thread thread2 = new Thread(() -> {
            phoneService.bookPhone(phoneId, bookedBy);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(phoneRepositoryMock, times(2)).findById(phoneId);
        verify(phoneRepositoryMock, times(1)).save(phone);
    }

    @Test
    void concurrentBooking_PhoneAlreadyBooked() throws InterruptedException {
        Long phoneId = 1L;
        String bookedBy = "Praveen";

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(false);

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));

        Thread thread1 = new Thread(() -> {
            phoneService.bookPhone(phoneId, bookedBy);
        });

        Thread thread2 = new Thread(() -> {
            phoneService.bookPhone(phoneId, bookedBy);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(phoneRepositoryMock, times(2)).findById(phoneId);
        verify(phoneRepositoryMock, never()).save(phone);
    }

    @Test
    void concurrentBooking_OptimisticLockException() throws InterruptedException {
        Long phoneId = 1L;
        String bookedBy = "Praveen";

        Phone initialPhone = new Phone();
        initialPhone.setId(phoneId);
        initialPhone.setAvailability(true);

        // Create a custom repository mock to return a new instance of Phone for each invocation
        PhoneRepository customPhoneRepositoryMock = mock(PhoneRepository.class);
        when(customPhoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(initialPhone));

        // Simulate OptimisticLockingFailureException for save operation
        doThrow(OptimisticLockingFailureException.class).when(customPhoneRepositoryMock).save(any(Phone.class));

        // Create two threads, each invoking bookPhone method with a separate phone entity
        Thread thread1 = new Thread(() -> {
            Phone phone1 = new Phone();
            phone1.setId(phoneId);
            phoneService = new PhoneService(customPhoneRepositoryMock);
            phoneService.bookPhone(phoneId, bookedBy);
        });

        Thread thread2 = new Thread(() -> {
            Phone phone2 = new Phone();
            phone2.setId(phoneId);
            phoneService = new PhoneService(customPhoneRepositoryMock);
            phoneService.bookPhone(phoneId, bookedBy);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        verify(customPhoneRepositoryMock, atLeastOnce()).findById(phoneId);
        verify(customPhoneRepositoryMock, atLeastOnce()).save(any(Phone.class));
    }

}
