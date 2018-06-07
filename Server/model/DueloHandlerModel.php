<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "EnemigoSimplificadoModel.php";

class DueloHandlerModel{
    const TIEMPO_TURNO = 20;

    private static function getAmigosRanked($idUsuario)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT A1.ID_Usuario2, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Amigos A1
                      INNER JOIN Amigos AS A2
                        ON A1.ID_Usuario1 = A2.ID_Usuario2 AND A1.ID_Usuario2 = A2.ID_Usuario1
                      INNER JOIN Usuarios AS U
                        ON A1.ID_Usuario2 = U.ID
                      INNER JOIN Rollos AS R
                        ON A1.ID_Usuario2 = R.ID_Usuario
                    WHERE A1.ID_Usuario1 = ? AND !dueloHaceMenosDeUnDia(A1.ID_Usuario1, A1.ID_Usuario2);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($id, $usuario, $rango, $honor, $nivel);
        $prep_query->execute();

        $listaAmigosRanked = array();
        while ($prep_query->fetch()) {
            $listaAmigosRanked[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaAmigosRanked;
    }

    private static function getAmigosAmistoso($idUsuario)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT A1.ID_Usuario2, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Amigos A1
                      INNER JOIN Amigos AS A2
                        ON A1.ID_Usuario1 = A2.ID_Usuario2 AND A1.ID_Usuario2 = A2.ID_Usuario1
                      INNER JOIN Usuarios AS U
                        ON A1.ID_Usuario2 = U.ID
                      INNER JOIN Rollos AS R
                        ON A1.ID_Usuario2 = R.ID_Usuario
                    WHERE A1.ID_Usuario1 = ? AND dueloHaceMenosDeUnDia(A1.ID_Usuario1, A1.ID_Usuario2);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($id, $usuario, $rango, $honor, $nivel);
        $prep_query->execute();

        $listaAmigosRanked = array();
        while ($prep_query->fetch()) {
            $listaAmigosRanked[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaAmigosRanked;
    }

    private static function getRetosDuelo($idUsuario)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT D1.ID_Rollo, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Duelos AS D1
                      INNER JOIN Usuarios AS U
                        ON D1.ID_Rollo = U.ID
                      INNER JOIN Rollos AS R
                        ON D1.ID_Rollo = R.ID_Usuario
                    WHERE D1.ID_Oponente = ?
                          AND D1.Turno = 0
                          AND D1.ID_Oponente NOT IN (SELECT D2.ID_Rollo FROM Duelos AS D2 WHERE D2.ID_Oponente = D1.ID_Rollo);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($id, $usuario, $rango, $honor, $nivel);
        $prep_query->execute();

        $listaRetosDuelo = array();
        while ($prep_query->fetch()) {
            $listaRetosDuelo[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaRetosDuelo;
    }

    private static function enDueloCon($idUsuario)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT D1.ID_Rollo
                    FROM Duelos AS D1
                      INNER JOIN Usuarios AS U
                        ON D1.ID_Rollo = U.ID
                      INNER JOIN Rollos AS R
                        ON D1.ID_Rollo = R.ID_Usuario
                    WHERE D1.ID_Oponente = ?
                          AND D1.Turno = 0
                          AND D1.ID_Oponente IN (SELECT D2.ID_Rollo FROM Duelos AS D2 WHERE D2.ID_Oponente = D1.ID_Rollo)
                    LIMIT 1;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($id);
        $prep_query->execute();
        $prep_query->fetch();

        return $id;
    }

    public static function getLobbyDuelo($id)
    {
        $enDueloCon = self::enDueloCon($id);
        //Si está en duelo con alguien, no necesita saber la información de a quién puede retar o quién le ha retado
        if ($enDueloCon == null) {
            $retosDuelo = self::getRetosDuelo($id);
            $amigosRanked = self::getAmigosRanked($id);
            $amigosAmistoso = self::getAmigosAmistoso($id);
        } else {
            $retosDuelo = array();
            $amigosRanked = array();
            $amigosAmistoso = array();
        }
        $lobbyDueloModel = new LobbyDueloModel($enDueloCon, $retosDuelo, $amigosRanked, $amigosAmistoso);
        return $lobbyDueloModel;
    }

    public static function esAmigoMutuo($idRollo, $idOponente)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT EXISTS(
                        SELECT *
                        FROM Amigos AS A1
                          INNER JOIN Amigos AS A2
                            ON A1.ID_Usuario2 = A2.ID_Usuario1
                        WHERE A1.ID_Usuario1 = ? AND A1.ID_Usuario2 = ?
                    );";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idOponente);
        $prep_query->bind_result($existe);
        $prep_query->execute();
        $prep_query->fetch();

        $existe = $existe == 1;

        return $existe;
    }

    public static function retarADuelo($idRollo, $idOponente)
    {
        if (self::esAmigoMutuo($idRollo, $idOponente)) {
            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "CALL retarADuelo(?,?);";

            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ii', $idRollo, $idOponente);
            $prep_query->execute();
        }
    }

    public static function rechazarDuelo($idRollo, $idOponente)
    {
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL rechazarDuelo(?,?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idOponente);
        $prep_query->execute();
    }

    /*public static function getEstadoRollo($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT Vida, Ataque
                    FROM Duelos
                    WHERE ID_Rollo = ?
                    ORDER BY Turno DESC
                    LIMIT 1;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($vida, $ataque);
        $prep_query->execute();
        $prep_query->fetch();

        return new EstadoRolloModel($vida, $ataque);
    }*/

    public static function getEstado($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT D1.Vida, D1.Ataque, D2.Vida, D2.Ataque, D1.Momento
                    FROM Duelos AS D1
                      INNER JOIN Duelos AS D2
                        ON D1.ID_Rollo = D2.ID_Oponente AND D1.Turno = D2.Turno
                    WHERE D1.ID_Rollo = ?
                    ORDER BY D1.Turno DESC
                    LIMIT 1;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($vidaRollo, $ataqueRollo, $vidaOponente, $ataqueOponente, $momento);
        $prep_query->execute();
        $prep_query->fetch();

        $tiempoLimiteTurno = $momento + self::TIEMPO_TURNO;

        return new EstadoDueloModel($vidaRollo, $ataqueRollo, $vidaOponente, $ataqueOponente, $tiempoLimiteTurno);
    }

    private static function oponenteMasRapido($idRollo, $idOponente){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT enemigoMasRapido(A1.Destreza, A2.Destreza)
                    FROM Duelos AS D
                      INNER JOIN Rollos AS R1
                        ON D.ID_Rollo = R1.ID_Usuario
                      INNER JOIN Atributos AS A1
                        ON R1.ID_Atributos = A1.ID
                      INNER JOIN Rollos AS R2
                        ON D.ID_Oponente = R2.ID_Usuario
                      INNER JOIN Atributos AS A2
                        ON R2.ID_Atributos = A2.ID
                    WHERE ID_Rollo = ? AND ID_Oponente = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idOponente);
        $prep_query->bind_result($esMasRapido);
        $prep_query->execute();
        $prep_query->fetch();

        $esMasRapido = $esMasRapido == 1;

        return $esMasRapido;
    }

