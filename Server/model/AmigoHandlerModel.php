<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class AmigoHandlerModel
{
    private static function getAmigosMutuos($id){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $amigos = array();

        $query = "SELECT A1.ID_Usuario2, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Amigos A1
                      INNER JOIN Amigos AS A2
                        ON A1.ID_Usuario1 = A2.ID_Usuario2 AND A1.ID_Usuario2 = A2.ID_Usuario1
                      INNER JOIN Usuarios AS U
                        ON A1.ID_Usuario2 = U.ID
                      INNER JOIN Rollos AS R
                        ON A1.ID_Usuario2 = R.ID_Usuario
                    WHERE A1.ID_Usuario1 = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($id, $nombre, $rango, $honor, $nivel);
        $prep_query->execute();

        while($prep_query->fetch()){
            $amigos[] = new AmigoModel($id, $nombre, $rango, $honor, $nivel);
        }

        return $amigos;
    }

    private static function getPeticionesAmistad($id){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();
        $amigos = array();

        $query = "SELECT A1.ID_Usuario1, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Amigos AS A1
                      INNER JOIN Usuarios AS U
                        ON A1.ID_Usuario1 = U.ID
                      INNER JOIN Rollos AS R
                        ON A1.ID_Usuario1 = R.ID_Usuario
                    WHERE A1.ID_Usuario2 = ? AND
                          A1.ID_Usuario1 NOT IN(SELECT ID_Usuario2 FROM Amigos AS A2 WHERE A2.ID_Usuario1 = A1.ID_Usuario2);";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('i', $id);
        $prep_query->bind_result($id, $nombre, $rango, $honor, $nivel);
        $prep_query->execute();

        while($prep_query->fetch()){
            $amigos[] = new AmigoModel($id, $nombre, $rango, $honor, $nivel);
        }

        return $amigos;
    }

    public static function getListadoAmigosCompleto($id){
        return new ListadoAmigosCompletoModel(self::getPeticionesAmistad($id), self::getAmigosMutuos($id));
    }

    //Se usa el ID del usuario que busca para no mostrarle a sí mismo en la búsqueda
    public static function buscarUsuariosPorNombreParcial($nombreParcial, $idUsuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT U.ID, U.Usuario, rangoRollo(R.Honor, U.ID), R.Honor, R.Nivel
                    FROM Usuarios AS U
                      INNER JOIN Rollos AS R
                        ON U.ID = R.ID_Usuario
                    WHERE Usuario LIKE ? AND U.ID != ?
                    ORDER BY U.Usuario;";

        $amigos = array();
        $prep_query = $db_connection->prepare($query);
        $nombreParcial = "%".$nombreParcial."%";
        $prep_query->bind_param('si', $nombreParcial, $idUsuario);
        $prep_query->bind_result($id, $nombre, $rango, $honor, $nivel);
        $prep_query->execute();

        while($prep_query->fetch()){
            $amigos[] = new AmigoModel($id, $nombre, $rango, $honor, $nivel);
        }

        return $amigos;
    }


    public static function agregarAmigo($idUsuario, $idAmigo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL agregarAmigo(?,?);";
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idUsuario, $idAmigo);
        $prep_query->execute();
    }

    public static function borrarAmigo($idUsuario, $idAmigo){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL borrarAmigo(?,?);";
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ii', $idUsuario, $idAmigo);
        $prep_query->execute();
    }
}