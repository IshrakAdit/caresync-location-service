package com.caresync.service.location.dtos.response;

import com.caresync.service.location.enums.LOCATION_TYPE;
import lombok.Builder;

import java.time.ZoneId;

@Builder
public record LocationResponse(
        String id,
        LOCATION_TYPE locationType,
        String address,
        String thana,
        String po,
        String city,
        Number postalCode,
        Number ZoneId
) {}

