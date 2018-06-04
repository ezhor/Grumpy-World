<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 03/06/2018
 * Time: 17:40
 */

class RolloOponenteModel extends RolloModel implements JsonSerializable
{
    private $masRapido;

    /**
     * RolloOponenteModel constructor.
     * @param $masRapido
     */
    public function __construct($nombre, $sombrero, $arma, $zona, $nivel, $rango, $masRapido)
    {
        parent::__construct($nombre, $sombrero, $arma, $zona, $nivel, $rango);
        $this->masRapido = $masRapido;
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
            'nombre' => $this->getNombre(),
            'sombrero' => $this->getSombrero(),
            'arma' => $this->getArma(),
            'zona' => $this->getZona(),
            'nivel' => $this->getNivel(),
            'rango' => $this->getRango(),
            'masRapido' => $this->masRapido
        );
    }

}