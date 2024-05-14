package com.praveen.mobiletest.controller;

import com.praveen.mobiletest.entity.Phone;
import com.praveen.mobiletest.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PhoneControllerTest {

    @Mock
    private PhoneService phoneServiceMock;

    private PhoneController phoneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        phoneController = new PhoneController(phoneServiceMock);
    }

    @Test
    void getAllPhones_Success() {
        List<Phone> phones = Arrays.asList(new Phone(), new Phone());

        when(phoneServiceMock.getAllPhones()).thenReturn(phones);

        List<Phone> result = phoneController.getAllPhones();

        assertEquals(2, result.size());

        verify(phoneServiceMock, times(1)).getAllPhones();
    }

    @Test
    void bookPhone_Success() {
        Long phoneId = 1L;
        String bookedBy = "Praveen";

        Phone phone = new Phone();

        when(phoneServiceMock.bookPhone(phoneId, bookedBy)).thenReturn(phone);

        Phone result = phoneController.bookPhone(phoneId, bookedBy);

        assertEquals(phone, result);

        verify(phoneServiceMock, times(1)).bookPhone(phoneId, bookedBy);
    }

    @Test
    void returnPhone_Success() {
        Long phoneId = 1L;

        Phone phone = new Phone();

        when(phoneServiceMock.returnPhone(phoneId)).thenReturn(phone);

        Phone result = phoneController.returnPhone(phoneId);

        assertEquals(phone, result);

        verify(phoneServiceMock, times(1)).returnPhone(phoneId);
    }
}
