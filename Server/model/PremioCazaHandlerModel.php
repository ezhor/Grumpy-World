<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "EnemigoSimplificadoModel.php";

class PremioCazaHandlerModel
{
    //Devuelve -1 si el jugador aún no ha terminado la caza
    //Devuelve 0 si el jugador ha terminado la caza pero ha perdido
    //Devuelve 1 si el jugador ha terminado la caza y ha ganado
    public static function merecePremio($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $respuesta = -1;

        $query = "SELECT VidaRollo, VidaEnemigo
                    FROM Rollos AS R
                      INNER JOIN Caza AS C
                        ON R.ID_Usuario = C.ID_Rollo
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($vidaRollo, $vidaEnemigo);
        $prep_query->execute();
        $prep_query->fetch();


        if($vidaRollo == 0 || $vidaEnemigo == 0){
            if($vidaRollo == 0){
                $respuesta = 0;
            }else{
                $respuesta = 1;
            }
        }

        return $respuesta;
        return 1;
    }

    //Devuelve todos los premios posibles en la caza actual
    private static function premiosPosibles($idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $premiosPosibles = array();

        $query = "SELECT M.Nombre, EM.Cantidad, EM.Probabilidad
                    FROM Rollos AS R
                      INNER JOIN Caza AS C
                        ON R.ID_Usuario = C.ID_Rollo
                      INNER JOIN Enemigos AS E
                        ON C.ID_Enemigo = E.ID
                      INNER JOIN Enemigos_Materiales AS EM
                        ON E.ID = EM.ID_Enemigo
                      INNER JOIN Materiales AS M
                        ON EM.ID_Material = M.ID
                    WHERE R.ID_Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $idUsuario);
        $prep_query->bind_result($nombre, $cantidad, $probabilidad);
        $prep_query->execute();

        while ($prep_query->fetch()){
            $premiosPosibles[] = new PremioPosibleModel($nombre, $cantidad, $probabilidad);
        }

        return $premiosPosibles;
    }

    //Concede los premios y los devuelve
    public static function asignarPremios($idUsuario){
        $premiosPosibles = self::premiosPosibles($idUsuario);
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $premiosCondecidos = array();

        $query = "CALL concederPremio(?, ?, ?);";

        $prep_query = $db_connection->prepare($query);

        for($i=0; $i<count($premiosPosibles); $i++){
            $aleatorio = rand(0,100);
            if($aleatorio < $premiosPosibles[$i]->getProbabilidad()){
                $cantidad = rand(1, $premiosPosibles[$i]->getCantidad());
                $prep_query->bind_param('isi', $idUsuario, $premiosPosibles[$i]->getNombre(), $cantidad);
                $prep_query->execute();
                $premiosCondecidos[] = new PremioModel($premiosPosibles[$i]->getNombre(), $cantidad);
            }
        }
        return $premiosCondecidos;
    }
}