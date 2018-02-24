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

        $query = "SELECT E.Nombre, E.EsJefe, enemigoMasRapido(RA.Destreza, EA.Destreza) AS esMasRapido
                    FROM Rollos AS R
                      INNER JOIN Atributos AS RA
                        ON R.ID_Atributos = RA.ID
                      INNER JOIN Enemigos AS E
                        ON R.ID_Zona = E.ID_Zona
                      INNER JOIN Atributos AS EA
                        ON E.ID_Atributos = EA.ID
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($nombre, $esJefe, $esMasRapido);
        $prep_query->execute();

        $listaEnemigos = array();
        while($prep_query->fetch()){
            $listaEnemigos[] = new EnemigoSimplificadoModel($nombre, $esJefe, $esMasRapido);
        }

        return $listaEnemigos;
    }

    public static function getEnemigoEnCaza($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT E.Nombre, E.EsJefe, enemigoMasRapido(RA.Destreza, EA.Destreza) AS masRapido
                    FROM Rollos AS R
                      INNER JOIN Atributos AS RA
                        ON R.ID_Atributos = RA.ID
                      INNER JOIN Caza AS C
                      ON R.ID_Usuario = C.ID_Rollo
                      INNER JOIN Enemigos AS E
                        ON C.ID_Enemigo = E.ID
                      INNER JOIN Atributos AS EA
                        ON E.ID_Atributos = EA.ID
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($nombre, $esJefe, $esMasRapido);
        $prep_query->execute();
        $prep_query->fetch();

        $enemigo = new EnemigoSimplificadoModel($nombre, $esJefe, $esMasRapido);

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

    /*public static function entrenar($id, $atributo){
        $conseguido = false;

        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        //Se comprueba si ha pasado tiempo suficiente para volver a entrenar
        $query ='SELECT FinEntrenamiento FROM Atributos
                                              INNER JOIN Rollos ON Atributos.ID = Rollos.ID_Atributos
                                              WHERE Rollos.ID_Usuario = ?;';
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($finEntrenamiento);
        $prep_query->execute();
        $prep_query->fetch();

        //Si ha pasado tiempo suficiente, se entrena
        if($finEntrenamiento<=time()){
            $prep_query->free_result();
            $query = "CALL entrenar(?,?);";
            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('is', $id, $atributo);

            $prep_query->execute();
            if($db_connection->affected_rows>0){
                $conseguido = true;
            }
        }
        return $conseguido;
    }*/
}