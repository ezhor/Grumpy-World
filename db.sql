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
  ID_Usuario INT NOT NULL,
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
  READS SQL DATA
  BEGIN
    RETURN (SELECT COUNT(*) FROM Usuarios WHERE Usuario = nombre_usuario)>0;
  END $$

-- Comprueba si un rollo es más rápido que su enemigo
CREATE FUNCTION enemigoMasRapido(destrezaRollo INT, destrezaEnemigo INT)
  RETURNS BIT
  DETERMINISTIC
  BEGIN
    RETURN destrezaEnemigo>destrezaRollo;
  END $$

-- Calcula el rango de un rollo
CREATE FUNCTION rangoRollo(honorRollo INT, idRollo INT)
  RETURNS INT
  READS SQL DATA
  BEGIN
    DECLARE _rango INT;

    SET _rango = (SELECT COUNT(*) FROM Rollos WHERE Honor>honorRollo OR (Honor=honorRollo AND ID_Usuario<idRollo) );
    SET _rango = _rango+1;
    RETURN _rango;
  END $$

-- Devuelve el ID de un enemigo aleatorio para un rollo según su zona y los enemigos que ha vencido en esa zona
CREATE FUNCTION enemigoAleatorio(idRollo INT)
  RETURNS INT
  READS SQL DATA  
  BEGIN
    DECLARE _enemigosVencidos, _idEnemigo INT;

    SET _enemigosVencidos = (
      SELECT COUNT(*)
      FROM Rollos AS R
        INNER JOIN Vencidos AS V
          ON R.ID_Usuario = V.ID_Rollo
        INNER JOIN Enemigos AS E
          ON V.ID_Enemigo = E.ID
      WHERE R.ID_Usuario = idRollo AND
            R.ID_Zona = E.ID_Zona
    );
    IF(_enemigosVencidos >= 4) THEN
      BEGIN
        SET _idEnemigo = (SELECT E.ID
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
      SET _idEnemigo = (SELECT E.ID
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
    RETURN _idEnemigo;
  END $$

-- Genera un ataque aleatorio (entero del 1 al 3 incluidos)
CREATE FUNCTION ataqueAleatorio()
  RETURNS INT
  NO SQL
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
  READS SQL DATA
  BEGIN
    DECLARE _fAtacante, _cAtacante, _fVictima, _cVictima, _bonusSombreroAtacante, _bonusArmaAtacante, _bonusSombreroVictima, _bonusArmaVictima, _dano INT;

    -- Atributos del atacante
    SELECT Fuerza*(1+(Pactos/10)), Constitucion*(1+(Pactos/10)) INTO _fAtacante, _cAtacante FROM Atributos WHERE ID = idAtributosAtacante;

    -- Atributos de la víctima
    SELECT Fuerza*(1+(Pactos/10)), Constitucion*(1+(Pactos/10)) INTO _fVictima, _cVictima FROM Atributos WHERE ID = idAtributosVictima;

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
    INTO _bonusSombreroAtacante, _bonusArmaAtacante
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
    INTO _bonusSombreroVictima, _bonusArmaVictima
    FROM Atributos AS A2
    WHERE A2.ID = idAtributosVictima;

    SET _dano = 10*(0.85*((_fAtacante+_bonusArmaAtacante)/(_cVictima+_bonusSombreroVictima))) + (RAND()*0.15*((_fAtacante+_bonusArmaAtacante)/(_cVictima+_bonusSombreroVictima)));
    RETURN _dano;
  END $$

-- Calcula el daño de un ataque de un personaje a otro
CREATE FUNCTION dano(idAtributosAtacante INT, idAtributosVictima INT, ataqueAtacante INT, ataqueVictima INT)
  RETURNS INT
  DETERMINISTIC
  BEGIN
    DECLARE _dano INT;

    IF(ataqueAtacante=1) THEN
      BEGIN
        SET _dano = danoBase(idAtributosAtacante, idAtributosVictima);
      END;
    ELSE
      BEGIN
        IF(ataqueAtacante=2) THEN
          BEGIN
            IF(ataqueVictima != 3) THEN
              BEGIN
                SET _dano = 2*danoBase(idAtributosAtacante, idAtributosVictima);
              END;
            ELSE
              BEGIN
                SET _dano = 0;
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
                SET _dano = 2*danoBase(idAtributosVictima, idAtributosVictima);
              END;
            ELSE
              BEGIN
                SET _dano = 0;
              END;
            END IF;
          END;
        END IF;
      END;
    END IF;
    RETURN _dano;
  END $$

-- Comprueba si dos usuarios han jugado un duelo hace menos de un día.
CREATE FUNCTION dueloHaceMenosDeUnDia(idRollo INT, idOponente INT)
  RETURNS BIT
  READS SQL DATA
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
  READS SQL DATA
  BEGIN
    DECLARE _idOponente, _turno INT;

    SELECT ID_Oponente, Turno
    INTO _idOponente, _turno
    FROM Duelos
    WHERE ID_Rollo = idRollo
    ORDER BY Turno DESC
    LIMIT 1;

    RETURN EXISTS(SELECT 1
                  FROM Duelos
                  WHERE ID_Rollo = _idOponente AND ID_Oponente = idRollo AND Turno = _turno);
  END $$

-- Comprueba si el otro jugador ya ha elegido turno antes que el primero (Y por tanto está esperando a que sea calculado)
-- Esta función sería la contraposición de oponenteHaElegidoEsteTurno
CREATE FUNCTION oponenteHaElegidoSiguienteTurno(idRollo INT)
  RETURNS BIT
  READS SQL DATA
  BEGIN
    DECLARE _idOponente, _turno INT;

    SELECT ID_Oponente, Turno
    INTO _idOponente, _turno
    FROM Duelos
    WHERE ID_Rollo = idRollo
    ORDER BY Turno DESC
    LIMIT 1;

    RETURN EXISTS(SELECT 1
                  FROM Duelos
                  WHERE ID_Rollo = _idOponente AND ID_Oponente = idRollo AND Turno = (_turno+1));
  END $$
  
CREATE FUNCTION calcularPremioDuelo(idRollo INT)
RETURNS INT
READS SQL DATA
BEGIN
	DECLARE premio, vidaRollo, fuerzaRollo, constitucionRollo, destrezaRollo, pactosRollo, vidaOponente, fuerzaOponente, constitucionOponente, destrezaOponente, pactosOponente INT;
    DECLARE oponenteHaBorradoSusDatos BIT;
    
	SET premio = 0;
	SET oponenteHaBorradoSusDatos = (SELECT NOT EXISTS(SELECT 1
									  FROM Duelos AS D1
										INNER JOIN Duelos AS D2
										  ON D1.ID_Rollo = D2.ID_Oponente AND D1.Turno = D2.Turno
									  WHERE D1.ID_Rollo = idRollo
									  ORDER BY D1.Turno DESC
									  LIMIT 1));   
                                      
	IF(oponenteHaBorradoSusDatos) THEN
    BEGIN
		SELECT D1.Vida, A1.Fuerza, A1.Constitucion, A1.Destreza, A1.Pactos, A2.Fuerza, A2.Constitucion, A2.Destreza, A2.Pactos
			INTO vidaRollo, fuerzaRollo, constitucionRollo, destrezaRollo, pactosRollo, fuerzaOponente, constitucionOponente, destrezaOponente, pactosOponente
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
            
            IF(vidaRollo IS NOT NULL) THEN
            BEGIN
				IF(vidaRollo > 0) THEN
				BEGIN
					SET premio = CEIL(10 * ( (fuerzaOponente + constitucionOponente + destrezaOponente) * (1+(pactosOponente/10)) )/( (fuerzaRollo + constitucionRollo + destrezaRollo) * (1+(pactosRollo))));
				END;
				ELSE
					SET premio = 1;
				BEGIN
				END;
				END IF;
			END;
            END IF;			
    END;    
    ELSE
    BEGIN
		SELECT D1.Vida, A1.Fuerza, A1.Constitucion, A1.Destreza, A1.Pactos, D2.Vida, A2.Fuerza, A2.Constitucion, A2.Destreza, A2.Pactos
			INTO vidaRollo, fuerzaRollo, constitucionRollo, destrezaRollo, pactosRollo, vidaOponente, fuerzaOponente, constitucionOponente, destrezaOponente, pactosOponente
			FROM Duelos AS D1
			  INNER JOIN Duelos AS D2
				ON D1.ID_Rollo = D2.ID_Oponente AND D1.Turno = D2.Turno
			  INNER JOIN Rollos AS R1
				ON D1.ID_Rollo = R1.ID_Usuario
			  INNER JOIN Rollos AS R2
				ON D2.ID_Rollo = R2.ID_Usuario
			  INNER JOIN Atributos AS A1
				ON R1.ID_Atributos = A1.ID
			  INNER JOIN Atributos AS A2
				ON R2.ID_Atributos = A2.ID
			WHERE D1.ID_Rollo = idRollo
			ORDER BY D1.Turno DESC
			LIMIT 1;
            
		IF(vidaRollo = 0 OR vidaOponente = 0)THEN
        BEGIN
			IF(vidaRollo >0)THEN
            BEGIN
				SET premio = CEIL(10 * ( (fuerzaOponente + constitucionOponente + destrezaOponente) * (1+(pactosOponente/10)) )/( (fuerzaRollo + constitucionRollo + destrezaRollo) * (1+(pactosRollo))));
            END;
            ELSE
            BEGIN
				SET premio = 1;
            END;
            END IF;
        END;
        END IF;
    END;
    END IF;
       
    RETURN premio;
END $$

-- Procedimientos

-- Crea un usuario insertando en todas las tablas necesarias
CREATE PROCEDURE crearUsuario(IN _usuario NVARCHAR(15), IN _contrasena NVARCHAR(255), OUT conseguido BIT)
  BEGIN
    DECLARE _idUsuario, _idAtributos INT;

    INSERT INTO Usuarios (Usuario, Contrasena) VALUE (_usuario, _contrasena);
    IF(ROW_COUNT()>0) THEN
      BEGIN
        SET _idUsuario = LAST_INSERT_ID();
        INSERT INTO Atributos () VALUES ();
        SET _idAtributos = LAST_INSERT_ID();
        INSERT INTO Rollos(ID_Usuario, ID_Atributos, ID_Zona) SELECT _idUsuario, _idAtributos, ID FROM Zonas WHERE Nivel=1;

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
    DECLARE _ataqueEnemigo, _vidaRollo, _vidaEnemigo, _enemigoMasRapido, _idAtributosRollo, _idAtributosEnemigo, _idEnemigo INT;

    SET _ataqueEnemigo = ataqueAleatorio();
    SELECT VidaRollo, VidaEnemigo, enemigoMasRapido(AR.Destreza, AE.Destreza) AS enemigoMasRapido, AR.ID AS idAtributosRollo, AE.ID AS idAtributosEnemigo, E.ID
    INTO _vidaRollo, _vidaEnemigo, _enemigoMasRapido, _idAtributosRollo, _idAtributosEnemigo, _idEnemigo
    FROM Caza AS C
      INNER JOIN Rollos AS R ON C.ID_Rollo = R.ID_Usuario
      INNER JOIN Atributos AS AR ON R.ID_Atributos = AR.ID
      INNER JOIN Enemigos AS E ON C.ID_Enemigo = E.ID
      INNER JOIN Atributos AS AE ON E.ID_Atributos = AE.ID
    WHERE C.ID_Rollo = idRollo;
    IF(_vidaRollo>0 AND _vidaEnemigo>0) THEN
      BEGIN
        IF(_enemigoMasRapido) THEN
          BEGIN
            SET _vidaRollo = _vidaRollo - dano(_idAtributosEnemigo, _idAtributosRollo, _ataqueEnemigo, ataqueRollo);
            IF(_vidaRollo>0) THEN
              BEGIN
                SET _vidaEnemigo = _vidaEnemigo - dano(_idAtributosRollo, _idAtributosEnemigo, ataqueRollo, _ataqueEnemigo);
              END;
            END IF;
          END;
        ELSE
          BEGIN
            SET _vidaEnemigo = _vidaEnemigo - dano(_idAtributosRollo, _idAtributosEnemigo, ataqueRollo, _ataqueEnemigo);
            IF(_vidaEnemigo>0)THEN
              BEGIN
                SET _vidaRollo = _vidaRollo - dano(_idAtributosEnemigo, _idAtributosRollo, _ataqueEnemigo, ataqueRollo);
              END;
            END IF;
          END;
        END IF;

        IF(_vidaRollo<0) THEN
          BEGIN
            SET _vidaRollo = 0;
          END;
        END IF;

        IF(_vidaEnemigo<0) THEN
          BEGIN
            SET _vidaEnemigo = 0;
          END;
        END IF;

        IF(_vidaRollo>0 AND _vidaEnemigo = 0) THEN
          BEGIN
            CALL marcarVictoria(idRollo, _idEnemigo);
            CALL revisarNivel(idRollo);
          END;
        END IF;

        UPDATE Caza SET VidaRollo = _vidaRollo, VidaEnemigo = _vidaEnemigo, AtaqueRollo = ataqueRollo, AtaqueEnemigo = _ataqueEnemigo WHERE ID_Rollo = idRollo;
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
    DECLARE _nivel INT;

    SET _nivel = (SELECT COUNT(*)+1
                  FROM Rollos AS R
                    INNER JOIN Vencidos AS V
                      ON R.ID_Usuario = V.ID_Rollo
                    INNER JOIN Enemigos AS E
                      ON V.ID_Enemigo = E.ID
                  WHERE R.ID_Usuario = idRollo AND E.EsJefe);

    UPDATE Rollos AS R SET Nivel = _nivel
    WHERE R.ID_Usuario = idRollo;
  END $$

-- Asocia un material a un enemigo de forma que pueda darlo como premio al vencerle
CREATE PROCEDURE asociarEnemigoMaterial(IN nombreEnemigo VARCHAR(15), IN nombreMaterial VARCHAR(30), IN _cantidad INT, IN _probabilidad INT)
  BEGIN
    DECLARE _idEnemigo, _idMaterial INT;

    SET _idEnemigo = (SELECT ID FROM Enemigos WHERE Nombre = nombreEnemigo);
    SET _idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    INSERT INTO Enemigos_Materiales (ID_Enemigo, ID_Material, Cantidad, Probabilidad) VALUE (_idEnemigo, _idMaterial, _cantidad, _probabilidad);
  END $$

-- Concede una cantidad de material a un rollo
CREATE PROCEDURE concederPremio(IN idRollo INT, IN nombreMaterial VARCHAR(30), IN cantidadAnadida INT)
  BEGIN
    DECLARE _idMaterial, _cantidadOriginal INT;

    SET _idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    IF EXISTS (SELECT 1 FROM Rollos_Materiales WHERE ID_Rollo = idRollo AND ID_Material = _idMaterial) THEN
      BEGIN
        START TRANSACTION;
        SET _cantidadOriginal = (SELECT Cantidad FROM Rollos_Materiales  WHERE ID_Rollo = idRollo AND ID_Material = _idMaterial);
        UPDATE Rollos_Materiales SET Cantidad = (_cantidadOriginal + cantidadAnadida) WHERE ID_Rollo = idRollo AND ID_Material = _idMaterial;
        COMMIT;
      END;
    ELSE
      BEGIN
        INSERT INTO Rollos_Materiales(ID_Rollo, ID_Material, Cantidad) VALUE (idRollo, _idMaterial, cantidadAnadida);
      END;
    END IF;
  END $$

-- Asocia una cantidad de material a un equipable de forma que sea necesaria dicha cantidad para fabricarlo
CREATE PROCEDURE asociarEquipableMaterial(IN nombreEquipable VARCHAR(30), IN nombreMaterial VARCHAR(30), IN _cantidad INT)
  BEGIN
    DECLARE _idEquipable, _idMaterial INT;

    SET _idEquipable = (SELECT ID FROM Equipables WHERE Nombre = nombreEquipable);
    SET _idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    INSERT INTO Equipables_Materiales (ID_Equipable, ID_Material, Cantidad) VALUE (_idEquipable, _idMaterial, _cantidad);
  END $$

-- Asocia una cantidad de un submaterial a un material compuesto de forma que sea necesaria dicha cantidad para fabricarlo
CREATE PROCEDURE asociarMaterialSubmaterial(IN nombreMaterial VARCHAR(30), IN nombreSubmaterial VARCHAR(30), IN _cantidad INT)
  BEGIN
    DECLARE _idMaterial, _idSubmaterial INT;

    SET _idMaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreMaterial);
    SET _idSubmaterial = (SELECT ID FROM Materiales WHERE Nombre = nombreSubmaterial);
    INSERT INTO Materiales_Materiales (ID_Material, ID_Submaterial, Cantidad) VALUE (_idMaterial, _idSubmaterial, _cantidad);
  END $$

-- Gasta una cantidad de material en de un rollo
CREATE PROCEDURE gastarMaterial(IN idRollo INT, IN idMaterial INT, IN cantidadGastada INT)
  BEGIN
    DECLARE _cantidadOriginal INT;

    START TRANSACTION;
    SET _cantidadOriginal = (SELECT Cantidad FROM Rollos_Materiales  WHERE ID_Rollo = idRollo AND ID_Material = idMaterial);
    UPDATE Rollos_Materiales SET Cantidad = (_cantidadOriginal - cantidadGastada) WHERE ID_Rollo = idRollo AND ID_Material = idMaterial;
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
    DECLARE _nombreMaterial VARCHAR(30);

    SET _nombreMaterial = (SELECT Nombre FROM Materiales WHERE ID = idMaterial);
    CALL concederPremio(idRollo, _nombreMaterial, 1);
  END $$

-- Equipa un arma o sombrero para que pueda ser usado durante la caza o el duelo
CREATE PROCEDURE equipar(IN idRollo INT, IN idEquipable INT)
  BEGIN
    DECLARE _tipoEquipable CHAR(1);

    START TRANSACTION;
    SET _tipoEquipable = (SELECT Tipo FROM Equipables WHERE ID = idEquipable);
    UPDATE Rollos_Equipables AS RE INNER JOIN Equipables AS E ON RE.ID_Equipable = E.ID SET Equipado = 0 WHERE RE.ID_Rollo = idRollo AND E.Tipo = _tipoEquipable;
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
	DECLARE _previamenteRetado BIT;
    DECLARE _momento INT;
    
	START TRANSACTION;
	DELETE FROM Duelos WHERE ID_Rollo = idRollo;
    IF (EXISTS(SELECT 1 FROM Duelos WHERE ID_Rollo = idOponente AND ID_Oponente = idRollo)) THEN
    BEGIN
		SET _momento = UNIX_TIMESTAMP();
        UPDATE Duelos SET Momento = _momento WHERE ID_Rollo = idOponente AND ID_Oponente = idRollo;
		INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, idOponente, 0, NULL, NULL, _momento);
    END;
    ELSE
    BEGIN
		INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, idOponente, 0, NULL, NULL, NULL);
    END;
    END IF;
    
    
    COMMIT;
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
    DECLARE _vidaRollo, _enemigoMasRapido, _idAtributosRollo, _idAtributosEnemigo, _idEnemigo, _turno, _ataqueEnemigo, _vidaEnemigo, _habiaElegidoAtaque, _momento INT;

    SELECT D1.Vida, enemigoMasRapido(A1.Destreza, A2.Destreza), A1.ID, A2.ID, D1.ID_Oponente, D1.Turno
    INTO _vidaRollo, _enemigoMasRapido, _idAtributosRollo, _idAtributosEnemigo, _idEnemigo, _turno
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

    SET _ataqueEnemigo = (SELECT Ataque FROM Duelos WHERE ID_Oponente = idRollo ORDER BY Turno DESC LIMIT 1);
    SET _vidaEnemigo = (SELECT Vida FROM Duelos WHERE ID_Oponente = idRollo AND Turno = _turno LIMIT 1);
    
    IF(_vidaRollo IS NULL OR _vidaEnemigo IS NULL) THEN
    BEGIN
		SET _vidaRollo = 100;
        SET _vidaEnemigo = 100;
    END;
    END IF;

    SET _habiaElegidoAtaque = TRUE;
    IF(_ataqueEnemigo IS NULL) THEN
      BEGIN
        SET _habiaElegidoAtaque = FALSE;
        SET _ataqueEnemigo = ataqueAleatorio();
      END;
    END IF;

    IF(_enemigoMasRapido) THEN
      BEGIN
        SET _vidaRollo = _vidaRollo - dano(_idAtributosEnemigo, _idAtributosRollo, _ataqueEnemigo, ataqueRollo);
        IF(_vidaRollo>0) THEN
          BEGIN
            SET _vidaEnemigo = _vidaEnemigo - dano(_idAtributosRollo, _idAtributosEnemigo, ataqueRollo, _ataqueEnemigo);
          END;
        END IF;
      END;
    ELSE
      BEGIN
        SET _vidaEnemigo = _vidaEnemigo - dano(_idAtributosRollo, _idAtributosEnemigo, ataqueRollo, _ataqueEnemigo);
        IF(_vidaEnemigo>0)THEN
          BEGIN
            SET _vidaRollo = _vidaRollo - dano(_idAtributosEnemigo, _idAtributosRollo, _ataqueEnemigo, ataqueRollo);
          END;
        END IF;
      END;
    END IF;

    IF(_vidaRollo<0) THEN
      BEGIN
        SET _vidaRollo = 0;
      END;
    END IF;

    IF(_vidaEnemigo<0) THEN
      BEGIN
        SET _vidaEnemigo = 0;
      END;
    END IF;

    IF(_vidaRollo>0 AND _vidaEnemigo = 0) THEN
      BEGIN
        -- IF vacío para posible condición de victoria
        -- CALL marcarVictoria(idRollo, _idEnemigo);
        -- CALL revisarNivel(idRollo);
      END;
    END IF;

    SET _momento = UNIX_TIMESTAMP();

    IF(_habiaElegidoAtaque) THEN
      BEGIN
        INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, _idEnemigo, (_turno+1), _vidaRollo, ataqueRollo, _momento);
        UPDATE Duelos SET Vida = _vidaEnemigo, Momento = _momento WHERE ID_Rollo = _idEnemigo AND ID_Oponente = idRollo AND Turno = (_turno+1);
      END;
    ELSE
      BEGIN
        INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUES
          (idRollo, _idEnemigo, (_turno+1), _vidaRollo, ataqueRollo, _momento),
          (_idEnemigo, idRollo, (_turno+1), _vidaEnemigo, _ataqueEnemigo, _momento);
      END;
    END IF;
  END $$

