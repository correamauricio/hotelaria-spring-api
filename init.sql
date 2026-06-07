
USE hotel_management;

CREATE TABLE guests (
                        guest_id INT AUTO_INCREMENT PRIMARY KEY,
                        full_name VARCHAR(150) NOT NULL,
                        document_number VARCHAR(20) NOT NULL UNIQUE,
                        birth_date DATE NOT NULL,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms (
                       room_id INT AUTO_INCREMENT PRIMARY KEY,
                       room_number VARCHAR(10) NOT NULL UNIQUE,
                       category VARCHAR(100) NOT NULL COMMENT 'Ex: Premium Suite - 2 beds',
                       base_daily_rate DECIMAL(10, 2) NOT NULL,
                       status ENUM('Available', 'Occupied', 'Maintenance') DEFAULT 'Available'
);

CREATE TABLE reservations (
                              reservation_id INT AUTO_INCREMENT PRIMARY KEY,
                              guest_id INT NOT NULL,
                              room_id INT NOT NULL,
                              expected_check_in_date DATE NOT NULL,
                              expected_check_out_date DATE NOT NULL,
                              applied_daily_rate DECIMAL(10, 2) NOT NULL COMMENT 'Locks the price at the time of booking',
                              status ENUM('Scheduled', 'Checked-in', 'Checked-out', 'Cancelled') DEFAULT 'Scheduled',
                              notes TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                              FOREIGN KEY (guest_id) REFERENCES guests(guest_id) ON DELETE RESTRICT,
                              FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE RESTRICT
);

CREATE TABLE room_charges (
                              charge_id INT AUTO_INCREMENT PRIMARY KEY,
                              reservation_id INT NOT NULL,
                              category ENUM('Minibar', 'Restaurant', 'Laundry', 'Other') NOT NULL,
                              description VARCHAR(255) NOT NULL,
                              amount DECIMAL(10, 2) NOT NULL,
                              recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                              FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id) ON DELETE CASCADE
);

CREATE TABLE payments (
                          payment_id INT AUTO_INCREMENT PRIMARY KEY,
                          reservation_id INT NOT NULL,
                          payment_timing ENUM('Check-in', 'Check-out') NOT NULL,
                          amount_paid DECIMAL(10, 2) NOT NULL,
                          payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                          FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id) ON DELETE CASCADE
);