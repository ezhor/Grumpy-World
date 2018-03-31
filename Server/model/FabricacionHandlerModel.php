<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class FabricacionHandlerModel
{
    public static function getEquipablesDisponibles($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $equipables = array();

        $query = "SELECT ID, Nombre, Tipo, Bonus
                    FROM Rollos AS R
                      INNER JOIN Equipables AS E
                        ON R.Nivel >= E.NivelNecesario
                      WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($id, $nombre, $tipo, $bonus);
        $prep_query->execute();
        while($prep_query->fetch()){
            $equipables[] = new EquipableModel($id, $nombre, $tipo, $bonus);
        }

        return $equipables;
    }

    public static function getEquipableDetalle($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT Nombre, Tipo, Bonus, DestrezaNecesaria, FuerzaNecesaria, NivelNecesario
                    FROM Equipables
                    WHERE ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idEquipable);
        $prep_query->bind_result($nombre, $tipo, $bonus, $destrezaNecesaria, $fuerzaNecesaria, $nivelNecesario);
        $prep_query->execute();
        $prep_query->fetch();
        $equipablesDetalle = new EquipableDetalleModel($idEquipable, $nombre, $tipo, $bonus, $destrezaNecesaria,
            $fuerzaNecesaria, $nivelNecesario, null);
        $prep_query->close();
        $equipablesDetalle->setMaterialesNecesarios(self::materialesNecesarios($idRollo, $idEquipable));

        return $equipablesDetalle;
    }

    private static function materialesNecesarios($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $materialesNecesarios = array();

        $query = "SELECT
                    M.ID,
                    M.Nombre,
                    IFNULL((SELECT Cantidad FROM Rollos_Materiales AS RM WHERE ID_Rollo = ? AND RM.ID_Material = EM.ID_Material), 0) AS CantidadActual,
                    EM.Cantidad AS CantidadNecesaria
                  FROM Equipables AS E
                    LEFT JOIN Equipables_Materiales AS EM
                      ON E.ID = EM.ID_Equipable
                    LEFT JOIN Materiales AS M
                      ON EM.ID_Material = M.ID
                    WHERE E.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
        $prep_query->bind_result($id, $nombre, $cantidadActual, $cantidadNecesaria);
        $prep_query->execute();

        while($prep_query->fetch()){
            $materialesNecesarios[] = new MaterialNecesarioModel($id, $nombre, $cantidadActual, $cantidadNecesaria);
        }

        return $materialesNecesarios;
    }

    public static function puedeFabricar($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT (SELECT Nivel FROM Rollos WHERE ID_Usuario = ?) >=
       (SELECT NivelNecesario FROM Equipables WHERE ID = ?);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
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

    /*public static function cambiarZona($id, $zona){
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
    }*/
}