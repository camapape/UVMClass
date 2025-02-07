-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS Actividad4;
USE Actividad4;

-- Crear la tabla alumnos
CREATE TABLE IF NOT EXISTS alumnos (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50) NOT NULL,
    nota FLOAT NOT NULL
);

-- Crear el trigger para controlar los valores de la nota
DELIMITER $$
CREATE TRIGGER trigger_check_nota_before_insert
BEFORE INSERT ON alumnos
FOR EACH ROW
BEGIN
    IF NEW.nota < 0 THEN
        SET NEW.nota = 0;
    ELSEIF NEW.nota > 10 THEN
        SET NEW.nota = 10;
    END IF;
END$$
DELIMITER ;

-- Insertar datos en la tabla con valores variados para verificar el trigger
INSERT INTO alumnos (nombre, apellido1, apellido2, nota) VALUES
('Juan', 'Perez', 'Lopez', 8.5),
('Ana', 'Gomez', 'Fernandez', 10),
('Carlos', 'Lopez', 'Gonzalez', -3),
('Luis', 'Martinez', 'Sanchez', 4.5),
('Elena', 'Hernandez', 'Ruiz', 7.2),
('Maria', 'Santos', 'Ortiz', 12),
('Pedro', 'Dominguez', 'Garcia', 5.8),
('Sofia', 'Navarro', 'Torres', 9.1),
('Javier', 'Cruz', 'Mendoza', 3.7),
('Andrea', 'Ortega', 'Silva', 6.3),
('Diego', 'Rojas', 'Paredes', 2.0),
('Valeria', 'Molina', 'Castro', 0),
('Oscar', 'Ramirez', 'Diaz', 10),
('Lucia', 'Alvarez', 'Vega', 1.5),
('Fernando', 'Fuentes', 'Reyes', 7.9);

-- Crear un procedimiento almacenado para obtener alumnos con nota igual o superior a un valor
DELIMITER $$
CREATE PROCEDURE ObtenerAlumnosPorNota(IN notaMin FLOAT)
BEGIN
    SELECT * FROM alumnos WHERE nota >= notaMin;
END$$
DELIMITER ;

-- Crear una función para contar el número total de registros en la tabla
DELIMITER $$
CREATE FUNCTION ContarAlumnos() RETURNS INT DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM alumnos;
    RETURN total;
END$$
DELIMITER ;

-- Verificar que el trigger funciona correctamente
SELECT * FROM alumnos;

-- Ejecutar el procedimiento almacenado con un ejemplo de consulta
CALL ObtenerAlumnosPorNota(5);

-- Ejecutar la función para contar alumnos
SELECT ContarAlumnos() AS TotalAlumnos;
