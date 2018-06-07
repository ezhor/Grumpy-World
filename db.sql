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
  CONSTRAINT FK_Rollos_Atributos FOREIGN KEY (ID_Atributos) REFERENCES Atributos(ID) ON DELETE NO ACTION,
  PRIMARY KEY(ID_Usuario)
);

CREATE TABLE Duelos(
	ID_Rollo INT NOT NULL,
    ID_Oponente INT NOT NULL,
    Turno TINYINT NOT NULL,
    Vida TINYINT NULL,
    Ataque TINYINT NULL,
    Momento INT NULL,
    CONSTRAINT FK_Duelos_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Duelos_RolloEnemigo FOREIGN KEY (ID_Oponente) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    PRIMARY KEY(ID_Rollo, ID_Oponente, Turno)
);

CREATE TABLE Ultimos_Duelos(
	ID_Rollo INT NOT NULL,
    ID_Oponente INT NOT NULL,
    Momento INT NULL,
    CONSTRAINT FK_Ultimos_Duelos_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    CONSTRAINT FK_Ultimos_Duelos_RolloEnemigo FOREIGN KEY (ID_Oponente) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
    PRIMARY KEY(ID_Rollo, ID_Oponente)
);

CREATE TABLE Equipables(
  ID INT NOT NULL AUTO_INCREMENT,
  Nombre NVARCHAR(30) NOT NULL UNIQUE,
  Tipo CHAR(1) NOT NULL,
  Bonus INT NOT NULL,
  DestrezaNecesaria INT NOT NULL,
  FuerzaNecesaria INT NOT NULL,
  NivelNecesario INT NOT NULL,
  PRIMARY KEY(ID),
  CHECK (Tipo='S' OR Tipo='A')
);

CREATE TABLE Rollos_Equipables(
  ID_Rollo INT NOT NULL,
  ID_Equipable INT NOT NULL,
  Equipado BIT NOT NULL DEFAULT 0,
  CONSTRAINT FK_Rollos_Equipables_ID_Rollo FOREIGN KEY (ID_Rollo) REFERENCES Rollos(ID_Usuario) ON DELETE CASCADE,
  CONSTRAINT FK_Rollos_Equipables_ID_Equipable FOREIGN KEY (ID_Equipable) REFERENCES Equipables(ID) ON DELETE CASCADE,
  PRIMARY KEY(ID_Rollo, ID_Equipable)
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

CREATE TABLE Enemigos_Materiales(
	ID_Enemigo INT NOT NULL,
    ID_Material INT NOT NULL,
    Cantidad INT NOT NULL,
    Probabilidad TINYINT NOT NULL,
    CONSTRAINT FK_Enemigos_Materiales_ID_Enemigo FOREIGN KEY (ID_Enemigo) REFERENCES Enemigos(ID) ON DELETE CASCADE,
	CONSTRAINT FK_Enemigos_Materiales_ID_Material FOREIGN KEY (ID_Material) REFERENCES Materiales(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID_Enemigo, ID_Material)
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

/*
Calcula el daño base parcialmente aleatorio de un personaje a otro, teniendo en cuenta su fuerza, atributos y pactos
	Fórmula base: Daño = 10*(FuerzaAtacante + BonusArmaAtacante) / (ConstituciónVíctima + BonusSombreroVíctima)
	Todos los atributos de un personaje se multiplica por su número de pactos divididos entre 10
	Hasta un 15% de este daño se puede reducir de forma aleatoria
*/
CREATE FUNCTION danoBase(idAtributosAtacante INT, idAtributosVictima INT)
  RETURNS INT
  BEGIN
	-- Atributos del atacante
    SELECT Fuerza*(1+(Pactos/10)), Constitucion*(1+(Pactos/10)) INTO @fAtacante, @cAtacante FROM Atributos WHERE ID = idAtributosAtacante;
    
    -- Atributos de la víctima
    SELECT Fuerza*(1+(Pactos/10)), Constitucion*(1+(Pactos/10)) INTO @fVictima, @cVictima FROM Atributos WHERE ID = idAtributosVictima;
	
    -- Bonus de equipables del atacante
	SELECT
	  IFNULL(
		  (
			SELECT E.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipado AND E.Tipo = 'S' AND A.ID = A2.ID
		  )
		  , 0),
	  IFNULL(
		  (
			SELECT E.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipado AND E.Tipo = 'A' AND A.ID = A2.ID
		  )
		  , 0)
	INTO @bonusSombreroAtacante, @bonusArmaAtacante
	FROM Atributos AS A2
	WHERE A2.ID = idAtributosAtacante;
		  
      -- Bonus de equipables de la víctima
	 SELECT
	  IFNULL(
		  (
			SELECT E.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipado AND E.Tipo = 'S' AND A.ID = A2.ID
		  )
		  , 0),
	  IFNULL(
		  (
			SELECT E.Bonus
			FROM Atributos AS A
			  LEFT JOIN Rollos AS R ON A.ID = R.ID_Atributos
			  LEFT JOIN Rollos_Equipables AS RE ON R.ID_Usuario = RE.ID_Rollo
			  LEFT JOIN Equipables AS E ON RE.ID_Equipable = E.ID
			WHERE RE.Equipado AND E.Tipo = 'A' AND A.ID = A2.ID
		  )
		  , 0)
	INTO @bonusSombreroVictima, @bonusArmaVictima
	FROM Atributos AS A2
	WHERE A2.ID = idAtributosVictima;
    
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
                por eso ambos atributos son del ID de la víctima*/
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

-- Comprueba si dos usuarios han jugado un duelo hace menos de un día.
CREATE FUNCTION dueloHaceMenosDeUnDia(idRollo INT, idOponente INT)
RETURNS BIT
BEGIN
	RETURN EXISTS(SELECT 1
					FROM Ultimos_Duelos
					WHERE ID_Rollo = idRollo AND ID_Oponente = idOponente AND
						Momento > UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 1 day)));
