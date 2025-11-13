-- Script to set up the database with tables and procedures for testing

-- Create usuarios table
CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test data into usuarios table
INSERT INTO usuarios (username, password) VALUES 
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3');

-- Create auditoria_login table
CREATE TABLE IF NOT EXISTS auditoria_login (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Create sessoes table
CREATE TABLE IF NOT EXISTS sessoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Create procedure for user login
DELIMITER //
CREATE PROCEDURE login_user(IN p_username VARCHAR(255), IN p_password VARCHAR(255))
BEGIN
    DECLARE user_id INT;

    SELECT id INTO user_id FROM usuarios WHERE username = p_username AND password = p_password LIMIT 1;

    IF user_id IS NOT NULL THEN
        INSERT INTO auditoria_login (usuario_id) VALUES (user_id);
        SELECT 'Login successful' AS message;
    ELSE
        SELECT 'Invalid credentials' AS message;
    END IF;
END //
DELIMITER ;