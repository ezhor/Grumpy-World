<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class RankingHandlerModel
{
    public static function getRanked($id){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT U.Usuario, rangoRollo(R.Honor, R.ID_Usuario) AS Rango, Honor
                    FROM Usuarios AS U
                      INNER JOIN Rollos AS R
                        ON U.ID = R.ID_Usuario
                    WHERE U.ID = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($nombre, $rango, $honor);
        $prep_query->execute();
        $prep_query->fetch();

        return new RolloRankedModel($nombre, $rango, $honor);
    }

    public static function getTopRanking(){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT U.Usuario, Honor
                    FROM Usuarios AS U
                      INNER JOIN Rollos AS R
                        ON U.ID = R.ID_Usuario
                    ORDER BY Honor DESC, ID_Usuario ASC
                    LIMIT 10;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_result($nombre, $honor);
        $prep_query->execute();

        $rango = 1;
        while($prep_query->fetch()){
            $ranking[] = new RolloRankedModel($nombre, $rango, $honor);
            $rango++;
        }

        return $ranking;
    }

    public static function getRankedWithTopRanking($id){
        return new RankingModel(self::getRanked($id), self::getTopRanking());
    }


}