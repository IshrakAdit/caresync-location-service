package com.caresync.service.location.repositories;

import com.caresync.service.location.entities.Location;
import com.caresync.service.location.enums.LOCATION_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByLocationType(LOCATION_TYPE locationType);
    Optional<Location> findByIdAndLocationType(Long id, LOCATION_TYPE locationType);

}