package com.caresync.service.location.services.implementations;

import com.caresync.service.location.repositories.LocationRepository;
import com.caresync.service.location.services.abstractions.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public String testResponse() {
        return "Location service running successfully";
    }

}
