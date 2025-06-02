-- 1. Create ENUM type for location_type
CREATE TYPE location_type AS ENUM ('USER', 'DOCTOR', 'HOSPITAL');

-- 2. Create locations table
CREATE TABLE locations (
                           id TEXT PRIMARY KEY,
                           location_type location_type NOT NULL,
                           address TEXT,
                           thana TEXT,
                           po TEXT,
                           city TEXT NOT NULL,
                           postal_code BIGINT NOT NULL,
                           zone_id BIGINT NOT NULL
);

-- 3. Insert dummy data
INSERT INTO locations (id, location_type, address, thana, po, city, postal_code, zone_id)
VALUES
    ('loc-001', 'HOSPITAL', '123 Street', 'Dhanmondi', '1209', 'Dhaka', 1205, 1),
    ('loc-002', 'DOCTOR', '56 Road', 'Gulshan', '1212', 'Dhaka', 1212, 2),
    ('loc-003', 'USER', NULL, NULL, NULL, 'Chittagong', 4000, 3);