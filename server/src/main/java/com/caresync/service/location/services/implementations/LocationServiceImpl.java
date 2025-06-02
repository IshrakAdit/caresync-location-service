package com.caresync.service.location.services.implementations;

import com.caresync.service.location.dtos.request.LocationRequest;
import com.caresync.service.location.dtos.response.LocationResponse;
import com.caresync.service.location.entities.Location;
import com.caresync.service.location.enums.LOCATION_TYPE;
import com.caresync.service.location.repositories.LocationRepository;
import com.caresync.service.location.services.abstractions.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public String testResponse() {
        return "Location service running successfully";
    }

    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<LocationResponse> getLocationsByType(LOCATION_TYPE type) {
        return locationRepository.findAllByLocationType(type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LocationResponse getLocationByTypeAndId(LocationRequest request) {
        return locationRepository
                .findByIdAndLocationType(request.locationId(), request.locationType())
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Location not found"));
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
