-- 1. Creating ENUM type for location_type
CREATE TYPE location_type AS ENUM ('USER', 'DOCTOR', 'HOSPITAL');

-- 2. Creating locations table
CREATE TABLE locations (
                           id BIGSERIAL PRIMARY KEY,  -- changed from TEXT to BIGSERIAL for auto-incrementing Long IDs
                           location_type location_type NOT NULL,
                           address TEXT,
                           thana TEXT,
                           po TEXT,
                           city TEXT NOT NULL,
                           postal_code BIGINT NOT NULL,
                           zone_id BIGINT NOT NULL
);

-- 3. Inserting dummy data
INSERT INTO locations (location_type, address, thana, po, city, postal_code, zone_id)
VALUES
    ('HOSPITAL', '123 Street', 'Dhanmondi', '1209', 'Dhaka', 1205, 1),
    ('DOCTOR', '56 Road', 'Gulshan', '1212', 'Dhaka', 1212, 2),
    ('USER', NULL, NULL, NULL, 'Chittagong', 4000, 3);
