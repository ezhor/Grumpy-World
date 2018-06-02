<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:41
 */

class EstadoRolloModel implements JsonSerializable
{
    private $vidaRollo;
    private $ataqueRollo;

    /**
     * EstadoModel constructor.
     * @param $vidaRollo
     * @param $vidaEnemigo
     * @param $ataqueRollo
     * @param $ataqueEnemigo
     */
    public function __construct($vidaRollo, $ataqueRollo)
    {
        $this->vidaRollo = $vidaRollo;
        $this->ataqueRollo = $ataqueRollo;
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
            'ataqueRollo' => $this->ataqueRollo
        );
    }
}