END $$

-- Comprueba si el otro jugador ya ha elegido turno una vez el primero ya lo ha elegido (Y por tanto ya ha calculado el resultado)
-- Esta función sería la contraposición de oponenteHaElegidoEsteTurno
CREATE FUNCTION oponenteHaElegidoEsteTurno(idRollo INT)
RETURNS BIT
BEGIN
	SELECT ID_Oponente, Turno
		INTO @idOponente, @turno
		FROM Duelos
		WHERE ID_Rollo = idRollo
        ORDER BY Turno DESC
        LIMIT 1;
        
	RETURN EXISTS(SELECT 1
					FROM Duelos
					WHERE ID_Rollo = @idOponente AND ID_Oponente = idRollo AND Turno = @turno);
END $$

-- Comprueba si el otro jugador ya ha elegido turno antes que el primero (Y por tanto está esperando a que sea calculado)
-- Esta función sería la contraposición de oponenteHaElegidoEsteTurno
CREATE FUNCTION oponenteHaElegidoSiguienteTurno(idRollo INT)
RETURNS BIT
BEGIN
	SELECT ID_Oponente, Turno
		INTO @idOponente, @turno
		FROM Duelos
		WHERE ID_Rollo = idRollo
        ORDER BY Turno DESC
        LIMIT 1;
        
	RETURN EXISTS(SELECT 1
					FROM Duelos
					WHERE ID_Rollo = @idOponente AND ID_Oponente = idRollo AND Turno = (@turno+1));
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

