<?php


class EnemigoModel implements JsonSerializable
{
    private $id;
    private $nombre;
    private $fuerza;
    private $constitucion;
    private $destreza;
    private $zona;

    /**
     * Model constructor.
     * @param $id
     * @param $nombre
     * @param $fuerza
     * @param $constitucion
     * @param $destreza
     * @param $zona
     */
    public function __construct($id, $nombre, $fuerza, $constitucion, $destreza, $zona)
    {
        $this->id = $id;
        $this->nombre = $nombre;
        $this->fuerza = $fuerza;
        $this->constitucion = $constitucion;
        $this->destreza = $destreza;
        $this->zona = $zona;
    }

    /**
     * Specify data which should be serialized to JSON
     * @link http://php.net/manual/en/jsonserializable.jsonserialize.php
     * @return mixed data which can be serialized by <b>json_encode</b>,
     * which is a value of any type other than a resource.
     * @since 5.4.0
     */

    //Needed if the properties of the class are private.
    //Otherwise json_encode will encode blank objects
    function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nombre' => $this->nombre,
            'fuerza' => $this->fuerza,
            'constitucion' => $this->constitucion,
            'destreza' => $this->destreza,
            'zona' => $this->zona
        );
    }

    public function __sleep(){
        return array('id', 'nombre', 'fuerza', 'constitucion', 'destreza', 'zona');
    }
}