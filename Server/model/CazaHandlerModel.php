<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "EnemigoSimplificadoModel.php";

class CazaHandlerModel
{
    public static function getEnemigosPosibles($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT E.Nombre, Z.Nivel, E.EsJefe, enemigoMasRapido(RA.Destreza, EA.Destreza) AS esMasRapido
                    FROM Rollos AS R
                      INNER JOIN Atributos AS RA
                        ON R.ID_Atributos = RA.ID
                      INNER JOIN Zonas AS Z
                        ON R.ID_Zona = Z.ID 
                      INNER JOIN Enemigos AS E
                        ON Z.ID = E.ID_Zona
                      INNER JOIN Atributos AS EA
                        ON E.ID_Atributos = EA.ID
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($nombre, $nivel, $esJefe, $esMasRapido);
        $prep_query->execute();

        $listaEnemigos = array();
        while($prep_query->fetch()){
            $listaEnemigos[] = new EnemigoSimplificadoModel($nombre, $nivel, $esJefe, $esMasRapido);
        }

        return $listaEnemigos;
    }

    public static function getEnemigoEnCaza($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT E.Nombre, Z.Nivel, E.EsJefe, enemigoMasRapido(RA.Destreza, EA.Destreza) AS masRapido
                    FROM Rollos AS R
                      INNER JOIN Atributos AS RA
                        ON R.ID_Atributos = RA.ID
                      INNER JOIN Caza AS C
                      ON R.ID_Usuario = C.ID_Rollo
                      INNER JOIN Enemigos AS E
                        ON C.ID_Enemigo = E.ID
                      INNER JOIN Zonas AS Z
                        ON E.ID_Zona = Z.ID
                      INNER JOIN Atributos AS EA
                        ON E.ID_Atributos = EA.ID
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($nombre, $nivel, $esJefe, $esMasRapido);
        $prep_query->execute();
        $prep_query->fetch();

        if($esJefe == 1){
            $esJefe = true;
        }else{
            $esJefe = false;
        }

        if($esMasRapido == 1){
            $esMasRapido = true;
        }else{
            $esMasRapido = false;
        }

        $enemigo = new EnemigoSimplificadoModel($nombre, $nivel, $esJefe, $esMasRapido);

        return $enemigo;
    }

    public static function getEstadoCaza($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT VidaRollo, VidaEnemigo, AtaqueRollo, AtaqueEnemigo
                    FROM Caza
                    WHERE ID_Rollo = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($vidaRollo, $vidaEnemigo, $ataqueRollo, $ataqueEnemigo);
        $prep_query->execute();
        $prep_query->fetch();

        $estado = new EstadoModel($vidaRollo, $vidaEnemigo, $ataqueRollo, $ataqueEnemigo);

        return $estado;
    }

    public static function asignarCaza($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL asignarCaza(?)";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->execute();
    }

    public static function jugarTurno($idUsuario, $ataque){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL jugarTurnoCaza(?,?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idUsuario, $ataque);
        $prep_query->execute();
    }

    public static function borrarCaza($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL borrarCaza(?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->execute();
    }
}