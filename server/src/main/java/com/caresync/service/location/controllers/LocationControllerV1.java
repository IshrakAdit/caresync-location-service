package com.caresync.service.location.controllers;

import com.caresync.service.location.dtos.request.LocationRequest;
import com.caresync.service.location.dtos.response.LocationResponse;
import com.caresync.service.location.enums.LOCATION_TYPE;
import com.caresync.service.location.services.abstractions.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location/v1")
@RequiredArgsConstructor
public class LocationControllerV1 {

    private final LocationService locationService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(locationService.testResponse());
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/hospitals")
    public ResponseEntity<List<LocationResponse>> getAllHospitalLocations() {
        return ResponseEntity.ok(locationService.getLocationsByType(LOCATION_TYPE.HOSPITAL));
    }

    @GetMapping("/users")
    public ResponseEntity<List<LocationResponse>> getAllUserLocations() {
        return ResponseEntity.ok(locationService.getLocationsByType(LOCATION_TYPE.USER));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<LocationResponse>> getAllDoctorLocations() {
        return ResponseEntity.ok(locationService.getLocationsByType(LOCATION_TYPE.DOCTOR));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<LocationResponse> saveNewLocation(@RequestBody LocationRequest locationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.saveNewLocation(locationRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<LocationResponse> updateLocation(@RequestBody LocationRequest locationRequest) {
        return ResponseEntity.ok(locationService.updateLocation(locationRequest));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }

}
