<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:31
 */

class EnemigoSimplificadoModel implements JsonSerializable
{
    private $nombre;
    private $esJefe;
    private $esMasRapido;

    /**
     * EnemigoSimplificadoModel constructor.
     * @param $nombre
     * @param $esJefe
     * @param $esMasRapido
     */
    public function __construct($nombre, $esJefe, $esMasRapido)
    {
        $this->nombre = $nombre;
        $this->esJefe = $esJefe;
        $this->esMasRapido = $esMasRapido;
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
    public function getEsJefe()
    {
        return $this->esJefe;
    }

    /**
     * @param mixed $esJefe
     */
    public function setEsJefe($esJefe)
    {
        $this->esJefe = $esJefe;
    }

    /**
     * @return mixed
     */
    public function getEsMasRapido()
    {
        return $this->esMasRapido;
    }

    /**
     * @param mixed $esMasRapido
     */
    public function setEsMasRapido($esMasRapido)
    {
        $this->esMasRapido = $esMasRapido;
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
            'esJefe' => $this->esJefe,
            'esMasRapido' => $this->esMasRapido
        );
    }
}