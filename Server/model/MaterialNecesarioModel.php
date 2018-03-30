<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 30/03/2018
 * Time: 14:40
 */

class MaterialNecesarioModel implements JsonSerializable
{
    private $id;
    private $nombre;
    private $cantidadActual;
    private $cantidadNecesaria;

    /**
     * MaterialNecesarioModel constructor.
     * @param $id
     * @param $nombre
     * @param $cantidadActual
     * @param $cantidadNecesaria
     */
    public function __construct($id, $nombre, $cantidadActual, $cantidadNecesaria)
    {
        $this->id = $id;
        $this->nombre = $nombre;
        $this->cantidadActual = $cantidadActual;
        $this->cantidadNecesaria = $cantidadNecesaria;
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
    public function getCantidadActual()
    {
        return $this->cantidadActual;
    }

    /**
     * @param mixed $cantidadActual
     */
    public function setCantidadActual($cantidadActual)
    {
        $this->cantidadActual = $cantidadActual;
    }

    /**
     * @return mixed
     */
    public function getCantidadNecesaria()
    {
        return $this->cantidadNecesaria;
    }

    /**
     * @param mixed $cantidadNecesaria
     */
    public function setCantidadNecesaria($cantidadNecesaria)
    {
        $this->cantidadNecesaria = $cantidadNecesaria;
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
            'id' => $this->id,
            'nombre' => $this->nombre,
            'cantidadActual' => $this->cantidadActual,
            'cantidadNecesaria' => $this->cantidadNecesaria
        );
    }
}