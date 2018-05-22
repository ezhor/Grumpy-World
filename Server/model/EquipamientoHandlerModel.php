<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class EquipamientoHandlerModel
{
    public static function getEquipablesPoseidos($idRollo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $equipables = array();

        $query = "SELECT
                      RE.ID_Equipable, E.Nombre, E.Tipo, E.Bonus, RE.Equipado
                    FROM Rollos_Equipables AS RE
                      INNER JOIN Equipables AS E
                        ON RE.ID_Equipable = E.ID
                      INNER JOIN Atributos AS A
                        ON RE.ID_Rollo = A.ID
                    WHERE
                        ID_Rollo = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idRollo);
        $prep_query->bind_result($id, $nombre, $tipo, $bonus, $equipado);
        $prep_query->execute();
        while($prep_query->fetch()){
            $equipables[] = new EquipablePoseidoModel($id, $nombre, $tipo, $bonus, $equipado);
        }

        return $equipables;
    }

    public static function getEquipablePoseidoDetalle($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT
                      E.Nombre, E.Tipo, E.Bonus, E.FuerzaNecesaria, E.DestrezaNecesaria, E.NivelNecesario, RE.Equipado,
                      (A.Fuerza >= E.FuerzaNecesaria) AS FuerzaSuficiente, (A.Destreza >= E.DestrezaNecesaria) AS DestrezaSuficiente
                    FROM Rollos AS R
                      INNER JOIN Rollos_Equipables AS RE
                        ON R.ID_Usuario = RE.ID_Rollo
                      INNER JOIN Equipables AS E
                        ON RE.ID_Equipable = E.ID
                      INNER JOIN Atributos AS A
                        ON R.ID_Atributos = A.ID
                    WHERE R.ID_Usuario = ? AND E.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
        $prep_query->bind_result($nombre, $tipo, $bonus, $fuerzaNecesaria, $destrezaNecesaria,
            $nivelNecesario, $equipado, $fuerzaSuficiente, $destrezaSuficiente);
        $prep_query->execute();
        $prep_query->fetch();

        $equipado = $equipado == 1 ? true : false;
        $fuerzaSuficiente = $fuerzaSuficiente == 1 ? true : false;
        $destrezaSuficiente = $destrezaSuficiente == 1 ? true : false;

        $equipableDetalle = new EquipablePoseidoDetalleModel($idEquipable, $nombre, $tipo, $bonus, $equipado,
            $fuerzaNecesaria, $destrezaNecesaria, $nivelNecesario, $fuerzaSuficiente, $destrezaSuficiente);

        return $equipableDetalle;
    }

    public static function tieneEquipable($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT COUNT(*) > 0 FROM Rollos_Equipables WHERE ID_Rollo = ? AND ID_Equipable = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
        $prep_query->bind_result($tiene);
        $prep_query->execute();
        $prep_query->fetch();

        $tiene = $tiene == 1 ? true : false;

        return $tiene;
    }

    public static function puedeEquipar($idRollo, $idEquipable){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT (A.Fuerza >= E.FuerzaNecesaria) AS FuerzaSuficiente, (A.Destreza >= E.DestrezaNecesaria) AS DestrezaSuficiente
                    FROM Rollos AS R
                      INNER JOIN Rollos_Equipables AS RE
                        ON R.ID_Usuario = RE.ID_Rollo
                      INNER JOIN Equipables AS E
                        ON RE.ID_Equipable = E.ID
                      INNER JOIN Atributos AS A
                        ON R.ID_Atributos = A.ID
                      WHERE R.ID_Usuario = ? AND E.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idRollo, $idEquipable);
        $prep_query->bind_result($fuerzaSuficiente, $destrezaSuficiente);
        $prep_query->execute();
        $prep_query->fetch();

        $fuerzaSuficiente = $fuerzaSuficiente == 1 ? true : false;
        $destrezaSuficiente = $destrezaSuficiente == 1 ? true : false;

        return $fuerzaSuficiente && $destrezaSuficiente;
    }

    public static function equipar($idRollo, $idEquipable){
            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "CALL equipar(?,?)";

            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ii', $idRollo, $idEquipable);
            $prep_query->execute();
            $fabricacionExitosa = true;
    }


}