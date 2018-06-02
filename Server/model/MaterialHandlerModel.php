<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 18/05/2018
 * Time: 12:11
 */

class MaterialHandlerModel
{
    public static function materialesNecesarios($idRollo, $idSupermaterial){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $materialesNecesarios = array();

        $query = "SELECT
                    M2.ID,
                    M2.Nombre,
                    IFNULL((SELECT Cantidad FROM Rollos_Materiales AS RM WHERE ID_Rollo = ? AND RM.ID_Material = MM.ID_Submaterial), 0) AS CantidadActual,
                    MM.Cantidad AS CantidadNecesaria
                  FROM Materiales AS M
                    INNER JOIN Materiales_Materiales AS MM
                      ON M.ID = MM.ID_Material
                    INNER JOIN Materiales AS M2
                      ON MM.ID_Submaterial = M2.ID
                    WHERE M.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idSupermaterial);
        $prep_query->bind_result($id, $nombre, $cantidadActual, $cantidadNecesaria);
        $prep_query->execute();

        while($prep_query->fetch()){
            $materialesNecesarios[] = new MaterialNecesarioModel($id, $nombre, $cantidadActual, $cantidadNecesaria);
        }

        return $materialesNecesarios;
    }

    public static function puedePermitirseFabricar($idRollo, $idSupermaterial){
        $materialesNecesarios = self::materialesNecesarios($idRollo, $idSupermaterial);
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

        $query = "CALL gastarMaterial(?,?,?);";
        $prep_query = $db_connection->prepare($query);
        for($i=0; $i<count($materialesNecesarios); $i++) {
            $idMaterial = $materialesNecesarios[$i]->getId();
            $cantidadNecesaria = $materialesNecesarios[$i]->getCantidadNecesaria();
            $prep_query->bind_param('iii', $idRollo, $idMaterial, $cantidadNecesaria);
            $prep_query->execute();
        }
    }

    public static function supermaterial($idRollo, $idSupermaterial){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT
                      M.ID,
                      M.Nombre,
                      IFNULL((SELECT Cantidad FROM Rollos_Materiales WHERE ID_Rollo = ? AND ID_Material = ?), 0) AS Cantidad
                    FROM
                      Materiales AS M
                    WHERE
                      M.ID = ?;";
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('iii', $idRollo, $idSupermaterial, $idSupermaterial);
        $prep_query->bind_result($id, $nombre, $cantidad);
        $prep_query->execute();
        $prep_query->fetch();
        $supermaterial = new SupermaterialModel($id, $nombre, $cantidad, null);
        $prep_query->close();

        $supermaterial->setMaterialesNecesarios(self::materialesNecesarios($idRollo, $idSupermaterial));
        return $supermaterial;
    }

    public static function fabricar($idRollo, $idSupermaterial){
        $fabricacionExitosa = false;

        if(self::puedePermitirseFabricar($idRollo, $idSupermaterial)){
            self::gastarMaterialesNecesarios($idRollo, self::materialesNecesarios($idRollo, $idSupermaterial));

            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "CALL anadirSupermaterial(?,?);";

            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ii', $idRollo, $idSupermaterial);
            $prep_query->execute();
            $fabricacionExitosa = true;
        }
        return $fabricacionExitosa;
    }
}