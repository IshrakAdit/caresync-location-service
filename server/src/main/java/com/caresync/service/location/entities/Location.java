package com.caresync.service.location.entities;

import com.caresync.service.location.enums.LOCATION_TYPE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Must provide location type")
    @Enumerated(EnumType.STRING)
    private LOCATION_TYPE locationType;

    private String address;
    private String thana;
    private String po;

    @NotNull(message = "City cannot be blank")
    private String city;

    @NotNull(message = "Postal code cannot be blank")
    private Long postalCode;

    @NotNull(message = "Zone ID cannot be blank")
    private Long zoneId;

    public Location(LOCATION_TYPE newLocationType, String newCity, Long newPostalCode, Long newZoneId) {
        this.locationType = newLocationType;
        this.address = null;
        this.thana = null;
        this.po = null;
        this.city = newCity;
        this.postalCode = newPostalCode;
        this.zoneId = newZoneId;
    }

    public Location(LOCATION_TYPE newLocationType, String newAddress, String newThana, String newPO, String newCity, Long newPostalCode, Long newZoneId) {
        this.locationType = newLocationType;
        this.address = newAddress;
        this.thana = newThana;
        this.po = newPO;
        this.city = newCity;
        this.postalCode = newPostalCode;
        this.zoneId = newZoneId;
    }
}