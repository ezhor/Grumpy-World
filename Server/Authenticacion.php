<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 23/01/2018
 * Time: 19:58
 */

class Authenticacion
{
    private $usuario;
    private $contrasena;
    private $token;

    /**
     * Authenticacion constructor.
     * @param $usuario
     * @param $contrasena
     */
    public function __construct($usuario, $contrasena, $token)
    {
        $this->usuario = $usuario;
        $this->contrasena = $contrasena;
        $this->token = $token;
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