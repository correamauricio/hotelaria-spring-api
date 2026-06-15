
USE hotel_management;

CREATE TABLE guests (
                        guest_id INT AUTO_INCREMENT PRIMARY KEY,
                        full_name VARCHAR(150) NOT NULL,
                        document_number VARCHAR(20) NOT NULL UNIQUE,
                        birth_date DATE NOT NULL,
                        email VARCHAR(150) NOT NULL UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE room (
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
USE hotel_management;

-- --------------------------------------------------------
-- 1. Inserindo Hóspedes (Guests)
-- --------------------------------------------------------
INSERT INTO guests (full_name, document_number, birth_date, email) VALUES
                                                                       ('João da Silva', '12345678901', '1985-04-12', 'joao.silva@example.com'),
                                                                       ('Maria Oliveira', '98765432100', '1990-08-25', 'maria.oliveira@example.com'),
                                                                       ('Carlos Eduardo Santos', '45612378922', '1978-11-03', 'carlos.santos@example.com'),
                                                                       ('Ana Carolina Souza', '78945612333', '1995-02-15', 'ana.souza@example.com'),
                                                                       ('Roberto Almeida', '32165498744', '1982-06-30', 'roberto.almeida@example.com');

-- --------------------------------------------------------
-- 2. Inserindo Quartos (Rooms)
-- --------------------------------------------------------
INSERT INTO room (room_number, category, base_daily_rate, status) VALUES
                                                                       ('101', 'Standard Room - 1 Queen bed', 150.00, 'Available'),
                                                                       ('102', 'Standard Room - 2 Twin beds', 140.00, 'Maintenance'),
                                                                       ('201', 'Deluxe Room - 1 King bed', 250.00, 'Available'),
                                                                       ('202', 'Deluxe Room - 1 King bed + Balcony', 280.00, 'Occupied'),
                                                                       ('301', 'Premium Suite - 2 beds + Kitchen', 450.00, 'Occupied'),
                                                                       ('302', 'Presidential Suite', 900.00, 'Available');

-- --------------------------------------------------------
-- 3. Inserindo Reservas (Reservations)
-- As datas e taxas foram baseadas nos quartos acima
-- --------------------------------------------------------
INSERT INTO reservations (guest_id, room_id, expected_check_in_date, expected_check_out_date, applied_daily_rate, status, notes) VALUES
-- Reserva 1: Já finalizada (Checked-out)
(1, 1, '2023-10-01', '2023-10-05', 145.00, 'Checked-out', 'Hóspede pediu travesseiros extras.'),

-- Reserva 2: Em andamento (Checked-in) - Quarto 202 (Ocupado)
(2, 4, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), 280.00, 'Checked-in', 'Late check-out solicitado para as 14h.'),

-- Reserva 3: Futura (Scheduled)
(3, 3, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), 220.00, 'Scheduled', 'Promoção de aniversário aplicada.'),

-- Reserva 4: Em andamento (Checked-in) - Quarto 301 (Ocupado)
(4, 5, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), 450.00, 'Checked-in', 'Hóspede com restrição alimentar (sem glúten).'),

-- Reserva 5: Cancelada (Cancelled)
(5, 6, '2023-11-20', '2023-11-25', 850.00, 'Cancelled', 'Cancelado devido a problemas de saúde.');

-- --------------------------------------------------------
-- 4. Inserindo Consumos (Room Charges)
-- Associados apenas às reservas que já fizeram Check-in ou Check-out
-- --------------------------------------------------------
INSERT INTO room_charges (reservation_id, category, description, amount) VALUES
                                                                             (1, 'Minibar', '2x Água mineral, 1x Cerveja Artesanal', 35.00),
                                                                             (1, 'Restaurant', 'Jantar no restaurante principal', 120.50),
                                                                             (1, 'Laundry', 'Lavagem de terno', 45.00),
                                                                             (2, 'Minibar', '1x Refrigerante, 1x Chocolate', 20.00),
                                                                             (4, 'Restaurant', 'Café da manhã no quarto', 75.00),
                                                                             (4, 'Other', 'Massagem no Spa (1 hora)', 200.00);

-- --------------------------------------------------------
-- 5. Inserindo Pagamentos (Payments)
-- --------------------------------------------------------
INSERT INTO payments (reservation_id, payment_timing, amount_paid) VALUES
-- Pagamentos da Reserva 1 (Finalizada: diárias + consumo)
(1, 'Check-in', 290.00),   -- Pagou 50% adiantado
(1, 'Check-out', 490.50),  -- Pagou restante das diárias (290) + consumos (200.50)

-- Pagamento da Reserva 2 (Em andamento)
(2, 'Check-in', 840.00),   -- Pagou todas as 3 diárias no check-in

-- Pagamento da Reserva 4 (Em andamento)
(4, 'Check-in', 450.00);   -- Pagou apenas a 1ª diária como sinal