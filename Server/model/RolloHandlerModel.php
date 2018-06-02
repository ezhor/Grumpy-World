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

        $query = "SELECT U.Usuario AS Nombre, Z.Nombre AS Zona, R.Nivel, rangoRollo(R.Honor, R.ID_Usuario) AS Rango,
                       (
                         SELECT Nombre
                         FROM Equipables AS E
                           INNER JOIN Rollos_Equipables AS RE ON E.ID = RE.ID_Equipable
                         WHERE RE.ID_Rollo = ? AND E.Tipo = 'S' AND RE.Equipado = TRUE
                       ) AS Sombrero,
                       (
                         SELECT Nombre
                         FROM Equipables AS E
                           INNER JOIN Rollos_Equipables AS RE ON E.ID = RE.ID_Equipable
                         WHERE RE.ID_Rollo = ? AND E.Tipo = 'A' AND Equipado = TRUE
                       ) AS Arma
                FROM Usuarios AS U
                  INNER JOIN Rollos AS R ON U.ID = R.ID_Usuario
                  INNER JOIN Zonas AS Z ON R.ID_Zona = Z.ID
                WHERE U.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('iii', $id, $id, $id);
        $prep_query->bind_result($nombre, $zona, $nivel, $rango, $sombrero, $arma);
        $prep_query->execute();
        $prep_query->fetch();
        $rollo = new RolloModel($nombre, $sombrero, $arma, $zona, $nivel, $rango);
        return $rollo;
    }
}