DROP DATABASE GrumpyWorld; -- Borrar línea al terminar los tests

-- Base de datos
CREATE DATABASE GrumpyWorld;
USE GrumpyWorld;

-- Tablas
CREATE TABLE Usuarios(
  ID INT NOT NULL AUTO_INCREMENT,
  Usuario NVARCHAR(20) NOT NULL UNIQUE,
  Contrasena NVARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Amigos(
	ID_Usuario1 INT NOT NULL,
    ID_Usuario2 INT NOT NULL,
    CONSTRAINT FK_ID_Usuario1 FOREIGN KEY (ID_Usuario1) REFERENCES Usuarios(ID) ON DELETE CASCADE,
    CONSTRAINT FK_ID_Usuario2 FOREIGN KEY (ID_Usuario2) REFERENCES Usuarios(ID) ON DELETE CASCADE,
    PRIMARY KEY(ID_Usuario1, ID_Usuario2)
);

CREATE TABLE Zonas(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  Nivel INT NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Atributos(
	ID INT NOT NULL AUTO_INCREMENT,
	Fuerza INT NOT NULL DEFAULT 1,
	Constitucion INT NOT NULL DEFAULT 1,
	Destreza INT NOT NULL DEFAULT 1,
    FinEntrenamiento INT NULL DEFAULT 0,
    PRIMARY KEY(ID)
);

CREATE TABLE Rollos(
  ID_Usuario INT,
  ID_Atributos INT NOT NULL,
  ID_Zona INT NOT NULL,
  Nivel INT NOT NULL DEFAULT 0,
  Honor INT NOT NULL DEFAULT 0,
  Pactos TINYINT NOT NULL DEFAULT 0,
  CONSTRAINT FK_Rollos_Usuario FOREIGN KEY(ID_Usuario) REFERENCES Usuarios(ID) ON DELETE CASCADE,
  CONSTRAINT FK_Rollos_Zona FOREIGN KEY (ID_Zona) REFERENCES Zonas(ID) ON DELETE NO ACTION,
  PRIMARY KEY(ID_Usuario)
);

CREATE TABLE Duelos(
	ID_Rollo INT NOT NULL,
    ID_RolloEnemigo INT NOT NULL,
    Vida TINYINT NULL, -- Es NULL cuando un jugador ha decidido su turno pero el otro jugado aún no
    Turno TINYINT NULL,
    Ataque TINYINT NULL,
    Momento INT NULL,
    CONSTRAINT FK_Duelos_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Duelos_RolloEnemigo FOREIGN KEY (ID_RolloEnemigo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE
);

CREATE TABLE Equipables(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  Tipo CHAR(1),
  Bonus INT NOT NULL,
  DestrezaNecesaria INT NOT NULL,
  NivelNecesario INT NOT NULL,
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

CREATE TABLE Materiales_Materiales(
	ID_Material INT NOT NULL,
    ID_Submaterial INT NOT NULL,
    Cantidad INT NOT NULL,
    CONSTRAINT FK_Materiales_Materiales_Material FOREIGN KEY (ID_Material) REFERENCES Materiales(ID) ON DELETE CASCADE,
    CONSTRAINT FK_Materiales_Materiales_Submaterial FOREIGN KEY (ID_Submaterial) REFERENCES Materiales(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID_Material, ID_Submaterial)
);

CREATE TABLE Rollos_Materiales(
	ID_Rollo INT NOT NULL,
    ID_Material INT NOT NULL,
    Cantidad INT NOT NULL DEFAULT 0,
    CONSTRAINT FK_Rollos_Materiales_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
	CONSTRAINT FK_Rollos_Materiales_ID_Material FOREIGN KEY (ID_Material) REFERENCES Materiales(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID_Rollo, ID_Material)
);

CREATE TABLE Equipables_Materiales(
	ID_Equipable INT NOT NULL,
    ID_Material INT NOT NULL,
    Cantidad INT NOT NULL DEFAULT 0,
    CONSTRAINT FK_Equipables_Materiales_ID_Equipable FOREIGN KEY (ID_Equipable) REFERENCES Equipables(ID) ON DELETE CASCADE,
	CONSTRAINT FK_Equipables_Materiales_ID_Material FOREIGN KEY (ID_Material) REFERENCES Materiales(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID_Equipable, ID_Material)
);

CREATE TABLE Enemigos(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(20) NOT NULL UNIQUE,
  ID_Atributos INT NOT NULL,
  ID_Zona INT NOT NULL,
  EsJefe BIT(1) NOT NULL,
  CONSTRAINT FK_Enemigos_Zona FOREIGN KEY (ID_Zona) REFERENCES Zonas(ID) ON DELETE NO ACTION,
  CONSTRAINT FK_Enemigos_Atributos FOREIGN KEY (ID_Atributos) REFERENCES Atributos(ID) ON DELETE NO ACTION,
  PRIMARY KEY(ID)
);

CREATE TABLE Vencidos(
	ID_Rollo INT NOT NULL,
    ID_Enemigo INT NOT NULL,
    CONSTRAINT FK_Vencidos_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Vencidos_Enemigo FOREIGN KEY (ID_Enemigo) REFERENCES Enemigos(ID) ON DELETE CASCADE
);

CREATE TABLE Caza(
	ID_Rollo INT NOT NULL,
    ID_Enemigo INT NOT NULL,
    VidaRollo TINYINT NOT NULL DEFAULT 100,
    VidaEnemigo TINYINT NOT NULL DEFAULT 100,
    AtaqueRollo TINYINT NULL,
    AtaqueEnemigo TINYINT NULL,
    CONSTRAINT FK_Rollos_Enemigos_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Rollos_Enemigos_Enemigo FOREIGN KEY (ID_Enemigo) REFERENCES Enemigos(ID) ON DELETE CASCADE
);



-- Funciones
DELIMITER $$
CREATE FUNCTION existeUsuario(nombre_usuario NVARCHAR(20)) -- En desuso
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

CREATE FUNCTION enemigoMasRapido(destrezaRollo INT, destrezaEnemigo INT) -- En desuso
RETURNS BIT
BEGIN
	RETURN destrezaEnemigo>destrezaRollo;
END $$

CREATE FUNCTION rangoRollo(honorRollo INT, idRollo INT)
RETURNS INT
BEGIN
	SET @rango = (SELECT COUNT(*) FROM Rollos WHERE Honor>honorRollo OR (Honor=honorRollo AND ID_Usuario<idRollo) );
    SET @rango = @rango+1;
	RETURN @rango;
END $$


-- Procedimientos

CREATE PROCEDURE crearUsuario(IN usuario NVARCHAR(20), IN contrasena NVARCHAR(255), OUT conseguido BIT)
BEGIN
    INSERT INTO Usuarios (Usuario, Contrasena) VALUE (usuario, contrasena);
    IF(ROW_COUNT()>0) THEN
    BEGIN
		SET @ID_Usuario = LAST_INSERT_ID();
        INSERT INTO Atributos () VALUES ();
        SET @ID_Atributo = LAST_INSERT_ID();
		INSERT INTO Rollos(ID_Usuario, ID_Atributos, ID_Zona) SELECT @ID_Usuario, @ID_Atributo, ID FROM Zonas WHERE Nombre='bano';
        
        SELECT 1 INTO conseguido;
    END;
    END IF;
    
END $$

CREATE PROCEDURE entrenar(IN idUsuario INT, IN atributo VARCHAR(15))
BEGIN
	IF (atributo = 'fuerza') THEN
		BEGIN
			UPDATE Atributos
				INNER JOIN Rollos
				ON Atributos.ID = Rollos.ID_Atributos
			SET Atributos.FinEntrenamiento = UNIX_TIMESTAMP()+((Atributos.Fuerza)*(Atributos.Fuerza)),
				Atributos.Fuerza = Atributos.Fuerza+1
			WHERE Rollos.ID_Usuario = idUsuario;
				
        END;
        ELSE IF (atributo = 'constitucion') THEN
			BEGIN
				UPDATE Atributos
					INNER JOIN Rollos
					ON Atributos.ID = Rollos.ID_Atributos
				SET Atributos.FinEntrenamiento = UNIX_TIMESTAMP()+((Atributos.Constitucion)*(Atributos.Constitucion)),
					Atributos.Constitucion = Atributos.Constitucion+1
				WHERE Rollos.ID_Usuario = idUsuario;
					
			END;
            ELSE IF (atributo = 'destreza') THEN
				BEGIN
					UPDATE Atributos
						INNER JOIN Rollos
						ON Atributos.ID = Rollos.ID_Atributos
					SET Atributos.FinEntrenamiento = UNIX_TIMESTAMP()+((Atributos.Destreza)*(Atributos.Destreza)),
						Atributos.Destreza = Atributos.Destreza+1
				WHERE Rollos.ID_Usuario = idUsuario;
						
				END;
				END IF;
			END IF;
        END IF;
END $$

CREATE PROCEDURE crearEnemigo(IN nNombre NVARCHAR(20), IN nFuerza INT, IN nConstitucion INT, IN nDestreza INT, IN nEsJefe BIT, IN nNombreZona NVARCHAR(30))
BEGIN
	INSERT INTO Atributos (Fuerza, Constitucion, Destreza) VALUE (nFuerza, nConstitucion, nDestreza);
    INSERT INTO Enemigos (Nombre, ID_Atributos, ID_Zona, Esjefe)
		SELECT nNombre, LAST_INSERT_ID(), ID, nEsJefe FROM Zonas WHERE Nombre = nNombreZona;
END $$

CREATE PROCEDURE asignarCaza(IN idRollo INT, IN nombreEnemigo NVARCHAR(20))
BEGIN
	INSERT INTO Caza (ID_Rollo, ID_Enemigo)
		SELECT idRollo, ID FROM Enemigos WHERE Enemigos.Nombre = nombreEnemigo;
END $$

DELIMITER ;
-- Datos iniciales

-- Zonas
INSERT INTO Zonas (Nombre, Nivel) VALUES
	('bano', 1),
	('cocina', 2),
    ('oficina', 3),
    ('parque', 4),
    ('cementerio', 5),
    ('infierno', 6);

CALL crearEnemigo('stripper', 10, 20, 30, 0, 'bano');
CALL crearEnemigo('cepillo', 10, 30, 20, 0, 'bano');
CALL crearEnemigo('cuchilla', 30, 10, 20, 0, 'bano');
CALL crearEnemigo('champu', 20, 10, 30, 0, 'bano');
CALL crearEnemigo('vater', 30, 50, 20, 1, 'bano');

CALL crearEnemigo('leche', 60, 80, 70, 0, 'cocina');
CALL crearEnemigo('zanahoria', 80, 60, 70, 0, 'cocina');
CALL crearEnemigo('cuchara', 70, 80, 60, 0, 'cocina');
CALL crearEnemigo('limon', 70, 60, 80, 0, 'cocina');
CALL crearEnemigo('calabaza', 80, 100, 70, 1, 'cocina');

CALL crearEnemigo('raton', 120, 110, 130, 0, 'oficina');
CALL crearEnemigo('grapadora', 130, 120, 110, 0, 'oficina');
CALL crearEnemigo('lapiz', 130, 110, 120, 0, 'oficina');
CALL crearEnemigo('libro', 110, 130, 120, 0, 'oficina');
CALL crearEnemigo('pendrive', 110, 150, 120, 1, 'oficina');

-- Pruebas
CALL crearUsuario('dani', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL asignarCaza(1, 'champu');

UPDATE Rollos SET Honor = 1000 WHERE ID_Usuario = 2;