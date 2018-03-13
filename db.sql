DROP DATABASE GrumpyWorld; -- Borrar línea al terminar los tests

-- Base de datos
CREATE DATABASE GrumpyWorld;
USE GrumpyWorld;

-- Tablas
CREATE TABLE Usuarios(
  ID INT NOT NULL AUTO_INCREMENT,
  Usuario NVARCHAR(15) NOT NULL UNIQUE,
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
    Pactos TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY(ID)
);

CREATE TABLE Rollos(
  ID_Usuario INT,
  ID_Atributos INT NOT NULL,
  ID_Zona INT NOT NULL,
  Nivel INT NOT NULL DEFAULT 1,
  Honor INT NOT NULL DEFAULT 0,
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
  Tipo CHAR(1) NOT NULL,
  Bonus INT NOT NULL,
  DestrezaNecesaria INT NOT NULL,
  NivelNecesario INT NOT NULL,
  PRIMARY KEY(ID),
  CHECK (Tipo='S' OR Tipo='A')
);

CREATE TABLE Rollos_Equipables(
  ID_Rollo INT NOT NULL,
  ID_Equipable INT NOT NULL,
  Equipada BIT NOT NULL DEFAULT 0,
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
  Nombre NVARCHAR(15) NOT NULL UNIQUE,
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

-- Comprueba si existe un usuario
CREATE FUNCTION existeUsuario(nombre_usuario NVARCHAR(15))
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

-- Comprueba si un rollo es más rápido que su enemigo
CREATE FUNCTION enemigoMasRapido(destrezaRollo INT, destrezaEnemigo INT)
RETURNS BIT
BEGIN
	RETURN destrezaEnemigo>destrezaRollo;
END $$

-- Calcula el rango de un rollo
CREATE FUNCTION rangoRollo(honorRollo INT, idRollo INT)
RETURNS INT
BEGIN
	SET @rango = (SELECT COUNT(*) FROM Rollos WHERE Honor>honorRollo OR (Honor=honorRollo AND ID_Usuario<idRollo) );
    SET @rango = @rango+1;
	RETURN @rango;
END $$

-- Devuelve el ID de un enemigo aleatorio para un rollo según su zona y los enemigos que ha vencido en esa zona
CREATE FUNCTION enemigoAleatorio(idRollo INT)
RETURNS INT
BEGIN
	SET @enemigosVencidos = (
								SELECT COUNT(*)
								FROM Rollos AS R
								  INNER JOIN Vencidos AS V
									ON R.ID_Usuario = V.ID_Rollo
								  INNER JOIN Enemigos AS E
									ON V.ID_Enemigo = E.ID
								WHERE R.ID_Usuario = idRollo AND
								  R.ID_Zona = E.ID_Zona
							);
    IF(@enemigosVencidos >= 4) THEN
	BEGIN
		SET @idEnemigo = (SELECT E.ID
		FROM Rollos AS R
		INNER JOIN Zonas AS Z
		ON R.ID_Zona = Z.ID
		INNER JOIN Enemigos AS E
		ON Z.ID = E.ID_Zona
        WHERE R.ID_Usuario = idRollo
		ORDER BY RAND()
		LIMIT 1);
    END;
    ELSE
		SET @idEnemigo = (SELECT E.ID
		FROM Rollos AS R
		INNER JOIN Zonas AS Z
		ON R.ID_Zona = Z.ID
		INNER JOIN Enemigos AS E
		ON Z.ID = E.ID_Zona
        WHERE R.ID_Usuario = idRollo AND
        E.EsJefe = 0
		ORDER BY RAND()
		LIMIT 1);
    BEGIN
    END;
    END IF;
	RETURN @idEnemigo;
END $$

-- Genera un ataque aleatorio (entero del 1 al 3 incluidos)
CREATE FUNCTION ataqueAleatorio()
RETURNS INT
BEGIN
	RETURN FLOOR(1 + (RAND() * 3));
END $$

-- Calcula el daño base parcialmente aleatorio de un personaje a otro, teniendo en cuenta su fuerza, atributos y pactos
CREATE FUNCTION danoBase(idAtributosAtacante INT, idAtributosVictima INT)
  RETURNS INT
  BEGIN
	-- Atributos del atacante
    SELECT Fuerza*(1+Pactos/10), Constitucion*(1+Pactos/10) INTO @fAtacante, @cAtacante FROM Atributos WHERE ID = idAtributosAtacante;
    
    -- Atributos de la víctima
    SELECT Fuerza*(1+Pactos/10), Constitucion*(1+Pactos/10) INTO @fVictima, @cVictima FROM Atributos WHERE ID = idAtributosVictima;
	
    -- Bonus de equipables del atacante
	SELECT
	  IFNULL(
		  (
			SELECT e.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipada AND E.Tipo = 'S' AND A.ID = A2.ID
		  )
		  , 0),
	  IFNULL(
		  (
			SELECT e.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipada AND E.Tipo = 'A' AND A.ID = A2.ID
		  )
		  , 0)
	INTO @bonusSombreroAtacante, @bonusArmaAtacante
	FROM Atributos AS A2
	WHERE A2.ID = idAtributosAtacante;
		  
      -- Bonus de equipables de la víctima
	 SELECT
	  IFNULL(
		  (
			SELECT e.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipada AND E.Tipo = 'S' AND A.ID = A2.ID
		  )
		  , 0),
	  IFNULL(
		  (
			SELECT e.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipada AND E.Tipo = 'A' AND A.ID = A2.ID
		  )
		  , 0)
	INTO @bonusSombreroVictima, @bonusArmaVictima
	FROM Atributos AS A2
	WHERE A2.ID = idAtributosVictima;
    
    -- Fórmula: Daño = 10*(FuerzaAtacante + BonusArmaAtacante) / (ConstituciónVíctima + BonusSombreroVíctima)
    -- Hasta un 15% de este daño se puede reducir de forma aleatoria
    SET @dano = 10*(0.85*((@fAtacante+@bonusArmaAtacante)/(@cVictima+@bonusSombreroVictima))) + (RAND()*0.15*((@fAtacante+@bonusArmaAtacante)/(@cVictima+@bonusSombreroVictima)));
    RETURN @dano;
  END $$

-- Calcula el daño de un ataque de un personaje a otro
CREATE FUNCTION dano(idAtributosAtacante INT, idAtributosVictima INT, ataqueAtacante INT, ataqueVictima INT)
RETURNS INT
BEGIN
	IF(ataqueAtacante=1) THEN
    BEGIN
		SET @dano = danoBase(idAtributosAtacante, idAtributosVictima);
    END;
    ELSE
    BEGIN
		IF(ataqueAtacante=2) THEN
        BEGIN
			IF(ataqueVictima != 3) THEN
            BEGIN
				SET @dano = 2*danoBase(idAtributosAtacante, idAtributosVictima);
            END;
			ELSE
            BEGIN
				SET @dano = 0;
            END;
            END IF;
        END;
        ELSE
		BEGIN
			-- Por eliminación, el ataque del atacante es 3
            IF(ataqueVictima = 2) THEN
            BEGIN
				/*Si el ataque de la víctima rebota,
                el daño recibido por la víctima va en función de su poder de ataque contra su propio poder de defensa,
                por eso ambos atributos son el ID de la víctima*/
				SET @dano = 2*danoBase(idAtributosVictima, idAtributosVictima);
            END;
            ELSE
            BEGIN
				SET @dano = 0;
            END;
            END IF;
        END;
        END IF;
    END;
    END IF;
    RETURN @dano;
END $$

-- Procedimientos

-- Crea un usuario insertando en todas las tablas necesarias
CREATE PROCEDURE crearUsuario(IN usuario NVARCHAR(15), IN contrasena NVARCHAR(255), OUT conseguido BIT)
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

-- Aumenta un atributo y le asigna el tiempo en el que acabará el entrenamiento
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

-- Crea un enemigo
CREATE PROCEDURE crearEnemigo(IN nNombre NVARCHAR(15), IN nFuerza INT, IN nConstitucion INT, IN nDestreza INT, IN nEsJefe BIT, IN nNombreZona NVARCHAR(30))
BEGIN
	INSERT INTO Atributos (Fuerza, Constitucion, Destreza) VALUE (nFuerza, nConstitucion, nDestreza);
    INSERT INTO Enemigos (Nombre, ID_Atributos, ID_Zona, Esjefe)
		SELECT nNombre, LAST_INSERT_ID(), ID, nEsJefe FROM Zonas WHERE Nombre = nNombreZona;
END $$

-- Asigna una caza aleatoria a un rollo según su zona y los enemigos vencidos
CREATE PROCEDURE asignarCaza(IN idRollo INT)
BEGIN
	INSERT INTO Caza (ID_Rollo, ID_Enemigo) VALUE (idRollo, enemigoAleatorio(idRollo));
END $$

CREATE PROCEDURE jugarTurnoCaza(IN idRollo INT, IN ataqueRollo INT)
BEGIN
	SET @ataqueEnemigo = ataqueAleatorio();
	SELECT VidaRollo, VidaEnemigo, enemigoMasRapido(AR.Destreza, AE.Destreza) AS enemigoMasRapido, AR.ID AS idAtributosRollo, AE.ID AS idAtributosEnemigo
	  INTO @vidaRollo, @vidaEnemigo, @enemigoMasRapido, @idAtributosRollo, @idAtributosEnemigo
	  FROM Caza AS C
	  INNER JOIN Rollos AS R ON C.ID_Rollo = R.ID_Usuario
	  INNER JOIN Atributos AS AR ON R.ID_Atributos = AR.ID
	  INNER JOIN Enemigos AS E ON C.ID_Enemigo = E.ID
	  INNER JOIN Atributos AS AE ON E.ID_Atributos = AE.ID
	WHERE C.ID_Rollo = idRollo;
    IF(@vidaRollo>0 AND @vidaEnemigo>0) THEN
    BEGIN
		IF(@enemigoMasRapido) THEN
        BEGIN
			SET @vidaRollo = @vidaRollo - dano(@idAtributosEnemigo, @idAtributosRollo, @ataqueEnemigo, ataqueRollo);
            IF(@vidaRollo>0) THEN
            BEGIN
				SET @vidaEnemigo = @vidaEnemigo - dano(@idAtributosRollo, @idAtributosEnemigo, ataqueRollo, @ataqueEnemigo);
            END;
            END IF;
        END;
        ELSE
        BEGIN
			SET @vidaEnemigo = @vidaEnemigo - dano(@idAtributosRollo, @idAtributosEnemigo, ataqueRollo, @ataqueEnemigo);
            IF(@vidaEnemigo>0)THEN
            BEGIN
				SET @vidaRollo = @vidaRollo - dano(@idAtributosEnemigo, @idAtributosRollo, @ataqueEnemigo, ataqueRollo);
            END;
            END IF;
        END;
        END IF;
        
        IF(@vidaRollo<0) THEN
		BEGIN
			SET @vidaRollo = 0;
		END;
		END IF;
        
		IF(@vidaEnemigo<0) THEN
			BEGIN
				SET @vidaEnemigo = 0;
		END;
		END IF;
            
		UPDATE Caza SET VidaRollo = @vidaRollo, VidaEnemigo = @vidaEnemigo, AtaqueRollo = ataqueRollo, AtaqueEnemigo = @ataqueEnemigo WHERE ID_Rollo = idRollo;
    END;
    END IF;
END $$

CREATE PROCEDURE borrarCaza(IN idRollo INT)
BEGIN
	DELETE FROM Caza WHERE ID_Rollo = idRollo;
END$$

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

-- Enemigos
CALL crearEnemigo('stripper', 10, 20, 30, FALSE, 'bano');
CALL crearEnemigo('cepillo', 10, 30, 20, FALSE, 'bano');
CALL crearEnemigo('cuchilla', 30, 10, 20, FALSE, 'bano');
CALL crearEnemigo('champu', 20, 10, 30, FALSE, 'bano');
CALL crearEnemigo('vater', 30, 50, 20, TRUE, 'bano');

CALL crearEnemigo('leche', 60, 80, 70, FALSE, 'cocina');
CALL crearEnemigo('zanahoria', 80, 60, 70, FALSE, 'cocina');
CALL crearEnemigo('cuchara', 70, 80, 60, FALSE, 'cocina');
CALL crearEnemigo('limon', 70, 60, 80, FALSE, 'cocina');
CALL crearEnemigo('calabaza', 80, 100, 70, TRUE, 'cocina');

CALL crearEnemigo('raton', 120, 110, 130, FALSE, 'oficina');
CALL crearEnemigo('grapadora', 130, 120, 110, FALSE, 'oficina');
CALL crearEnemigo('lapiz', 130, 110, 120, FALSE, 'oficina');
CALL crearEnemigo('libro', 110, 130, 120, FALSE, 'oficina');
CALL crearEnemigo('pendrive', 110, 150, 120, TRUE, 'oficina');

-- Pruebas
CALL crearUsuario('dani', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
-- UPDATE Atributos SET Fuerza = 200, Constitucion = 1, Destreza = 200 WHERE ID = 16;
/*INSERT INTO Equipables (Nombre, Tipo, Bonus, DestrezaNecesaria, NivelNecesario) VALUE ('armaPrueba', 'A', 200, 0, 0);
INSERT INTO Rollos_Equipables (ID_Rollo, ID_Equipable, Equipada) VALUE (1, 1, TRUE);
INSERT INTO Equipables (Nombre, Tipo, Bonus, DestrezaNecesaria, NivelNecesario) VALUE ('sombreroPrueba', 'S', 300, 0, 0);
INSERT INTO Rollos_Equipables (ID_Rollo, ID_Equipable, Equipada) VALUE (1, 2, TRUE);
CALL asignarCaza(1);*/