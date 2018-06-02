<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 8:42
 */
class LobbyDueloModel implements JsonSerializable
{
    private $enDueloCon;
    private $retosDuelo;
    private $amigosRanked;
    private $amigosAmistoso;

    /**
     * LobbyDueloModel constructor.
     * @param $enDueloCon
     * @param $retosDuelo
     * @param $amigosRanked
     * @param $amigosAmistoso
     */
    public function __construct($enDueloCon, $retosDuelo, $amigosRanked, $amigosAmistoso)
    {
        $this->enDueloCon = $enDueloCon;
        $this->retosDuelo = $retosDuelo;
        $this->amigosRanked = $amigosRanked;
        $this->amigosAmistoso = $amigosAmistoso;
    }

    /**
     * @return mixed
     */
    public function getEnDueloCon()
    {
        return $this->enDueloCon;
    }

    /**
     * @param mixed $enDueloCon
     */
    public function setEnDueloCon($enDueloCon)
    {
        $this->enDueloCon = $enDueloCon;
    }

    /**
     * @return mixed
     */
    public function getRetosDuelo()
    {
        return $this->retosDuelo;
    }

    /**
     * @param mixed $retosDuelo
     */
    public function setRetosDuelo($retosDuelo)
    {
        $this->retosDuelo = $retosDuelo;
    }

    /**
     * @return mixed
     */
    public function getAmigosRanked()
    {
        return $this->amigosRanked;
    }

    /**
     * @param mixed $amigosRanked
     */
    public function setAmigosRanked($amigosRanked)
    {
        $this->amigosRanked = $amigosRanked;
    }

    /**
     * @return mixed
     */
    public function getAmigosAmistoso()
    {
        return $this->amigosAmistoso;
    }

    /**
     * @param mixed $amigosAmistoso
     */
    public function setAmigosAmistoso($amigosAmistoso)
    {
        $this->amigosAmistoso = $amigosAmistoso;
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
            'enDueloCon' => $this->enDueloCon,
            'retosDuelo' => $this->retosDuelo,
            'amigosRanked' => $this->amigosRanked,
            'amigosAmistoso' => $this->amigosAmistoso
        );
    }
}
