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

        $query = "SELECT
                      E.Nombre, E.Tipo, E.Bonus, E.DestrezaNecesaria, E.FuerzaNecesaria, E.NivelNecesario,
                      (SELECT EXISTS(SELECT * FROM Rollos_Equipables WHERE ID_Rollo = ? AND ID_Equipable = E.ID)) AS poseido
                    FROM Equipables AS E
                    WHERE E.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
        $prep_query->bind_result($nombre, $tipo, $bonus, $destrezaNecesaria, $fuerzaNecesaria, $nivelNecesario, $poseido);
        $prep_query->execute();
        $prep_query->fetch();
        $poseido = $poseido == 1;
        $equipableDetalle = new EquipableDetalleModel($idEquipable, $nombre, $tipo, $bonus, $destrezaNecesaria,
            $fuerzaNecesaria, $nivelNecesario, null, $poseido);
        $prep_query->close();
        $equipableDetalle->setMaterialesNecesarios(self::materialesNecesarios($idRollo, $idEquipable));

        return $equipableDetalle;
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

        $puede = $puede == 1 ? true : false;

        return $puede;
    }

    public static function puedePermitirseFabricar($idRollo, $idEquipable){
        $materialesNecesarios = self::materialesNecesarios($idRollo, $idEquipable);
        $puedePermitirse = true;
        for($i=0; $i<count($materialesNecesarios) && $puedePermitirse; $i++){
            if($materialesNecesarios[$i]->getCantidad() < $materialesNecesarios[$i]->getCantidadNecesaria()){
                $puedePermitirse = false;
            }
        }
        return $puedePermitirse;
    }

    public static function gastarMaterialesNecesarios($idRollo, $materialesNecesarios){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL gastarMaterial(?,?,?)";
        $prep_query = $db_connection->prepare($query);
        for($i=0; $i<count($materialesNecesarios); $i++) {
            $idMaterial = $materialesNecesarios[$i]->getId();
            $cantidadNecesaria = $materialesNecesarios[$i]->getCantidadNecesaria();
            $prep_query->bind_param('iii', $idRollo, $idMaterial, $cantidadNecesaria);
            $prep_query->execute();
            echo mysqli_error($db_connection);
        }
    }

    public static function fabricar($idRollo, $idEquipable){
        $fabricacionExitosa = false;
        if(self::puedeFabricar($idRollo, $idEquipable) && self::puedePermitirseFabricar($idRollo, $idEquipable)){

            self::gastarMaterialesNecesarios($idRollo, self::materialesNecesarios($idRollo, $idEquipable));

            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "CALL anadirEquipable(?,?)";

            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ii', $idRollo, $idEquipable);
            $prep_query->execute();
            $fabricacionExitosa = true;
        }
        return $fabricacionExitosa;
    }
}