-- Genera un ataque aleatorio de un enemigo y calcula el resultado del turno
CREATE PROCEDURE jugarTurnoCaza(IN idRollo INT, IN ataqueRollo INT)
BEGIN
	SET @ataqueEnemigo = ataqueAleatorio();
	SELECT VidaRollo, VidaEnemigo, enemigoMasRapido(AR.Destreza, AE.Destreza) AS enemigoMasRapido, AR.ID AS idAtributosRollo, AE.ID AS idAtributosEnemigo, E.ID
	  INTO @vidaRollo, @vidaEnemigo, @enemigoMasRapido, @idAtributosRollo, @idAtributosEnemigo, @idEnemigo
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
        
        IF(@vidaRollo>0 AND @vidaEnemigo = 0) THEN
        BEGIN
			CALL marcarVictoria(idRollo, @idEnemigo);
            CALL revisarNivel(idRollo);
        END;
        END IF;
        
		UPDATE Caza SET VidaRollo = @vidaRollo, VidaEnemigo = @vidaEnemigo, AtaqueRollo = ataqueRollo, AtaqueEnemigo = @ataqueEnemigo WHERE ID_Rollo = idRollo;        
    END;
    END IF;
END $$

-- Borra la caza activa del rollo
CREATE PROCEDURE borrarCaza(IN idRollo INT)
BEGIN
	DELETE FROM Caza WHERE ID_Rollo = idRollo;
END$$

-- Mueve un rollo de zona
CREATE PROCEDURE cambiarZona(IN idRollo INT, IN nombreZona NVARCHAR(15))
BEGIN
	CALL borrarCaza(idRollo);
	UPDATE Rollos AS R
		SET ID_Zona = (SELECT ID FROM Zonas WHERE Nombre = nombreZona)
		WHERE R.ID_Usuario = idRollo;
END $$

-- Marca un enemigo como vencido por un rollo (siempre que no haya sido previamente marcado)
CREATE PROCEDURE marcarVictoria(IN idRollo INT, IN idEnemigo INT)
BEGIN
	IF((SELECT COUNT(*) FROM Vencidos WHERE ID_Rollo = idRollo AND ID_Enemigo = idEnemigo) = 0) THEN
    BEGIN
		INSERT INTO Vencidos VALUE (idRollo, idEnemigo);
    END;
    END IF;
END $$

-- Sube de nivel un rollo si ya ha vencido al jefe de su zona
CREATE PROCEDURE revisarNivel(IN idRollo INT)
BEGIN
	SET @nivel = (SELECT COUNT(*)+1
			 FROM Rollos AS R
			   INNER JOIN Vencidos AS V
				 ON R.ID_Usuario = V.ID_Rollo
			   INNER JOIN Enemigos AS E
				 ON V.ID_Enemigo = E.ID
			 WHERE R.ID_Usuario = idRollo AND E.EsJefe);
	
    UPDATE Rollos AS R SET Nivel = @nivel
		WHERE R.ID_Usuario = idRollo;
END $$

-- Asocia un material a un enemigo de forma que pueda darlo como premio al vencerle
CREATE PROCEDURE asociarEnemigoMaterial(IN nombreEnemigo VARCHAR(15), IN nombreMaterial VARCHAR(30), IN cantidad INT, IN probabilidad INT)
BEGIN
	SET @idEnemigo = (SELECT ID FROM Enemigos WHERE Nombre = nombreEnemigo);
    SET @idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    INSERT INTO Enemigos_Materiales (ID_Enemigo, ID_Material, Cantidad, Probabilidad) VALUE (@idEnemigo, @idMaterial, cantidad, probabilidad);
END $$

-- Concede una cantidad de material a un rollo
CREATE PROCEDURE concederPremio(IN idRollo INT, IN nombreMaterial VARCHAR(30), IN cantidadAnadida INT)
BEGIN
	SET @idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
	IF EXISTS (SELECT * FROM Rollos_Materiales WHERE ID_Rollo = idRollo AND ID_Material = @idMaterial) THEN
    BEGIN
		START TRANSACTION;
			SET @cantidadOriginal = (SELECT Cantidad FROM Rollos_Materiales  WHERE ID_Rollo = idRollo AND ID_Material = @idMaterial);
            UPDATE Rollos_Materiales SET Cantidad = (@cantidadOriginal + cantidadAnadida) WHERE ID_Rollo = idRollo AND ID_Material = @idMaterial;
        COMMIT;
    END;
    ELSE
    BEGIN
		INSERT INTO Rollos_Materiales(ID_Rollo, ID_Material, Cantidad) VALUE (idRollo, @idMaterial, cantidadAnadida);
    END;
    END IF;
