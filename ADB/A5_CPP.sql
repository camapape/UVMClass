-- Crear el Tipo de Objeto "Persona" como NOT FINAL
CREATE OR REPLACE TYPE Persona AS OBJECT 
(
    -- Atributos encapsulados (privados)
    DNI_P VARCHAR2(20),
    Nombre_P VARCHAR2(100),

    -- Métodos GET para acceder a los atributos
    MEMBER FUNCTION getDNI RETURN VARCHAR2,
    MEMBER FUNCTION getNombre RETURN VARCHAR2
) NOT FINAL;
/
CREATE OR REPLACE TYPE BODY Persona AS 
    -- Implementación del método GET para DNI_P
    MEMBER FUNCTION getDNI RETURN VARCHAR2 IS 
    BEGIN
        RETURN DNI_P;
    END getDNI;
    
    -- Implementación del método GET para Nombre_P
    MEMBER FUNCTION getNombre RETURN VARCHAR2 IS 
    BEGIN
        RETURN Nombre_P;
    END getNombre;
END;
/
DECLARE
    p Persona;  -- Variable basada en el objeto Persona
BEGIN
    -- Asignamos valores al objeto
    p := Persona('12345678A', 'Juan Pérez');

    -- Mostramos los valores usando los métodos GET
    DBMS_OUTPUT.PUT_LINE('DNI: ' || p.getDNI);
    DBMS_OUTPUT.PUT_LINE('Nombre: ' || p.getNombre);
END;
/
CREATE OR REPLACE TYPE Profesor UNDER Persona 
(
    -- Atributos encapsulados
    NCuenta_Pr VARCHAR2(20),
    Especialidad VARCHAR2(100),

    -- Métodos GET para acceder a los atributos
    MEMBER FUNCTION getNCuenta RETURN VARCHAR2,
    MEMBER FUNCTION getEspecialidad RETURN VARCHAR2
) NOT FINAL;
/
CREATE OR REPLACE TYPE BODY Profesor AS 
    -- Método GET para NCuenta_Pr
    MEMBER FUNCTION getNCuenta RETURN VARCHAR2 IS 
    BEGIN
        RETURN NCuenta_Pr;
    END getNCuenta;
    
    -- Método GET para Especialidad
    MEMBER FUNCTION getEspecialidad RETURN VARCHAR2 IS 
    BEGIN
        RETURN Especialidad;
    END getEspecialidad;
END;
/
CREATE OR REPLACE TYPE Alumno UNDER Persona 
(
    -- Atributos encapsulados
    Edad_A NUMBER,
    Semestre NUMBER,

    -- Métodos GET para acceder a los atributos
    MEMBER FUNCTION getEdad RETURN NUMBER,
    MEMBER FUNCTION getSemestre RETURN NUMBER
) FINAL;
/
CREATE OR REPLACE TYPE BODY Alumno AS 
    -- Método GET para Edad_A
    MEMBER FUNCTION getEdad RETURN NUMBER IS 
    BEGIN
        RETURN Edad_A;
    END getEdad;
    
    -- Método GET para Semestre
    MEMBER FUNCTION getSemestre RETURN NUMBER IS 
    BEGIN
        RETURN Semestre;
    END getSemestre;
END;
/
CREATE OR REPLACE TYPE Asignatura AS OBJECT 
(
    -- Atributos encapsulados
    Codigo_A VARCHAR2(20),
    Nombre_As VARCHAR2(100),
    Creditos NUMBER,

    -- Métodos GET para acceder a los atributos encapsulados
    MEMBER FUNCTION getCodigo RETURN VARCHAR2,
    MEMBER FUNCTION getNombre RETURN VARCHAR2,
    MEMBER FUNCTION getCreditos RETURN NUMBER
);
/
CREATE OR REPLACE TYPE BODY Asignatura AS 
    -- Método GET para Codigo_A
    MEMBER FUNCTION getCodigo RETURN VARCHAR2 IS 
    BEGIN
        RETURN Codigo_A;
    END getCodigo;
    
    -- Método GET para Nombre_As
    MEMBER FUNCTION getNombre RETURN VARCHAR2 IS 
    BEGIN
        RETURN Nombre_As;
    END getNombre;

    -- Método GET para Creditos
    MEMBER FUNCTION getCreditos RETURN NUMBER IS 
    BEGIN
        RETURN Creditos;
    END getCreditos;
