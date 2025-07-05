package com.caresync.service.location.services;

import com.caresync.service.location.dtos.request.LocationRequest;
import com.caresync.service.location.dtos.response.LocationResponse;
import com.caresync.service.location.entities.Location;
import com.caresync.service.location.enums.LOCATION_TYPE;
import com.caresync.service.location.repositories.LocationRepository;
import com.caresync.service.location.services.implementations.LocationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private static Location testLocation;
    private static LocationRequest testLocationRequest;
    private static LocationRequest testUpdateLocationRequest;

    @BeforeAll
    public static void init() {
        System.out.println("BeforeAll - Initializing test data");
        
        // Initialize test location
        testLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address("123 Test Street")
                .thana("Dhanmondi")
                .po("Dhanmondi")
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        // Initialize test location request
        testLocationRequest = new LocationRequest(
                null,
                LOCATION_TYPE.USER,
                "123 Test Street",
                "Dhanmondi",
                "Dhanmondi",
                "Dhaka",
                1205L,
                1L
        );

        // Initialize test update location request
        testUpdateLocationRequest = new LocationRequest(
                1L,
                LOCATION_TYPE.DOCTOR,
                "456 Updated Street",
                "Gulshan",
                "Gulshan",
                "Dhaka",
                1212L,
                2L
        );
    }

    @BeforeEach
    public void initEachTest() {
        System.out.println("BeforeEach - Setting up test");
        reset(locationRepository);
        
        // Recreate testLocation for each test to avoid shared state issues
        testLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address("123 Test Street")
                .thana("Dhanmondi")
                .po("Dhanmondi")
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();
    }

    // ============ TEST RESPONSE TESTS ============

    @Test
    @DisplayName("Should return test response successfully")
    void testResponse_ShouldReturnTestResponseSuccessfully() {
        // Act
        String result = locationService.testResponse();

        // Assert
        assertNotNull(result);
        assertEquals("Location service running successfully", result);
    }

    // ============ GET ALL LOCATIONS TESTS ============

    @Test
    @DisplayName("Should get all locations successfully")
    void getAllLocations_ShouldReturnAllLocationsSuccessfully() {
        // Arrange
        List<Location> locations = Arrays.asList(testLocation);
        when(locationRepository.findAll()).thenReturn(locations);

        // Act
        List<LocationResponse> result = locationService.getAllLocations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("123 Test Street", result.get(0).address());
        assertEquals("Dhaka", result.get(0).city());
        assertEquals(LOCATION_TYPE.USER, result.get(0).locationType());
        
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no locations exist")
    void getAllLocations_ShouldReturnEmptyListWhenNoLocationsExist() {
        // Arrange
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<LocationResponse> result = locationService.getAllLocations();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(locationRepository, times(1)).findAll();
    }

    // ============ GET LOCATIONS BY TYPE TESTS ============

    @Test
    @DisplayName("Should get locations by type successfully")
    void getLocationsByType_ShouldReturnLocationsByTypeSuccessfully() {
        // Arrange
        List<Location> locations = Arrays.asList(testLocation);
        when(locationRepository.findAllByLocationType(LOCATION_TYPE.USER)).thenReturn(locations);

        // Act
        List<LocationResponse> result = locationService.getLocationsByType(LOCATION_TYPE.USER);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(LOCATION_TYPE.USER, result.get(0).locationType());
        assertEquals("123 Test Street", result.get(0).address());
        
        verify(locationRepository, times(1)).findAllByLocationType(LOCATION_TYPE.USER);
    }

    @Test
    @DisplayName("Should return empty list when no locations exist for specific type")
    void getLocationsByType_ShouldReturnEmptyListWhenNoLocationsExistForType() {
        // Arrange
        when(locationRepository.findAllByLocationType(LOCATION_TYPE.DOCTOR)).thenReturn(Collections.emptyList());

        // Act
        List<LocationResponse> result = locationService.getLocationsByType(LOCATION_TYPE.DOCTOR);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(locationRepository, times(1)).findAllByLocationType(LOCATION_TYPE.DOCTOR);
    }

    // ============ GET LOCATION BY ID TESTS ============

    @Test
    @DisplayName("Should get location by ID successfully")
    void getLocationById_ShouldReturnLocationSuccessfully() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));

        // Act
        LocationResponse result = locationService.getLocationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("123 Test Street", result.address());
        assertEquals("Dhaka", result.city());
        assertEquals(LOCATION_TYPE.USER, result.locationType());
        assertEquals("Dhanmondi", result.thana());
        assertEquals("Dhanmondi", result.po());
        assertEquals(1205L, result.postalCode());
        assertEquals(1L, result.zoneId());
        
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when location not found by ID")
    void getLocationById_ShouldThrowExceptionWhenLocationNotFound() {
        // Arrange
        when(locationRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.getLocationById(999L);
        });

        assertEquals("Location not found with id: 999", exception.getMessage());
        verify(locationRepository, times(1)).findById(999L);
    }

    // ============ SAVE NEW LOCATION TESTS ============

    @Test
    @DisplayName("Should save new location successfully")
    void saveNewLocation_ShouldSaveLocationSuccessfully() {
        // Arrange
        when(locationRepository.save(any(Location.class))).thenReturn(testLocation);

        // Act
        LocationResponse result = locationService.saveNewLocation(testLocationRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("123 Test Street", result.address());
        assertEquals("Dhaka", result.city());
        assertEquals(LOCATION_TYPE.USER, result.locationType());
        
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should save location with minimal required fields")
    void saveNewLocation_ShouldSaveLocationWithMinimalFields() {
        // Arrange
        LocationRequest minimalRequest = new LocationRequest(
                null,
                LOCATION_TYPE.USER,
                null,
                null,
                null,
                "Dhaka",
                1205L,
                1L
        );

        Location minimalLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address(null)
                .thana(null)
                .po(null)
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        when(locationRepository.save(any(Location.class))).thenReturn(minimalLocation);

        // Act
        LocationResponse result = locationService.saveNewLocation(minimalRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Dhaka", result.city());
        assertEquals(LOCATION_TYPE.USER, result.locationType());
        assertNull(result.address());
        assertNull(result.thana());
        assertNull(result.po());
        
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should handle empty strings in location request")
    void saveNewLocation_ShouldHandleEmptyStrings() {
        // Arrange
        LocationRequest emptyStringRequest = new LocationRequest(
                null,
                LOCATION_TYPE.USER,
                "",
                "",
                "",
                "Dhaka",
                1205L,
                1L
        );

        Location locationWithEmptyStrings = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address("")
                .thana("")
                .po("")
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        when(locationRepository.save(any(Location.class))).thenReturn(locationWithEmptyStrings);

        // Act
        LocationResponse result = locationService.saveNewLocation(emptyStringRequest);

        // Assert
        assertNotNull(result);
        assertEquals("", result.address());
        assertEquals("", result.thana());
        assertEquals("", result.po());
        assertEquals("Dhaka", result.city());
        
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    // ============ UPDATE LOCATION TESTS ============

    @Test
    @DisplayName("Should update location successfully")
    void updateLocation_ShouldUpdateLocationSuccessfully() {
        // Arrange
        Location updatedLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.DOCTOR)
                .address("456 Updated Street")
                .thana("Gulshan")
                .po("Gulshan")
                .city("Dhaka")
                .postalCode(1212L)
                .zoneId(2L)
                .build();

        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(updatedLocation);

        // Act
        LocationResponse result = locationService.updateLocation(testUpdateLocationRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(LOCATION_TYPE.DOCTOR, result.locationType());
        assertEquals("456 Updated Street", result.address());
        assertEquals("Gulshan", result.thana());
        assertEquals("Gulshan", result.po());
        assertEquals("Dhaka", result.city());
        assertEquals(1212L, result.postalCode());
        assertEquals(2L, result.zoneId());
        
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent location")
    void updateLocation_ShouldThrowExceptionWhenLocationNotFound() {
        // Arrange
        LocationRequest request = new LocationRequest(
                999L,
                LOCATION_TYPE.USER,
                null,
                null,
                null,
                "Dhaka",
                1205L,
                1L
        );

        when(locationRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.updateLocation(request);
        });

        assertEquals("Location not found with id: 999", exception.getMessage());
        verify(locationRepository, times(1)).findById(999L);
        verify(locationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update location with partial data")
    void updateLocation_ShouldUpdateLocationWithPartialData() {
        // Arrange
        LocationRequest partialRequest = new LocationRequest(
                1L,
                null,
                "Partially Updated Address",
                null,
                null,
                null,
                null,
                null
        );

        Location partiallyUpdatedLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address("Partially Updated Address")
                .thana("Dhanmondi")
                .po("Dhanmondi")
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(partiallyUpdatedLocation);

        // Act
        LocationResponse result = locationService.updateLocation(partialRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Partially Updated Address", result.address());
        assertEquals("Dhanmondi", result.thana()); // Should remain unchanged
        assertEquals("Dhaka", result.city()); // Should remain unchanged
        assertEquals(LOCATION_TYPE.USER, result.locationType()); // Should remain unchanged
        
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should handle null values in update request")
    void updateLocation_ShouldHandleNullValues() {
        // Arrange
        LocationRequest nullRequest = new LocationRequest(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(testLocation);

        // Act
        LocationResponse result = locationService.updateLocation(nullRequest);

        // Assert
        assertNotNull(result);
        assertEquals(LOCATION_TYPE.USER, result.locationType()); // Should remain unchanged
        assertEquals("123 Test Street", result.address()); // Should remain unchanged
        assertEquals("Dhaka", result.city()); // Should remain unchanged
        
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    // ============ DELETE LOCATION TESTS ============

    @Test
    @DisplayName("Should delete location successfully")
    void deleteLocationById_ShouldDeleteLocationSuccessfully() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        doNothing().when(locationRepository).delete(testLocation);

        // Act
        locationService.deleteLocationById(1L);

        // Assert
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).delete(testLocation);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent location")
    void deleteLocationById_ShouldThrowExceptionWhenLocationNotFound() {
        // Arrange
        when(locationRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.deleteLocationById(999L);
        });

        assertEquals("Location not found with id: 999", exception.getMessage());
        verify(locationRepository, times(1)).findById(999L);
        verify(locationRepository, never()).delete(any());
    }

    // ============ PRIVATE METHOD TESTS ============

    @Test
    @DisplayName("Should test private mapToResponse method")
    void testPrivateMethod_mapToResponse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Method mapToResponseMethod = LocationServiceImpl.class.getDeclaredMethod("mapToResponse", Location.class);
        mapToResponseMethod.setAccessible(true);

        // Act
        LocationResponse result = (LocationResponse) mapToResponseMethod.invoke(locationService, testLocation);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(LOCATION_TYPE.USER, result.locationType());
        assertEquals("123 Test Street", result.address());
        assertEquals("Dhanmondi", result.thana());
        assertEquals("Dhanmondi", result.po());
        assertEquals("Dhaka", result.city());
        assertEquals(1205L, result.postalCode());
        assertEquals(1L, result.zoneId());
    }

    @Test
    @DisplayName("Should test private mapToResponse method with null values")
    void testPrivateMethod_mapToResponse_WithNullValues() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        Method mapToResponseMethod = LocationServiceImpl.class.getDeclaredMethod("mapToResponse", Location.class);
        mapToResponseMethod.setAccessible(true);

        Location locationWithNulls = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .address(null)
                .thana(null)
                .po(null)
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        // Act
        LocationResponse result = (LocationResponse) mapToResponseMethod.invoke(locationService, locationWithNulls);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(LOCATION_TYPE.USER, result.locationType());
        assertNull(result.address());
        assertNull(result.thana());
        assertNull(result.po());
        assertEquals("Dhaka", result.city());
        assertEquals(1205L, result.postalCode());
        assertEquals(1L, result.zoneId());
    }

    // ============ EDGE CASE TESTS ============

    @Test
    @DisplayName("Should handle all location types")
    void getLocationsByType_ShouldHandleAllLocationTypes() {
        // Test URBAN
        List<Location> urbanLocations = Arrays.asList(testLocation);
        when(locationRepository.findAllByLocationType(LOCATION_TYPE.USER)).thenReturn(urbanLocations);
        
        List<LocationResponse> urbanResult = locationService.getLocationsByType(LOCATION_TYPE.USER);
        assertEquals(1, urbanResult.size());
        assertEquals(LOCATION_TYPE.USER, urbanResult.get(0).locationType());

        // Test RURAL
        Location ruralLocation = Location.builder()
                .id(2L)
                .locationType(LOCATION_TYPE.DOCTOR)
                .city("Rural City")
                .postalCode(2000L)
                .zoneId(2L)
                .build();
        
        List<Location> ruralLocations = Arrays.asList(ruralLocation);
        when(locationRepository.findAllByLocationType(LOCATION_TYPE.DOCTOR)).thenReturn(ruralLocations);
        
        List<LocationResponse> ruralResult = locationService.getLocationsByType(LOCATION_TYPE.DOCTOR);
        assertEquals(1, ruralResult.size());
        assertEquals(LOCATION_TYPE.DOCTOR, ruralResult.get(0).locationType());

        verify(locationRepository, times(1)).findAllByLocationType(LOCATION_TYPE.USER);
        verify(locationRepository, times(1)).findAllByLocationType(LOCATION_TYPE.DOCTOR);
    }

    @Test
    @DisplayName("Should handle large postal codes")
    void saveNewLocation_ShouldHandleLargePostalCodes() {
        // Arrange
        LocationRequest largePostalCodeRequest = new LocationRequest(
                null,
                LOCATION_TYPE.USER,
                null,
                null,
                null,
                "Dhaka",
                999999999L,
                1L
        );

        Location locationWithLargePostalCode = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .city("Dhaka")
                .postalCode(999999999L)
                .zoneId(1L)
                .build();

        when(locationRepository.save(any(Location.class))).thenReturn(locationWithLargePostalCode);

        // Act
        LocationResponse result = locationService.saveNewLocation(largePostalCodeRequest);

        // Assert
        assertNotNull(result);
        assertEquals(999999999L, result.postalCode());
        
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should handle large zone IDs")
    void saveNewLocation_ShouldHandleLargeZoneIds() {
        // Arrange
        LocationRequest largeZoneIdRequest = new LocationRequest(
                null,
                LOCATION_TYPE.USER,
                null,
                null,
                null,
                "Dhaka",
                1205L,
                999999999L
        );

        Location locationWithLargeZoneId = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(999999999L)
                .build();

        when(locationRepository.save(any(Location.class))).thenReturn(locationWithLargeZoneId);

        // Act
        LocationResponse result = locationService.saveNewLocation(largeZoneIdRequest);

        // Assert
        assertNotNull(result);
        assertEquals(999999999L, result.zoneId());
        
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    // ============ MULTIPLE LOCATIONS TESTS ============

    @Test
    @DisplayName("Should handle multiple locations with different types")
    void getAllLocations_ShouldHandleMultipleLocationsWithDifferentTypes() {
        // Arrange
        Location urbanLocation = Location.builder()
                .id(1L)
                .locationType(LOCATION_TYPE.USER)
                .city("Dhaka")
                .postalCode(1205L)
                .zoneId(1L)
                .build();

        Location ruralLocation = Location.builder()
                .id(2L)
                .locationType(LOCATION_TYPE.DOCTOR)
                .city("Rural City")
                .postalCode(2000L)
                .zoneId(2L)
                .build();

        List<Location> locations = Arrays.asList(urbanLocation, ruralLocation);
        when(locationRepository.findAll()).thenReturn(locations);

        // Act
        List<LocationResponse> result = locationService.getAllLocations();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        LocationResponse firstLocation = result.get(0);
        LocationResponse secondLocation = result.get(1);
        
        assertEquals(LOCATION_TYPE.USER, firstLocation.locationType());
        assertEquals(LOCATION_TYPE.DOCTOR, secondLocation.locationType());
        assertEquals("Dhaka", firstLocation.city());
        assertEquals("Rural City", secondLocation.city());
        
        verify(locationRepository, times(1)).findAll();
    }

    // ============ MOCKING VOID METHODS ============

    @Test
    @DisplayName("Should verify delete method is called correctly")
    void deleteLocationById_ShouldVerifyDeleteMethodCall() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        doNothing().when(locationRepository).delete(testLocation);

        // Act
        locationService.deleteLocationById(1L);

        // Assert - Using argument captor to verify exact object passed
        verify(locationRepository, times(1)).delete(argThat(location -> 
            location.getId().equals(1L) && 
            location.getCity().equals("Dhaka") &&
            location.getLocationType().equals(LOCATION_TYPE.USER)
        ));
    }

    // ============ REPOSITORY EXCEPTION HANDLING ============

    @Test
    @DisplayName("Should handle repository exception during save")
    void saveNewLocation_ShouldHandleRepositoryException() {
        // Arrange
        when(locationRepository.save(any(Location.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.saveNewLocation(testLocationRequest);
        });

        assertEquals("Database connection failed", exception.getMessage());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should handle repository exception during update")
    void updateLocation_ShouldHandleRepositoryException() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        when(locationRepository.save(any(Location.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.updateLocation(testUpdateLocationRequest);
        });

        assertEquals("Database connection failed", exception.getMessage());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    @DisplayName("Should handle repository exception during delete")
    void deleteLocationById_ShouldHandleRepositoryException() {
        // Arrange
        when(locationRepository.findById(1L)).thenReturn(Optional.of(testLocation));
        doThrow(new RuntimeException("Database connection failed")).when(locationRepository).delete(testLocation);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            locationService.deleteLocationById(1L);
        });

        assertEquals("Database connection failed", exception.getMessage());
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).delete(testLocation);
    }

    @AfterEach
    public void cleanup() {
        System.out.println("AfterEach - Cleaning up test");
        verifyNoMoreInteractions(locationRepository);
    }

    @AfterAll
    public static void destroy() {
        System.out.println("AfterAll - Test suite completed");
    }
}
