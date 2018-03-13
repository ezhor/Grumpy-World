<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class ZonaHandlerModel
{
    public static function getZonasDisponibles($id){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT Z.Nombre, Z.Nivel
                    FROM Zonas AS Z
                    INNER JOIN Rollos AS R
                    ON R.Nivel>=Z.Nivel
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($nombre, $nivel);
        $prep_query->execute();
        while($prep_query->fetch()){
            $zonas[] = new ZonaModel($nombre, $nivel);
        }

        return $zonas;
    }

    public static function cambiarZona($id, $zona){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        //Se comprueba si ha pasado tiempo suficiente para volver a entrenar
        $query ='CALL cambiarZona(?,?);';
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('is', $id, $zona);
        $prep_query->execute();
    }

    public static function puedeCambiarZona($id, $zona){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT (
                    (SELECT Nivel FROM Rollos WHERE ID_Usuario = ?) >= (SELECT Nivel FROM Zonas WHERE Nombre = ?)
                  );";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('is', $id, $zona);
        $prep_query->bind_result($puede);
        $prep_query->execute();
        $prep_query->fetch();

        if($puede == 1){
            $puede = true;
        }else{
            $puede = false;
        }

        return $puede;
    }
}