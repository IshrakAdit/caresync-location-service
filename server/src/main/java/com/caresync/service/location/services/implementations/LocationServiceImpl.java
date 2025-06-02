package com.caresync.service.location.services.implementations;

import com.caresync.service.location.dtos.response.LocationResponse;
import com.caresync.service.location.entities.Location;
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

    private LocationResponse mapToResponse(Location location) {
        return LocationResponse.builder()
                .address(location.getAddress())
                .thana(location.getThana())
                .po(location.getPo())
                .city(location.getCity())
                .postalCode(location.getPostalCode())
                .zoneId(location.getZoneId())
                .build();
    }

}
