<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 8:42
 */
class PremioPosibleModel extends PremioModel
{
    private $probabilidad;

    /**
     * PremioPosibleModel constructor.
     * @param $nombre
     * @param $cantidad
     * @param $probabilidad
     */
    public function __construct($nombre, $cantidad, $probabilidad)
    {
        parent::__construct($nombre, $cantidad);
        $this->probabilidad = $probabilidad;
    }

    /**
     * @return mixed
     */
    public function getProbabilidad()
    {
        return $this->probabilidad;
    }

    /**
     * @param mixed $probabilidad
     */
    public function setProbabilidad($probabilidad)
    {
        $this->probabilidad = $probabilidad;
    }

    function jsonSerialize()
    {
        return array(
            'nombre' => $this->getNombre(),
            'cantidad' => $this->getCantidad(),
            'probabilidad' => $this->probabilidad
        );
    }
}
