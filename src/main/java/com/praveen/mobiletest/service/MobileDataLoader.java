package com.praveen.mobiletest.service;

import com.praveen.mobiletest.PhoneRepository;
import com.praveen.mobiletest.entity.Phone;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class MobileDataLoader implements ApplicationRunner {
    private final PhoneRepository phoneRepository;
    private final ResourceLoader resourceLoader;

    public MobileDataLoader(PhoneRepository phoneRepository, ResourceLoader resourceLoader) {
        this.phoneRepository = phoneRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Read data from the file and save it to the database
        Resource resource = resourceLoader.getResource("classpath:mobile_data.txt");
        try (Stream<String> lines = Files.lines(Paths.get(resource.getURI()))) {
            lines.forEach(model -> {
                Phone phone = new Phone();
                phone.setModel(model);
                phone.setAvailability(true); // Assume all phones are initially available
                phoneRepository.save(phone);
            });
        } catch (IOException e) {
            // Handle file reading error
            e.printStackTrace();
        }
    }
}

