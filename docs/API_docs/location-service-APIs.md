# Auth Service

## Base URL for Local Development

```
http://localhost:8083/
```

## Base URL for Production Environment

```
TBD
```

# Location API v1

## Base URL

```
/location/v1
```

---

## 1. Test Location Service

**GET** `/test`

### Description

Simple endpoint to verify the Location service is working.

### Success Response – 200

```json
"Location service running successfully"
```

---

## 2. Get All Locations

**GET** `/all`

### Description

Fetches all available locations in the system.

### Success Response – 200

```json
[
  {
    "id": 1,
    "locationType": "HOSPITAL",
    "address": "123 Street",
    "thana": "Dhanmondi",
    "po": "1209",
    "city": "Dhaka",
    "postalCode": 1205,
    "zoneId": 1
  },
  {
    "id": 2,
    "locationType": "DOCTOR",
    "address": "56 Road",
    "thana": "Gulshan",
    "po": "1212",
    "city": "Dhaka",
    "postalCode": 1212,
    "zoneId": 2
  },
  {
    "id": 3,
    "locationType": "USER",
    "address": null,
    "thana": null,
    "po": null,
    "city": "Chittagong",
    "postalCode": 4000,
    "zoneId": 3
  }
]
```

### Error Response – 500

```json
{
  "success": false,
  "error": {
    "code": "SERVER_ERROR",
    "message": "An unexpected error occurred while retrieving locations."
  }
}
```

---

## 3. Get All Hospital Locations

**GET** `/hospitals`

### Description

Fetches all locations categorized as hospitals.

### Success Response – 200

```json
[
  {
    "id": 1,
    "locationType": "HOSPITAL",
    "address": "123 Street",
    "thana": "Dhanmondi",
    "po": "1209",
    "city": "Dhaka",
    "postalCode": 1205,
    "zoneId": 1
  }
]
```

---

## 4. Get All User Locations

**GET** `/users`

### Description

Fetches all locations categorized as users.

### Success Response – 200

```json
[
  {
    "id": 3,
    "locationType": "USER",
    "address": null,
    "thana": null,
    "po": null,
    "city": "Chittagong",
    "postalCode": 4000,
    "zoneId": 3
  }
]
```

---

## 5. Get All Doctor Locations

**GET** `/doctors`

### Description

Fetches all locations categorized as doctors.

### Success Response – 200

```json
[
  {
    "id": 2,
    "locationType": "DOCTOR",
    "address": "56 Road",
    "thana": "Gulshan",
    "po": "1212",
    "city": "Dhaka",
    "postalCode": 1212,
    "zoneId": 2
  }
]
```

---

## 6. Get Location by ID

**GET** `/id/{id}`

### Description

Fetches a location by its ID.

### Path Parameters

| Parameter | Type | Description            |
| --------- | ---- | ---------------------- |
| id        | Long | The ID of the location |

### Success Response – 200

```json
{
  "id": 2,
  "locationType": "DOCTOR",
  "address": "56 Road",
  "thana": "Gulshan",
  "po": "1212",
  "city": "Dhaka",
  "postalCode": 1212,
  "zoneId": 2
}
```

### Error Response – 404

```json
{
  "success": false,
  "error": {
    "code": "LOCATION_NOT_FOUND",
    "message": "No location found with the specified ID."
  }
}
```

---

## 7. Add New Location

**POST** `/add`

### Description

Adds a new location to the system.

### Request Body

```json
{
  "locationType": "USER",
  "address": "house-road-block",
  "thana": "thana",
  "po": "post-office",
  "city": "Sylhet",
  "postalCode": 1209,
  "zoneId": 5
}
```

### Success Response – 201

```json
{
  "id": 4,
  "locationType": "USER",
  "address": "house-road-block",
  "thana": "thana",
  "po": "post-office",
  "city": "Sylhet",
  "postalCode": 1209,
  "zoneId": 5
}
```

### Error Response – 400

```json
{
  "success": false,
  "error": {
    "code": "INVALID_LOCATION_DATA",
    "message": "Provided location data is invalid or incomplete."
  }
}
```

---

## 8. Update Location

**PUT** `/update`

### Description

Updates an existing location’s details.

### Request Body

```json
{
  "id": 4,
  "locationType": "USER",
  "address": "house-road-block",
  "thana": "thana",
  "po": "post-office",
  "city": "Rangpur",
  "postalCode": 1210,
  "zoneId": 5
}
```

### Success Response – 200

```json
{
  "id": 5,
  "name": "Khulna General Updated",
  "type": "HOSPITAL"
}
```

---

## 9. Delete Location by ID

**DELETE** `/delete/{id}`

### Description

Deletes a location by its ID.

### Path Parameters

| Parameter | Type | Description            |
| --------- | ---- | ---------------------- |
| id        | Long | The ID of the location |

### Success Response – 204

_No content_

### Error Response – 404

```json
{
  "success": false,
  "error": {
    "code": "LOCATION_NOT_FOUND",
    "message": "No location found with the specified ID."
  }
}
```

---
