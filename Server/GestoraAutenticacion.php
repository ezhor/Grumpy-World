<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 23/01/2018
 * Time: 20:34
 */

//include_once 'model/DatabaseModel.php';

class GestoraAutenticacion
{
    public function comprobarAutenticacion(Autenticacion $autenticacion){
        $resultado = false;
        $usuario = $autenticacion->getUsuario();
        $contrasena = $autenticacion->getContrasena();
        if(isset($usuario) && isset($contrasena)){
            $db = DatabaseModel::getInstance();
            $db_connection = $db->getConnection();

            $query = "SELECT Contrasena FROM Usuarios WHERE Usuario = ?";
            $prep_query = $db_connection->prepare($query);
            $prep_query->bind_param('s', $usuario);
            $prep_query->bind_result($contrasena_hash);
            $prep_query->execute();
            $prep_query->fetch();

            if(password_verify($contrasena, $contrasena_hash)){
                $resultado = true;
            }

        }
        return $resultado;
    }
}