<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "EnemigoSimplificadoModel.php";

class DueloHandlerModel{
    private static function getAmigosRanked($idUsuario){
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

        $listaAmigosRanked = null;
        while($prep_query->fetch()){
            $listaAmigosRanked[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaAmigosRanked;
    }

    private static function getAmigosAmistoso($idUsuario){
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

        $listaAmigosRanked = null;
        while($prep_query->fetch()){
            $listaAmigosRanked[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaAmigosRanked;
    }

    private static function getRetosDuelo($idUsuario){
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

        $listaRetosDuelo = null;
        while($prep_query->fetch()){
            $listaRetosDuelo[] = new AmigoModel($id, $usuario, $rango, $honor, $nivel);
        }
        return $listaRetosDuelo;
    }

    private static function enDueloCon($idUsuario){
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

    public static function getLobbyDuelo($id){
        $enDueloCon = self::enDueloCon($id);
        //Si está en duelo con alguien, no necesita saber la información de a quién puede retar o quién le ha retado
        if($enDueloCon == null){
            $retosDuelo = self::getRetosDuelo($id);
            $amigosRanked = self::getAmigosRanked($id);
            $amigosAmistoso = self::getAmigosAmistoso($id);
        }
        $lobbyDueloModel = new LobbyDueloModel($enDueloCon, $retosDuelo, $amigosRanked, $amigosAmistoso);
        return $lobbyDueloModel;
    }

    public static function esAmigoMutuo($idRollo, $idOponente){
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

    public static function retarADuelo($idRollo, $idOponente){
        if(self::esAmigoMutuo($idRollo, $idOponente)){
            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "CALL retarADuelo(?,?);";

            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ii', $idRollo, $idOponente);
            $prep_query->execute();
        }
    }

    public static function rechazarDuelo($idRollo, $idOponente){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL rechazarDuelo(?,?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idOponente);
        $prep_query->execute();
    }

    public static function getEstadoRollo($idRollo){
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
    }

    public static function getEstado($idRollo, $idOponente){
        $estado = null;
        $estadoOponente = self::getEstadoRollo($idOponente);
        if($estadoOponente->getVidaRollo() != null){
            $estadoRollo = self::getEstadoRollo($idRollo);
            $estado = new EstadoDueloModel($estadoRollo->getVidaRollo(), $estadoRollo->getAtaqueRollo(),
                $estadoOponente->getVidaRollo(), $estadoOponente->getAtaqueRollo(), null);
        }
        return $estado;
    }

    public static function getDuelo($idRollo, $idOponente){
        $duelo = null;
        if($idRollo != $idOponente){
            $estado = self::getEstado($idRollo, $idOponente);
            if($estado != null){
                $rollo = RolloHandlerModel::getRollo($idRollo);
                $oponente = RolloHandlerModel::getRollo($idOponente);
                $duelo = new DueloModel($rollo, $oponente, $estado);
            }
        }
        return $duelo;
    }
}