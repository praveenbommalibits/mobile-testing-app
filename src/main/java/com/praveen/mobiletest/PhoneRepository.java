package com.praveen.mobiletest;

import com.praveen.mobiletest.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    // Custom queries if needed
}
