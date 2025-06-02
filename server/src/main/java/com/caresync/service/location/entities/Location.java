package com.caresync.service.location.entities;

import com.caresync.service.location.enums.LOCATION_TYPE;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="locations")
public class Location {

    @Id
    private String id;

    @NotBlank(message = "Must provide location type")
    private LOCATION_TYPE locationType;

    private String address;
    private String thana;
    private String po;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "Postal code cannot be blank")
    private Number postalCode;

    @NotBlank(message = "Zone ID cannot be blank")
    private Number zoneId;

    public Location(String locationId, LOCATION_TYPE newLocationType, String newCity, Number newPostalCode, Number newZoneId) {
        this.id = locationId;
        this.locationType = newLocationType;
        this.address = null;
        this.thana = null;
        this.po = null;
        this.city = newCity;
        this.postalCode = newPostalCode;
        this.zoneId = newZoneId;
    }
}