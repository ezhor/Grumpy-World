DROP DATABASE GrumpyWorld; -- Borrar línea al terminar los tests

-- Base de datos
CREATE DATABASE GrumpyWorld;
USE GrumpyWorld;

-- Tablas
CREATE TABLE Usuarios(
  ID INT NOT NULL AUTO_INCREMENT,
  Usuario NVARCHAR(30) NOT NULL UNIQUE,
  Contrasena NVARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Dispositivos(
	ID INT NOT NULL AUTO_INCREMENT,
    StringID VARCHAR(30) UNIQUE,
    IP VARCHAR(15),
    PRIMARY KEY(ID)
);

CREATE TABLE Usuarios_Dispositivos(
	ID_Usuario INT NOT NULL,
    ID_Dispositivo INT NOT NULL,
    CONSTRAINT FK_ID_Usuario FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID) ON DELETE CASCADE,
    CONSTRAINT FK_ID_Dispositivo FOREIGN KEY (ID_Dispositivo) REFERENCES Dispositivos(ID) ON DELETE CASCADE,
    PRIMARY KEY(ID_Usuario, ID_Dispositivo)
);

CREATE TABLE Usuarios_Usuarios(
	ID_Usuario1 INT NOT NULL,
    ID_Usuario2 INT NOT NULL,
    CONSTRAINT FK_ID_Usuario1 FOREIGN KEY (ID_Usuario1) REFERENCES Usuarios(ID) ON DELETE CASCADE,
    CONSTRAINT FK_ID_Usuario2 FOREIGN KEY (ID_Usuario2) REFERENCES Usuarios(ID) ON DELETE CASCADE,
    PRIMARY KEY(ID_Usuario1, ID_Usuario2)
);

CREATE TABLE Zonas(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  PRIMARY KEY(ID)
);

CREATE TABLE Rollos(
  ID_Usuario INT,
  Fuerza INT NOT NULL DEFAULT 1,
  Constitucion INT NOT NULL DEFAULT 1,
  Destreza INT NOT NULL DEFAULT 1,
  ID_Zona INT NOT NULL,
  CONSTRAINT FK_Rollos_Usuario FOREIGN KEY(ID_Usuario) REFERENCES Usuarios(ID) ON DELETE CASCADE,
  CONSTRAINT FK_Rollos_Zona FOREIGN KEY (ID_Zona) REFERENCES Zonas(ID) ON DELETE NO ACTION,
  PRIMARY KEY(ID_Usuario)
);

CREATE TABLE Rollos_Rollos(
	ID_Rollo1 INT NOT NULL,
    ID_Rollo2 INT NOT NULL,
    VidaRollo1 TINYINT NOT NULL DEFAULT 100,
    VidaRollo2 TINYINT NOT NULL DEFAULT 100,
    TurnoRollo1 TINYINT NULL,
    TurnoRollo2 TINYINT NULL,
    CONSTRAINT FK_Rollos_Rollos_1 FOREIGN KEY (ID_Rollo1) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Rollos_Rollos_2 FOREIGN KEY (ID_Rollo2) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE
);

CREATE TABLE Equipables(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  Tipo CHAR(1),
  Bonus INT NOT NULL,
  DestrezaNecesaria INT NOT NULL,
  PRIMARY KEY(ID),
  CHECK (Tipo='S' OR Tipo='A')
);

CREATE TABLE Rollos_Equipables(
  ID_Rollo INT NOT NULL,
  ID_Equipable INT NOT NULL,
  Equipada CHAR(1) NOT NULL DEFAULT 'N',
  CONSTRAINT FK_Rollos_Equipables_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
  CONSTRAINT FK_Rollos_Equipables_ID_Equipable FOREIGN KEY (ID_Equipable) REFERENCES Equipables(ID) ON DELETE CASCADE,
  PRIMARY KEY(ID_Rollo, ID_Equipable),
  CHECK (Equipada='S' OR Tipo='N')
);

CREATE TABLE Materiales(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  PRIMARY KEY(ID)
);

CREATE TABLE Rollos_Materiales(
	ID_Rollo INT NOT NULL,
    ID_Material INT NOT NULL,
    Cantidad INT NOT NULL DEFAULT 0,
    CONSTRAINT FK_Rollos_Materiales_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
	CONSTRAINT FK_Rollos_Materiales_ID_Material FOREIGN KEY (ID_Material) REFERENCES Materiales(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID_Rollo, ID_Material)
);

