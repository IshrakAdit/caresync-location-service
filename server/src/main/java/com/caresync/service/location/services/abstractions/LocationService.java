package com.caresync.service.location.services.abstractions;

import com.caresync.service.location.dtos.request.LocationRequest;
import com.caresync.service.location.dtos.response.LocationResponse;
import com.caresync.service.location.enums.LOCATION_TYPE;

import java.util.List;

public interface LocationService {

    String testResponse();
    List<LocationResponse> getAllLocations();
    List<LocationResponse> getLocationsByType(LOCATION_TYPE type);
    LocationResponse getLocationById(Long id);
    LocationResponse saveNewLocation(LocationRequest locationRequest);
    LocationResponse updateLocation(LocationRequest locationRequest);
    void deleteLocationById(Long id);

}
