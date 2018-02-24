<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:41
 */

class EstadoModel implements JsonSerializable
{
    private $vidaRollo;
    private $vidaEnemigo;
    private $ataqueRollo;
    private $ataqueEnemigo;

    /**
     * EstadoModel constructor.
     * @param $vidaRollo
     * @param $vidaEnemigo
     * @param $ataqueRollo
     * @param $ataqueEnemigo
     */
    public function __construct($vidaRollo, $vidaEnemigo, $ataqueRollo, $ataqueEnemigo)
    {
        $this->vidaRollo = $vidaRollo;
        $this->vidaEnemigo = $vidaEnemigo;
        $this->ataqueRollo = $ataqueRollo;
        $this->ataqueEnemigo = $ataqueEnemigo;
    }

    /**
     * @return mixed
     */
    public function getVidaRollo()
    {
        return $this->vidaRollo;
    }

    /**
     * @param mixed $vidaRollo
     */
    public function setVidaRollo($vidaRollo)
    {
        $this->vidaRollo = $vidaRollo;
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
    public function getAtaqueRollo()
    {
        return $this->ataqueRollo;
    }

    /**
     * @param mixed $ataqueRollo
     */
    public function setAtaqueRollo($ataqueRollo)
    {
        $this->ataqueRollo = $ataqueRollo;
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
            'vidaRollo' => $this->vidaRollo,
            'vidaEnemigo' => $this->vidaEnemigo,
            'ataqueRollo' => $this->ataqueRollo,
            'ataqueEnemigo' => $this->ataqueEnemigo
        );
    }
}