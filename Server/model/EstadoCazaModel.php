<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:41
 */

class EstadoCazaModel extends EstadoRolloModel implements JsonSerializable
{
    private $vidaEnemigo;
    private $ataqueEnemigo;

    /**
     * EstadoCazaModel constructor.
     * @param $vidaEnemigo
     * @param $ataqueEnemigo
     */
    public function __construct($vidaRollo, $ataqueRollo, $vidaEnemigo, $ataqueEnemigo)
    {
        parent::__construct($vidaRollo, $ataqueRollo);
        $this->vidaEnemigo = $vidaEnemigo;
        $this->ataqueEnemigo = $ataqueEnemigo;
    }

    /**
     * @return mixed
     */
    public function getVidaEnemigo()
    {
        return $this->vidaEnemigo;
    }

    /**
     * @param mixed $vidaEnemigo
     */
    public function setVidaEnemigo($vidaEnemigo)
    {
        $this->vidaEnemigo = $vidaEnemigo;
    }

    /**
     * @return mixed
     */
    public function getAtaqueEnemigo()
    {
        return $this->ataqueEnemigo;
    }

    /**
     * @param mixed $ataqueEnemigo
     */
    public function setAtaqueEnemigo($ataqueEnemigo)
    {
        $this->ataqueEnemigo = $ataqueEnemigo;
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
            'vidaRollo' => $this->getVidaRollo(),
            'ataqueRollo' => $this->getAtaqueRollo(),
            'vidaEnemigo' => $this->vidaEnemigo,
            'ataqueEnemigo' => $this->ataqueEnemigo
        );
    }
}