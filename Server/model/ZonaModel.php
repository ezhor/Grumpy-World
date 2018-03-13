<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 13/03/2018
 * Time: 17:50
 */

class ZonaModel implements JsonSerializable
{
    private $nombre;
    private $nivel;

    /**
     * ZonaModel constructor.
     * @param $nombre
     * @param $nivel
     */
    public function __construct($nombre, $nivel)
    {
        $this->nombre = $nombre;
        $this->nivel = $nivel;
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
    public function getNivel()
    {
        return $this->nivel;
    }

    /**
     * @param mixed $nivel
     */
    public function setNivel($nivel)
    {
        $this->nivel = $nivel;
    }


    /**
     * Specify data which should be serialized to JSON
     * @link http://php.net/manual/en/jsonserializable.jsonserialize.php
     * @return mixed data which can be serialized by <b>json_encode</b>,
     * which is a value of any type other than a resource.
     * @since 5.4.0
     */
    public function jsonSerialize()
    {
        return array(
            'nombre' => $this->nombre,
            'nivel' => $this->nivel
        );
    }
}