-- SCRIPT DE BASE DE DATOS PARA LA EVIDENCIA AA5-EV01
USE artechsanias_db;

-- Crear la tabla de usuarios requerida para el inicio de sesión
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Insertar un usuario de prueba para ensayar el servicio
INSERT INTO usuarios (usuario, password) 
VALUES ('andrea_admin', 'crochet2026')
ON DUPLICATE KEY UPDATE usuario=usuario;