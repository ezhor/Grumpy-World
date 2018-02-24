<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class RolloHandlerModel
{
    public static function getRollo($id){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT U.Usuario AS Nombre, S.Nombre AS Sombrero, A.Nombre AS Arma, Z.Nombre AS Zona, rangoRollo(R.Honor, R.ID_Usuario) AS Rango
                    FROM Usuarios AS U
                      LEFT JOIN Rollos AS R
                        ON U.ID = R.ID_Usuario
                      LEFT JOIN Rollos_Equipables AS RE
                        ON R.ID_Usuario = RE.ID_Rollo
                      LEFT JOIN Equipables AS A
                        ON RE.ID_Equipable = A.ID
                      LEFT JOIN Equipables AS S
                        ON RE.ID_Equipable = S.ID
                      LEFT JOIN Zonas AS Z
                        ON R.ID_Zona = Z.ID
                    WHERE R.ID_Usuario = ? AND
                          ( RE.Equipada IS NULL OR RE.Equipada='S') AND
                          (A.Tipo IS NULL OR A.Tipo='A') AND
                          (S.Tipo IS NULL OR S.Tipo='S');";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($nombre, $sombrero, $arma, $zona, $rango);
        $prep_query->execute();
        $prep_query->fetch();

        $rollo = new RolloModel($nombre, $sombrero, $arma, $zona, $rango);

        return $rollo;
    }
}