package com.caresync.service.location.dtos.request;

import com.caresync.service.location.enums.LOCATION_TYPE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationRequest(
        @NotBlank(message = "Location ID must not be blank")
        String locationId,

        @NotNull(message = "Location type must be provided")
        LOCATION_TYPE locationType
) {}