END $$

-- Asocia una cantidad de material a un equipable de forma que sea necesaria dicha cantidad para fabricarlo
CREATE PROCEDURE asociarEquipableMaterial(IN nombreEquipable VARCHAR(30), IN nombreMaterial VARCHAR(30), IN cantidad INT)
BEGIN
	SET @idEquipable = (SELECT ID FROM Equipables WHERE Nombre = nombreEquipable);
    SET @idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    INSERT INTO Equipables_Materiales (ID_Equipable, ID_Material, Cantidad) VALUE (@idEquipable, @idMaterial, cantidad);
END $$

-- Asocia una cantidad de un submaterial a un material compuesto de forma que sea necesaria dicha cantidad para fabricarlo
CREATE PROCEDURE asociarMaterialSubmaterial(IN nombreMaterial VARCHAR(30), IN nombreSubmaterial VARCHAR(30), IN cantidad INT)
BEGIN
	SET @idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    SET @idSubmaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreSubmaterial);
    INSERT INTO Materiales_Materiales (ID_Material, ID_Submaterial, Cantidad) VALUE (@idMaterial, @idSubmaterial, cantidad);
END $$

-- Gasta una cantidad de material en de un rollo
CREATE PROCEDURE gastarMaterial(IN idRollo INT, IN idMaterial INT, IN cantidadGastada INT)
BEGIN
	START TRANSACTION;
		SET @cantidadOriginal = (SELECT Cantidad FROM Rollos_Materiales  WHERE ID_Rollo = idRollo AND ID_Material = idMaterial);
		UPDATE Rollos_Materiales SET Cantidad = (@cantidadOriginal - cantidadGastada) WHERE ID_Rollo = idRollo AND ID_Material = idMaterial;
	COMMIT;
END $$

-- Añade un equipable a un rollo de forma que pueda equiparlo más tarde
CREATE PROCEDURE anadirEquipable(IN idRollo INT, IN idEquipable INT)
BEGIN
	INSERT INTO Rollos_Equipables (ID_Rollo, ID_Equipable, Equipado) VALUES (idRollo, idEquipable, 0);
END $$

-- Añade una unidad de un material compuesto al ser fabricado
CREATE PROCEDURE anadirSupermaterial(IN idRollo INT, IN idMaterial INT)
BEGIN
	SET @nombreMaterial = (SELECT Nombre FROM Materiales WHERE ID = idMaterial);
    CALL concederPremio(idRollo, @nombreMaterial, 1);
END $$

-- Equipa un arma o sombrero para que pueda ser usado durante la caza o el duelo
CREATE PROCEDURE equipar(IN idRollo INT, IN idEquipable INT)
BEGIN
	START TRANSACTION;
		SET @tipoEquipable = (SELECT Tipo FROM Equipables WHERE ID = idEquipable);
		UPDATE Rollos_Equipables AS RE INNER JOIN Equipables AS E ON RE.ID_Equipable = E.ID SET Equipado = 0 WHERE RE.ID_Rollo = idRollo AND E.Tipo = @tipoEquipable;
        UPDATE Rollos_Equipables SET Equipado = 1 WHERE ID_Rollo = idRollo AND ID_Equipable = idEquipable;
	COMMIT;
END $$

-- Agrega a un amigo unidireccionalmente. Más tarde, el usuario amigo tendrá que agregar al primer usuario para aceptar su solicitud.
CREATE PROCEDURE agregarAmigo(IN idUsuario INT, IN idAmigo INT)
BEGIN
	INSERT INTO Amigos (ID_Usuario1, ID_Usuario2) VALUES (idUsuario, idAmigo);
END $$

