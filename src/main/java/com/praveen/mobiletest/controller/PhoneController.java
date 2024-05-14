package com.praveen.mobiletest.controller;

import com.praveen.mobiletest.entity.Phone;
import com.praveen.mobiletest.service.PhoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public List<Phone> getAllPhones() {
        return phoneService.getAllPhones();
    }

    @PostMapping("/book/{phoneId}")
    public Phone bookPhone(@PathVariable Long phoneId, @RequestParam(value = "bookedBy" , required = false) String bookedBy) {
        return phoneService.bookPhone(phoneId, bookedBy);
    }

    @PostMapping("/return/{phoneId}")
    public Phone returnPhone(@PathVariable Long phoneId) {
        return phoneService.returnPhone(phoneId);
    }
}
