package com.caresync.service.location.dtos.response;

import com.caresync.service.location.enums.LOCATION_TYPE;
import lombok.Builder;

@Builder
public record LocationResponse(
        String id,
        LOCATION_TYPE locationType,
        String address,
        String thana,
        String po,
        String city,
        Number postalCode,
        Number zoneId
) {}

