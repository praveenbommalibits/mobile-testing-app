package com.praveen.mobiletest.service;

import com.praveen.mobiletest.PhoneRepository;
import com.praveen.mobiletest.entity.Phone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepositoryMock;

    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneService = new PhoneService(phoneRepositoryMock);
    }

    @Test
    void bookPhone_Success() {
        Long phoneId = 1L;
        String bookedBy = "Praveen";
        LocalDateTime now = LocalDateTime.now();

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(true);

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));
        when(phoneRepositoryMock.save(phone)).thenReturn(phone);

        Phone bookedPhone = phoneService.bookPhone(phoneId, bookedBy);

        assertFalse(bookedPhone.isAvailability());
        assertEquals(bookedBy, bookedPhone.getBookedBy());
        assertNotNull(bookedPhone.getBookingDate());
        assertEquals(now.getYear(), bookedPhone.getBookingDate().getYear());
        assertEquals(now.getMonth(), bookedPhone.getBookingDate().getMonth());
        assertEquals(now.getDayOfMonth(), bookedPhone.getBookingDate().getDayOfMonth());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, times(1)).save(phone);
    }

    @Test
    void bookPhone_PhoneAlreadyBooked() {
        Long phoneId = 1L;
        String bookedBy = "Praveen";

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(false);

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> phoneService.bookPhone(phoneId, bookedBy));

        assertEquals("Phone is already booked", exception.getMessage());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, never()).save(any());
    }

    @Test
    void bookPhone_PhoneNotFound() {
        Long phoneId = 1L;
        String bookedBy = "Praveen";

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> phoneService.bookPhone(phoneId, bookedBy));

        assertEquals("Phone not found", exception.getMessage());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, never()).save(any());
    }

    @Test
    void returnPhone_Success() {
        Long phoneId = 1L;

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(false);
        phone.setBookedBy("Praveen");
        phone.setBookingDate(LocalDateTime.now());

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));
        when(phoneRepositoryMock.save(phone)).thenReturn(phone);

        Phone returnedPhone = phoneService.returnPhone(phoneId);

        assertTrue(returnedPhone.isAvailability());
        assertNull(returnedPhone.getBookedBy());
        assertNull(returnedPhone.getBookingDate());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, times(1)).save(phone);
    }

    @Test
    void returnPhone_PhoneNotBooked() {
        Long phoneId = 1L;

        Phone phone = new Phone();
        phone.setId(phoneId);
        phone.setAvailability(true);

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.of(phone));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> phoneService.returnPhone(phoneId));

        assertEquals("Phone is not booked", exception.getMessage());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, never()).save(any());
    }

    @Test
    void returnPhone_PhoneNotFound() {
        Long phoneId = 1L;

        when(phoneRepositoryMock.findById(phoneId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> phoneService.returnPhone(phoneId));

        assertEquals("Phone not found", exception.getMessage());

        verify(phoneRepositoryMock, times(1)).findById(phoneId);
        verify(phoneRepositoryMock, never()).save(any());
    }
}