-- Borra a un amigo. También puede usarse para rechazar una petición de amistad.
CREATE PROCEDURE borrarAmigo(IN idUsuario INT, IN idAmigo INT)
BEGIN
	DELETE FROM Amigos WHERE (ID_Usuario1 = idUsuario AND ID_Usuario2 = idAmigo) OR (ID_Usuario1 = idAmigo AND ID_Usuario2 = idUsuario);
END $$

-- Reta a un amigo a un duelo. Más tarde, el usuario amigo tendrá que retar al primer usuario para aceptar su reto.
CREATE PROCEDURE retarADuelo(IN idRollo INT, IN idOponente INT)
BEGIN
	INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, idOponente, 0, 100, NULL, NULL);
END $$

-- Rechaza un reto a duelo. También puede usarse para cancelar un reto a un amigo.
CREATE PROCEDURE rechazarDuelo(IN idRollo INT, IN idOponente INT)
BEGIN
	DELETE FROM Duelos WHERE ((ID_Rollo = idRollo AND ID_Oponente = idOponente) OR (ID_Oponente = idRollo AND ID_Rollo = idOponente)) AND Turno = 0;
END $$

-- Cacula el resultado del turno de duelo dado el ID de un jugador y su ataque.
-- El jugador debe estar jugando un duelo con otro jugador. Dicho jugador debe haber elegido ya su turno.
-- En caso de que no lo haya elegido, se le asignará uno aleatorio.
CREATE PROCEDURE jugarTurnoDuelo(IN idRollo INT, IN ataqueRollo INT)
BEGIN
	SELECT D1.Vida, enemigoMasRapido(A1.Destreza, A2.Destreza), A1.ID, A2.ID, D1.ID_Oponente, D1.Turno
		INTO @vidaRollo, @enemigoMasRapido, @idAtributosRollo, @idAtributosEnemigo, @idEnemigo, @turno
		FROM Duelos AS D1
		  INNER JOIN Rollos AS R1
			ON D1.ID_Rollo = R1.ID_Usuario
		  INNER JOIN Atributos AS A1
			ON R1.ID_Atributos = A1.ID
		  INNER JOIN Rollos AS R2
			ON D1.ID_Oponente = R2.ID_Usuario
		  INNER JOIN Atributos AS A2
			ON R2.ID_Atributos = A2.ID
		WHERE D1.ID_Rollo = idRollo
		ORDER BY D1.Turno DESC
		LIMIT 1;
        
	SET @ataqueEnemigo = (SELECT Ataque FROM Duelos WHERE ID_Oponente = idRollo ORDER BY Turno DESC LIMIT 1);
    SET @vidaEnemigo = (SELECT Vida FROM Duelos WHERE ID_Oponente = idRollo AND Turno = @turno LIMIT 1);
	    
	SET @habiaElegidoAtaque = TRUE;
	IF(@ataqueEnemigo IS NULL) THEN
	BEGIN
		SET @habiaElegidoAtaque = FALSE;
        SET @ataqueEnemigo = ataqueAleatorio();
    END;
    END IF;
	
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
	
	IF(@vidaRollo>0 AND @vidaEnemigo = 0) THEN
	BEGIN
		-- IF vacío para posible condición de victoria
		-- CALL marcarVictoria(idRollo, @idEnemigo);
		-- CALL revisarNivel(idRollo);
	END;
	END IF;
	
    SET @momento = UNIX_TIMESTAMP();
    
	IF(@habiaElegidoAtaque) THEN
	BEGIN
		INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, @idEnemigo, (@turno+1), @vidaRollo, ataqueRollo, @momento);
		UPDATE Duelos SET Vida = @vidaEnemigo, Momento = @momento WHERE ID_Rollo = @idEnemigo AND ID_Oponente = idRollo AND Turno = (@turno+1);
	END;
	ELSE
	BEGIN
		INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUES
				(idRollo, @idEnemigo, (@turno+1), @vidaRollo, ataqueRollo, @momento),
				(@idEnemigo, idRollo, (@turno+1), @vidaEnemigo, @ataqueEnemigo, @momento);
	END;
	END IF;
