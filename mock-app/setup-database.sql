-- Database setup for QA Tests
-- PostgreSQL version

-- Drop existing tables if they exist
DROP TABLE IF EXISTS auditoria_login CASCADE;
DROP TABLE IF EXISTS sessoes CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- Create usuarios table
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER',
    bloqueado BOOLEAN DEFAULT FALSE,
    tentativas INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create auditoria_login table
CREATE TABLE auditoria_login (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    success BOOLEAN DEFAULT TRUE
);

-- Create sessoes table
CREATE TABLE sessoes (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuarios(id),
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP
);

-- Insert test users
INSERT INTO usuarios (username, password, role, bloqueado, tentativas) VALUES 
('admin', 'admin123', 'ADMIN', FALSE, 0),
('user', 'user123', 'USER', FALSE, 0),
('visitor', 'visitor123', 'VISITOR', FALSE, 0),
('blocked_user', 'any_password', 'USER', TRUE, 5);

-- Create indexes for better performance
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_auditoria_login_username ON auditoria_login(username);
CREATE INDEX idx_sessoes_usuario_id ON sessoes(usuario_id);

-- Display created data
SELECT * FROM usuarios;