    public static function getDuelo($idRollo, $idOponente)
    {
        $duelo = null;
        $estado = self::getEstado($idRollo);
        if ($estado != null) {
            $rollo = RolloHandlerModel::getRollo($idRollo);
            $oponenteSimple = RolloHandlerModel::getRollo($idOponente);
            $oponente = new RolloOponenteModel($oponenteSimple->getNombre(), $oponenteSimple->getSombrero(),
                $oponenteSimple->getArma(), $oponenteSimple->getZona(), $oponenteSimple->getNivel(),
                $oponenteSimple->getRango(), self::oponenteMasRapido($idRollo, $idOponente));
            $duelo = new DueloModel($rollo, $oponente, $estado);
        }
        return $duelo;
    }

    public static function oponenteHaElegidoEsteTurno($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT oponenteHaElegidoEsteTurno(?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($haElegidoTurno);
        $prep_query->execute();
        $prep_query->fetch();

        $haElegidoTurno = $haElegidoTurno == 1;

        return $haElegidoTurno;
    }

    public static function oponenteHaElegidoSiguienteTurno($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT oponenteHaElegidoSiguienteTurno(?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($haElegidoTurno);
        $prep_query->execute();
        $prep_query->fetch();

        $haElegidoTurno = $haElegidoTurno == 1;

        return $haElegidoTurno;
    }

    public static function elegirTurno($idRollo, $ataque){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL elegirTurnoDuelo(?,?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $ataque);
        $prep_query->execute();
    }

    public static function jugarTurno($idRollo, $ataque){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL jugarTurnoDuelo(?,?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $ataque);
        $prep_query->execute();
    }

    public static function yaHaJugadoTurno($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT EXISTS(SELECT 1 FROM Duelos WHERE ID_Rollo = ? AND Vida IS NULL);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($yaHaJugadoTurno);
        $prep_query->execute();
        $prep_query->fetch();

        $yaHaJugadoTurno = $yaHaJugadoTurno == 1;

        return $yaHaJugadoTurno;
    }
}