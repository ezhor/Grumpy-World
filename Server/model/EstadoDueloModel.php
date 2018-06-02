<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:41
 */

class EstadoDueloModel extends EstadoRolloModel implements JsonSerializable
{
    private $vidaOponente;
    private $ataqueOponente;
    private $tiempoLimiteTurno;

    /**
     * EstadoCazaModel constructor.
     * @param $vidaOponente
     * @param $ataqueOponente
     */
    public function __construct($vidaRollo, $ataqueRollo, $vidaOponente, $ataqueOponente, $tiempoLimiteTurno)
    {
        parent::__construct($vidaRollo, $ataqueRollo);
        $this->vidaOponente = $vidaOponente;
        $this->ataqueOponente = $ataqueOponente;
        $this->tiempoLimiteTurno = $tiempoLimiteTurno;
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
            'vidaOponente' => $this->vidaOponente,
            'ataqueOponente' => $this->ataqueOponente,
            'tiempoLimiteTurno' => $this->tiempoLimiteTurno
        );
    }
}