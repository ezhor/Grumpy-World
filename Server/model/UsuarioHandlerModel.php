<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 29/01/2018
 * Time: 20:01
 */

require_once __DIR__."/../Authentication.php";

class UsuarioHandlerModel
{
    public static function insertarUsuario(Authentication $autenticacion){
        $usuario = $autenticacion->getUsuario();
        $contrasena = password_hash($autenticacion->getContrasena(), PASSWORD_DEFAULT);

        $db = DatabaseModel::getInstance();
        $db_connection = $db->getConnection();

        $query = "CALL crearUsuario(?,?,@conseguido);";
        $prep_query = $db_connection->prepare($query);
        $prep_query->bind_param('ss', $usuario, $contrasena);

        $prep_query->execute();
        //echo $db_connection->errno;

        $select = $db_connection->query('SELECT @conseguido');
        $result = $select->fetch_assoc();
        $conseguido = $result['@conseguido'];


        return $conseguido;
    }
}