END $$

CREATE PROCEDURE elegirTurnoDuelo(IN idRollo INT, IN ataqueRollo INT)
BEGIN
	SELECT ID_Oponente, Turno
    INTO @idOponente, @turno
    FROM Duelos WHERE ID_Rollo = idRollo
    ORDER BY Turno DESC
    LIMIT 1;
	INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, @idOponente, (@turno+1), NULL, ataqueRollo, NULL);
END $$

DELIMITER ;
-- Datos iniciales

-- Zonas
INSERT INTO Zonas (Nombre, Nivel) VALUES
	('bano', 1),
	('cocina', 2),
    ('oficina', 3);
    -- ('parque', 4),
    -- ('cementerio', 5),
    -- ('infierno', 6);

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

-- Materiales
INSERT INTO Materiales (Nombre) VALUES ('buen_rollo'),
	('plastico'),
	('madera'),
	('trozo_calabaza'),
	('zumo_limon'),
	('grapa'),
	('zinc'),
	('cable'),
	('cobre'),
	('bateria'),
	('papel');

-- Enemigos_Materiales
CALL asociarEnemigoMaterial('stripper', 'buen_rollo', 1, 100);
CALL asociarEnemigoMaterial('cepillo', 'buen_rollo', 1, 100);
CALL asociarEnemigoMaterial('cuchilla', 'buen_rollo', 1, 100);
CALL asociarEnemigoMaterial('champu', 'buen_rollo', 1, 100);
CALL asociarEnemigoMaterial('vater', 'buen_rollo', 1, 100);

CALL asociarEnemigoMaterial('leche', 'buen_rollo', 2, 100);
CALL asociarEnemigoMaterial('zanahoria', 'buen_rollo', 2, 100);
CALL asociarEnemigoMaterial('cuchara', 'buen_rollo', 2, 100);
CALL asociarEnemigoMaterial('limon', 'buen_rollo', 2, 100);
CALL asociarEnemigoMaterial('calabaza', 'buen_rollo', 2, 100);

CALL asociarEnemigoMaterial('raton', 'buen_rollo', 3, 100);
CALL asociarEnemigoMaterial('grapadora', 'buen_rollo', 3, 100);
CALL asociarEnemigoMaterial('lapiz', 'buen_rollo', 3, 100);
CALL asociarEnemigoMaterial('libro', 'buen_rollo', 3, 100);
CALL asociarEnemigoMaterial('pendrive', 'buen_rollo', 3, 100);

CALL asociarEnemigoMaterial('cepillo', 'plastico', 1, 50);
CALL asociarEnemigoMaterial('champu', 'plastico', 2, 50);

CALL asociarEnemigoMaterial('cuchara', 'madera', 1, 50);
CALL asociarEnemigoMaterial('calabaza', 'trozo_calabaza', 1, 50);
CALL asociarEnemigoMaterial('limon', 'zumo_limon', 1, 25);

CALL asociarEnemigoMaterial('raton', 'cable', 1, 25);
CALL asociarEnemigoMaterial('grapadora', 'grapa', 10, 25);
CALL asociarEnemigoMaterial('libro', 'papel', 10, 25);

-- Equipables
INSERT INTO Equipables(Nombre, Tipo, Bonus , DestrezaNecesaria, FuerzaNecesaria, NivelNecesario) VALUES
	('tenedor', 'A', 2, 20, 0, 1),
    ('casco_obra', 'S', 2, 0, 20, 1),
    ('mazo_juez', 'A', 4, 40, 0, 2),
    ('casco_calabaza', 'S', 4, 0, 40, 2),
    ('pistola_airsoft', 'A', 8, 80, 0, 3),
    ('sombrero_papel', 'S', 8, 0, 80, 3);
    
