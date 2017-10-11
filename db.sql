DROP DATABASE GrumpyWorld; -- Borrar línea al terminar los tests

-- Base de datos
CREATE DATABASE GrumpyWorld;
USE GrumpyWorld;

-- Tablas
CREATE TABLE Usuarios(
  ID INT NOT NULL AUTO_INCREMENT,
  Usuario NVARCHAR(30) NOT NULL UNIQUE,
  Contrasena NVARCHAR(30) NOT NULL,
  PRIMARY KEY(ID)
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
  Material INT NOT NULL,
  ProbabilidadMaterial INT NOT NULL,
  ID_Zona INT NOT NULL,
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

-- Datos iniciales
INSERT INTO Zonas (Nombre) VALUES('Baño');
INSERT INTO Equipables (Nombre, Tipo, Bonus, DestrezaNecesaria) VALUES ('Cartona', 'A', 1, 0);

-- Pruebas

-- INSERT INTO Usuarios (Usuario, Contrasena) VALUES ("dani", "contrasena");
-- INSERT INTO Rollos (ID_Usuario) VALUES (LAST_INSERT_ID());

-- SELECT * FROM Usuarios;
-- SELECT * FROM Rollos;
-- SELECT * FROM Equipables;