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

    @Override
    public String testResponse() {
        return "Location service running successfully";
    }

    @Override
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationResponse> getLocationsByType(LOCATION_TYPE type) {
        return locationRepository.findAllByLocationType(type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponse getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));

        return mapToResponse(location);
    }

    @Override
    public LocationResponse saveNewLocation(LocationRequest locationRequest) {
        Location newLocation = new Location(locationRequest.locationType(), locationRequest.address(), locationRequest.thana(), locationRequest.po(), locationRequest.city(), locationRequest.postalCode(), locationRequest.zoneId());
        return mapToResponse(locationRepository.save(newLocation));
    }

    @Override
    public LocationResponse updateLocation(LocationRequest locationRequest) {
        Location location = locationRepository.findById(locationRequest.id())
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + locationRequest.id()));

        if (locationRequest.locationType() != null) {
            location.setLocationType(locationRequest.locationType());
        }
        if (locationRequest.address() != null) {
            location.setAddress(locationRequest.address());
        }
        if (locationRequest.thana() != null) {
            location.setThana(locationRequest.thana());
        }
        if (locationRequest.po() != null) {
            location.setPo(locationRequest.po());
        }
        if (locationRequest.city() != null) {
            location.setCity(locationRequest.city());
        }
        if (locationRequest.postalCode() != null) {
            location.setPostalCode(locationRequest.postalCode());
        }
        if (locationRequest.zoneId() != null) {
            location.setZoneId(locationRequest.zoneId());
        }

        return mapToResponse(locationRepository.save(location));
    }

    @Override
    public LocationResponse deleteLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));

        locationRepository.delete(location);
        return mapToResponse(location);
    }

    private LocationResponse mapToResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .locationType(location.getLocationType())
                .address(location.getAddress())
                .thana(location.getThana())
                .po(location.getPo())
                .city(location.getCity())
                .postalCode(location.getPostalCode())
                .zoneId(location.getZoneId())
                .build();
    }

}
