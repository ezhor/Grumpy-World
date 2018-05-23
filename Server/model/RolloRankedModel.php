<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 8:42
 */
class RolloRankedModel implements JsonSerializable
{
    private $nombre;
    private $rango;
    private $honor;

    /**
     * RolloModel constructor.
     * @param $nombre
     * @param $rango
     * @param $honor
     */
    public function __construct($nombre, $rango, $honor)
    {
        $this->nombre = $nombre;
        $this->rango = $rango;
        $this->honor = $honor;
    }

    /**
     * @return mixed
     */
    public function getNombre()
    {
        return $this->nombre;
    }

    /**
     * @param mixed $nombre
     */
    public function setNombre($nombre)
    {
        $this->nombre = $nombre;
    }

    /**
     * @return mixed
     */
    public function getRango()
    {
        return $this->rango;
    }

    /**
     * @param mixed $rango
     */
    public function setRango($rango)
    {
        $this->rango = $rango;
    }

    /**
     * @return mixed
     */
    public function getHonor()
    {
        return $this->honor;
    }

    /**
     * @param mixed $honor
     */
    public function setHonor($honor)
    {
        $this->honor = $honor;
    }

    

    /**
     * Specify data which should be serialized to JSON
     * @link http://php.net/manual/en/jsonserializable.jsonserialize.php
     * @return mixed data which can be serialized by <b>json_encode</b>,
     * which is a value of any type other than a resource.
     * @since 5.4.0
     */
    function jsonSerialize()
    {
        return array(
            'nombre' => $this->nombre,
            'rango' => $this->rango,
            'honor' => $this->honor
        );
    }
}
