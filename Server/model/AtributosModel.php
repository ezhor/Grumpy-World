<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 15/02/18
 * Time: 10:36
 */
class AtributosModel implements JsonSerializable
{
    private $fuerza;
    private $constitucion;
    private $destreza;
    private $finEntrenamiento;

    /**
     * EntrenamientoModel constructor.
     * @param $fuerza
     * @param $constitucion
     * @param $destreza
     * @param $finEntrenamiento
     */
    public function __construct($fuerza, $constitucion, $destreza, $finEntrenamiento)
    {
        $this->fuerza = $fuerza;
        $this->constitucion = $constitucion;
        $this->destreza = $destreza;
        $this->finEntrenamiento = $finEntrenamiento;
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
            'fuerza' => $this->fuerza,
            'constitucion' => $this->constitucion,
            'destreza' => $this->destreza,
            'finEntrenamiento' => $this->finEntrenamiento
        );
    }
}