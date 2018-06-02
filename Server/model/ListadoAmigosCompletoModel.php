<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 8:42
 */
class ListadoAmigosCompletoModel implements JsonSerializable
{
    private $peticionesAmistad;
    private $amigosMutuos;

    /**
     * ListadoAmigosCompletoModel constructor.
     * @param $peticionesAmistad
     * @param $amigosMutuos
     */
    public function __construct($peticionesAmistad, $amigosMutuos)
    {
        $this->peticionesAmistad = $peticionesAmistad;
        $this->amigosMutuos = $amigosMutuos;
    }

    /**
     * @return mixed
     */
    public function getPeticionesAmistad()
    {
        return $this->peticionesAmistad;
    }

    /**
     * @param mixed $peticionesAmistad
     */
    public function setPeticionesAmistad($peticionesAmistad)
    {
        $this->peticionesAmistad = $peticionesAmistad;
    }

    /**
     * @return mixed
     */
    public function getAmigosMutuos()
    {
        return $this->amigosMutuos;
    }

    /**
     * @param mixed $amigosMutuos
     */
    public function setAmigosMutuos($amigosMutuos)
    {
        $this->amigosMutuos = $amigosMutuos;
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
            'peticionesAmistad' => $this->peticionesAmistad,
            'amigosMutuos' => $this->amigosMutuos
        );
    }
}