END;
/
DECLARE
    asig Asignatura;
BEGIN
    -- Crear una asignatura
    asig := Asignatura('MAT101', 'Matemáticas', 4);

    -- Mostrar valores usando los métodos GET
    DBMS_OUTPUT.PUT_LINE('Código: ' || asig.getCodigo);
    DBMS_OUTPUT.PUT_LINE('Nombre: ' || asig.getNombre);
    DBMS_OUTPUT.PUT_LINE('Créditos: ' || asig.getCreditos);
END;
/
-- Crear tabla para Persona (Tabla base para herencia)
CREATE TABLE Personas OF Persona 
(
    CONSTRAINT pk_personas PRIMARY KEY (DNI_P)
);

-- Crear tabla para Profesor, heredando de Persona
CREATE TABLE Profesores OF Profesor 
(
    CONSTRAINT pk_profesores PRIMARY KEY (DNI_P),
    CONSTRAINT fk_profesores_personas FOREIGN KEY (DNI_P) REFERENCES Personas(DNI_P)
);

-- Crear tabla para Alumno, heredando de Persona
CREATE TABLE Alumnos OF Alumno 
(
    CONSTRAINT pk_alumnos PRIMARY KEY (DNI_P),
    CONSTRAINT fk_alumnos_personas FOREIGN KEY (DNI_P) REFERENCES Personas(DNI_P)
);

-- Crear tabla para Asignatura
CREATE TABLE Asignaturas OF Asignatura 
(
    CONSTRAINT pk_asignaturas PRIMARY KEY (Codigo_A)
);

-- Crear la tabla Rel_Alumno_Asignatura
CREATE TABLE Rel_Alumno_Asignatura 
(
    ID_Rel NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- Identificador único
    Alumno Alumno,       -- Tipo objeto Alumno
    Asignatura Asignatura, -- Tipo objeto Asignatura
    Profesor Profesor    -- Tipo objeto Profesor
);

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('11111111A', 'P00123', 'Pedro Ramírez', 'Matemáticas');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('22222222B', 'P00456', 'Laura Fernández', 'Física');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('33333333C', 'P00789', 'Javier Morales', 'Programación');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('44444444D', 'P01234', 'Andrea López', 'Historia');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('55555555E', 'P05678', 'Roberto Torres', 'Química');

INSERT INTO Personas (DNI_P, Nombre_P) VALUES ('11111111A', 'Pedro Ramírez');
INSERT INTO Personas (DNI_P, Nombre_P) VALUES ('22222222B', 'Laura Fernández');
INSERT INTO Personas (DNI_P, Nombre_P) VALUES ('33333333C', 'Javier Morales');
INSERT INTO Personas (DNI_P, Nombre_P) VALUES ('44444444D', 'Andrea López');
INSERT INTO Personas (DNI_P, Nombre_P) VALUES ('55555555E', 'Roberto Torres');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('11111111A', 'P00123', 'Pedro Ramírez', 'Matemáticas');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('22222222B', 'P00456', 'Laura Fernández', 'Física');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('33333333C', 'P00789', 'Javier Morales', 'Programación');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('44444444D', 'P01234', 'Andrea López', 'Historia');

INSERT INTO Profesores (DNI_P, NCuenta_Pr, Nombre_P, Especialidad) 
VALUES ('55555555E', 'P05678', 'Roberto Torres', 'Química');

-- Confirmar los cambios
COMMIT;


-- Confirmar los cambios
COMMIT;


-- Confirmar los cambios
COMMIT;

SELECT * FROM Personas;
SELECT * FROM Alumnos;
SELECT * FROM Profesores;
SELECT * FROM Asignaturas;
SELECT * FROM Rel_Alumno_Asignatura;
