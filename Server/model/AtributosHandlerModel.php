<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 9:02
 */

require_once "RolloModel.php";

class AtributosHandlerModel
{
    public static function getAtributos($usuario){
        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "SELECT Fuerza, Constitucion, Destreza, FinEntrenamiento
                    FROM Usuarios
                      INNER JOIN Rollos
                      ON Usuarios.ID = Rollos.ID_Usuario
                      INNER JOIN Atributos
                      ON Atributos.ID = Rollos.ID_Atributos
                    WHERE Usuario = ?;";

        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('s', $usuario);
        $prep_query->bind_result($fuerza, $constitucion, $destreza, $finEntrenamiento);
        $prep_query->execute();
        $prep_query->fetch();

        $rollo = new AtributosModel($fuerza, $constitucion, $destreza, $finEntrenamiento);

        return $rollo;
    }

    public static function entrenar($usuario, $atributo){
        $conseguido = false;

        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        //Se comprueba si ha pasado tiempo suficiente para volver a entrenar
        $query ='SELECT FinEntrenamiento FROM Atributos
                                              INNER JOIN Rollos ON Atributos.ID = Rollos.ID_Atributos
                                              INNER JOIN Usuarios ON Rollos.ID_Usuario = Usuarios.ID
                                              WHERE Usuarios.Usuario = ?;';
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('s', $usuario);
        $prep_query->bind_result($finEntrenamiento);
        $prep_query->execute();
        $prep_query->fetch();

        //Si ha pasado tiempo suficiente, se entrena
        if($finEntrenamiento<=time()){
            $prep_query->free_result();
            $query = "CALL entrenar(?,?);";
            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('ss', $usuario, $atributo);

            $prep_query->execute();
            if($db_connection->affected_rows>0){
                $conseguido = true;
            }
        }
        return $conseguido;
    }
}