CREATE TABLE Enemigos(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  Fuerza INT NOT NULL,
  Constitucion INT NOT NULL,
  Destreza INT NOT NULL,
  ID_Zona INT NOT NULL,
  Jefe BIT(1) NOT NULL,
  CONSTRAINT FK_Enemigos_Zona FOREIGN KEY (ID_Zona) REFERENCES Zonas(ID) ON DELETE NO ACTION,
  PRIMARY KEY(ID)
);

CREATE TABLE Rollos_Enemigos(
	ID_Rollo INT NOT NULL,
    ID_Enemigo INT NOT NULL,
    VidaRollo TINYINT NOT NULL DEFAULT 100,
    VidaEnemigo TINYINT NOT NULL DEFAULT 100,
    CONSTRAINT FK_Rollos_Enemigos_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Rollos_Enemigos_Enemigo FOREIGN KEY (ID_Enemigo) REFERENCES Enemigos(ID) ON DELETE CASCADE
);

-- Funciones y procedimientos
DELIMITER $$
CREATE FUNCTION existeUsuario(nombre_usuario NVARCHAR(30))
RETURNS BIT
BEGIN
  /*DECLARE existe BIT;
  SET existe = FALSE;
    IF((SELECT COUNT(*) FROM Usuarios WHERE Usuario = nombre_usuario)>0) THEN
    BEGIN
		SET existe = TRUE;
    END;
    END IF;*/
	RETURN (SELECT COUNT(*) FROM Usuarios WHERE Usuario = nombre_usuario)>0;
END $$

CREATE PROCEDURE crearUsuario(IN usuario NVARCHAR(30), IN contrasena NVARCHAR(255), OUT conseguido BIT)
BEGIN
    INSERT INTO Usuarios (Usuario, Contrasena) VALUE (usuario, contrasena);
    IF(ROW_COUNT()>0) THEN
    BEGIN
		INSERT INTO Rollos(ID_Usuario, ID_Zona) SELECT LAST_INSERT_ID(), ID FROM Zonas WHERE Nombre='bano';
        SELECT 1 INTO conseguido;
    END;
    END IF;
    
END $$

DELIMITER ;

-- Datos iniciales

-- Zonas
INSERT INTO Zonas (Nombre) VALUES('bano');
INSERT INTO Zonas (Nombre) VALUES('cocina');
INSERT INTO Zonas (Nombre) VALUES('oficina');
INSERT INTO Zonas (Nombre) VALUES('parque');

-- Enemigos
INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'stripper', 10, 20, 30, 0, ID FROM Zonas WHERE Nombre = 'bano';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'cepillo', 10, 30, 20, 0, ID FROM Zonas WHERE Nombre = 'bano';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'cuchilla', 30, 20, 10, 0, ID FROM Zonas WHERE Nombre = 'bano';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'champu', 20, 10, 30, 0, ID FROM Zonas WHERE Nombre = 'bano';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'vater', 30, 50, 20, 1, ID FROM Zonas WHERE Nombre = 'bano';


INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'leche', 60, 80, 70, 0, ID FROM Zonas WHERE Nombre = 'cocina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'zanahoria', 80, 60, 70, 0, ID FROM Zonas WHERE Nombre = 'cocina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'cuchara', 70, 80, 60, 0, ID FROM Zonas WHERE Nombre = 'cocina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'limon', 70, 60, 80, 0, ID FROM Zonas WHERE Nombre = 'cocina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'calabaza', 80, 100, 70, 1, ID FROM Zonas WHERE Nombre = 'cocina';


INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'raton', 120, 110, 130, 0, ID FROM Zonas WHERE Nombre = 'oficina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'grapadora', 130, 120, 110, 0, ID FROM Zonas WHERE Nombre = 'oficina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'lapiz', 130, 110, 120, 0, ID FROM Zonas WHERE Nombre = 'oficina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'libro', 110, 130, 120, 0, ID FROM Zonas WHERE Nombre = 'oficina';

INSERT INTO Enemigos (Nombre, Fuerza, Constitucion, Destreza, Jefe, ID_Zona)
	SELECT 'pendrive', 110, 150, 120, 1, ID FROM Zonas WHERE Nombre = 'oficina';


-- Usuario de prueba
CALL crearUsuario('dani', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);