package com.praveen.mobiletest.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private boolean availability;
    private String bookedBy;
    private LocalDateTime bookingDate;

    @Version
    private int version;
}
