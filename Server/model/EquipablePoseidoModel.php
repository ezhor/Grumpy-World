<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 30/03/2018
 * Time: 14:08
 */

class EquipablePoseidoModel implements JsonSerializable
{
    private $id;
    private $nombre;
    private $tipo;
    private $bonus;
    private $equipado;

    /**
     * EquipableModel constructor.
     * @param $id
     * @param $nombre
     * @param $tipo
     * @param $bonus
     */
    public function __construct($id, $nombre, $tipo, $bonus, $equipado)
    {
        $this->id = $id;
        $this->nombre = $nombre;
        $this->tipo = $tipo;
        $this->bonus = $bonus;
        $this->equipado = $equipado;
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
    public function getTipo()
    {
        return $this->tipo;
    }

    /**
     * @param mixed $tipo
     */
    public function setTipo($tipo)
    {
        $this->tipo = $tipo;
    }

    /**
     * @return mixed
     */
    public function getBonus()
    {
        return $this->bonus;
    }

    /**
     * @param mixed $bonus
     */
    public function setBonus($bonus)
    {
        $this->bonus = $bonus;
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
            'tipo' => $this->tipo,
            'bonus' => $this->bonus,
            'equipado' => $this->equipado
        );
    }

    /**
     * @return mixed
     */
    public function getEquipado()
    {
        return $this->equipado;
    }

    /**
     * @param mixed $equipado
     */
    public function setEquipado($equipado)
    {
        $this->equipado = $equipado;
    }
}