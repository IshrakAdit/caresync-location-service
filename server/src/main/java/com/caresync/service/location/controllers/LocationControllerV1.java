package com.caresync.service.location.controllers;

import com.caresync.service.location.services.abstractions.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location/v1")
@RequiredArgsConstructor
public class LocationControllerV1 {

    private final LocationService locationService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(locationService.testResponse());
    }

}