CREATE PROCEDURE elegirTurnoDuelo(IN idRollo INT, IN ataqueRollo INT)
  BEGIN
    DECLARE _idOponente, _turno INT;

    SELECT ID_Oponente, Turno
    INTO _idOponente, _turno
    FROM Duelos WHERE ID_Rollo = idRollo
    ORDER BY Turno DESC
    LIMIT 1;
    INSERT INTO Duelos (ID_Rollo, ID_Oponente, Turno, Vida, Ataque, Momento) VALUE (idRollo, _idOponente, (_turno+1), NULL, ataqueRollo, NULL);
  END $$
  
CREATE PROCEDURE concederPremioDuelo(IN _idRollo INT, IN _cantidad INT)
  BEGIN
	DELETE FROM Duelos WHERE ID_Rollo = _idRollo;
    UPDATE Rollos SET Honor = Honor + _cantidad WHERE ID_Usuario = _idRollo;
  END $$
  
CREATE PROCEDURE marcarUltimoDuelo(IN _idRollo INT, IN _idOponente INT)
  BEGIN
	START TRANSACTION;
	IF EXISTS (SELECT 1 FROM Ultimos_Duelos WHERE ID_Rollo = _idRollo AND ID_Oponente = _idOponente) THEN
      BEGIN
        UPDATE Ultimos_Duelos SET Momento = UNIX_TIMESTAMP() WHERE ID_Rollo = _idRollo AND ID_Oponente = _idOponente;
      END;
    ELSE
      BEGIN
        INSERT INTO Ultimos_Duelos(ID_Rollo, ID_Oponente, Momento) VALUE (_idRollo, _idOponente, UNIX_TIMESTAMP());
      END;
    END IF;
    COMMIT;
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
CALL crearEnemigo('hambriento', 10, 20, 30, FALSE, 'bano');
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
CALL asociarEnemigoMaterial('hambriento', 'buen_rollo', 1, 100);
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