<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 07/04/2018
 * Time: 14:10
 */

class SupermaterialModel implements JsonSerializable
{
    private $id;
    private $nombre;
    private $cantidad;
    private $materialesNecesarios;

    /**
     * SupermaterialModel constructor.
     * @param $id
     * @param $nombre
     * @param $cantidad
     * @param $submateriales
     */
    public function __construct($id, $nombre, $cantidad, $submateriales)
    {
        $this->id = $id;
        $this->nombre = $nombre;
        $this->cantidad = $cantidad;
        $this->materialesNecesarios = $submateriales;
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
    public function getCantidad()
    {
        return $this->cantidad;
    }

    /**
     * @param mixed $cantidad
     */
    public function setCantidad($cantidad)
    {
        $this->cantidad = $cantidad;
    }

    /**
     * @return mixed
     */
    public function getMaterialesNecesarios()
    {
        return $this->materialesNecesarios;
    }

    /**
     * @param mixed $materialesNecesarios
     */
    public function setMaterialesNecesarios($materialesNecesarios)
    {
        $this->materialesNecesarios = $materialesNecesarios;
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
            'cantidad' => $this->cantidad,
            'materialesNecesarios' => $this->materialesNecesarios
        );
    }
}