-- Equipables_Materiales
CALL asociarEquipableMaterial('tenedor', 'buen_rollo', 100);
CALL asociarEquipableMaterial('tenedor', 'plastico', 10);

CALL asociarEquipableMaterial('casco_obra', 'buen_rollo', 100);
CALL asociarEquipableMaterial('casco_obra', 'plastico', 30);

CALL asociarEquipableMaterial('mazo_juez', 'buen_rollo', 300);
CALL asociarEquipableMaterial('mazo_juez', 'madera', 20);

CALL asociarEquipableMaterial('casco_calabaza', 'buen_rollo', 300);
CALL asociarEquipableMaterial('casco_calabaza', 'trozo_calabaza', 1);

CALL asociarEquipableMaterial('pistola_airsoft', 'buen_rollo', 600);
CALL asociarEquipableMaterial('pistola_airsoft', 'plastico', 40);
CALL asociarEquipableMaterial('pistola_airsoft', 'bateria', 1);

CALL asociarEquipableMaterial('sombrero_papel', 'buen_rollo', 600);
CALL asociarEquipableMaterial('sombrero_papel', 'papel', 10);

-- Materiales_Materiales

CALL asociarMaterialSubmaterial('bateria', 'zinc', 10);
CALL asociarMaterialSubmaterial('bateria', 'cobre', 10);
CALL asociarMaterialSubmaterial('bateria', 'zumo_limon', 10);

CALL asociarMaterialSubmaterial('zinc', 'grapa', 10);

CALL asociarMaterialSubmaterial('cobre', 'cable', 1);

-- Pruebas
CALL crearUsuario('dani', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani2', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani3', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani4', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani5', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani6', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani7', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani8', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani9', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani10', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani11', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('dani12', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);
CALL crearUsuario('supremmaer', '$2y$10$8hnEpmUyg8WKrAU9U.tV.e75hFxq9SZRbRc8gmFTU5RThuWDF9Luy', @conseguido);

UPDATE Rollos SET Honor = 1000 WHERE ID_Usuario = 1;
UPDATE Rollos SET Honor = 800 WHERE ID_Usuario = 2;
UPDATE Rollos SET Honor = 600 WHERE ID_Usuario = 3;
UPDATE Rollos SET Honor = 550 WHERE ID_Usuario = 4;
UPDATE Rollos SET Honor = 200 WHERE ID_Usuario = 5;

UPDATE Atributos SET Fuerza = 100, Constitucion = 100, Destreza = 100 WHERE ID = 16;
UPDATE Atributos SET Fuerza = 50, Constitucion = 50, Destreza = 50 WHERE ID = 17;
UPDATE Rollos SET Nivel = 7 WHERE ID_Usuario = 1;
UPDATE Rollos SET Nivel = 7 WHERE ID_Usuario = 2;

CALL concederPremio(1, 'buen_rollo', 5000);
CALL concederPremio(1, 'plastico', 5000);
CALL concederPremio(1, 'zumo_limon', 5000);
CALL concederPremio(1, 'cable', 5000);
CALL concederPremio(1, 'zinc', 5000);
CALL concederPremio(1, 'trozo_calabaza', 5000);
CALL concederPremio(1, 'madera', 5000);
CALL concederPremio(1, 'papel', 5000);

CALL concederPremio(2, 'buen_rollo', 5000);
CALL concederPremio(2, 'plastico', 5000);
CALL concederPremio(2, 'zumo_limon', 5000);
CALL concederPremio(2, 'cable', 5000);
CALL concederPremio(2, 'zinc', 5000);
CALL concederPremio(2, 'trozo_calabaza', 5000);
CALL concederPremio(2, 'madera', 5000);
CALL concederPremio(2, 'papel', 5000);

INSERT INTO Amigos VALUES  (1,2), (2,1), (1,3), (4,1);

INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUES (1, 2, 0, 100, NULL, NULL), (2, 1, 0, 100, NULL, NULL), (2, 1, 1, NULL, 1, NULL);