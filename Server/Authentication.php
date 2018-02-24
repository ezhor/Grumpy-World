<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 23/01/2018
 * Time: 19:58
 */

class Authentication
{
    private $id;
    private $usuario;
    private $contrasena;
    private $token;

    /**
     * Authentication constructor.
     * @param $id
     * @param $usuario
     * @param $contrasena
     * @param $token
     */
    public function __construct($id, $usuario, $contrasena, $token)
    {
        $this->id = $id;
        $this->usuario = $usuario;
        $this->contrasena = $contrasena;
        $this->token = $token;
    }

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }




    /**
     * @return mixed
     */
    public function getUsuario()
    {
        return $this->usuario;
    }

    /**
     * @param mixed $usuario
     */
    public function setUsuario($usuario)
    {
        $this->usuario = $usuario;
    }

    /**
     * @return mixed
     */
    public function getContrasena()
    {
        return $this->contrasena;
    }

    /**
     * @param mixed $contrasena
     */
    public function setContrasena($contrasena)
    {
        $this->contrasena = $contrasena;
    }

    /**
     * @return mixed
     */
    public function getToken()
    {
        return $this->token;
    }

    /**
     * @param mixed $token
     */
    public function setToken($token)
    {
        $this->token = $token;
    }


}