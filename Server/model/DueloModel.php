<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 23:03
 */

class DueloModel implements JsonSerializable
{
    private $rollo;
    private $oponente;
    private $estado;

    /**
     * DueloModel constructor.
     * @param RolloModel $rollo
     * @param RolloModel $oponente
     * @param EstadoDueloModel $estado
     */
    public function __construct(RolloModel $rollo, RolloModel $oponente, EstadoDueloModel $estado)
    {
        $this->rollo = $rollo;
        $this->oponente = $oponente;
        $this->estado = $estado;
    }


    /**
     * @return RolloModel
     */
    public function getRollo()
    {
        return $this->rollo;
    }

    /**
     * @param RolloModel $rollo
     */
    public function setRollo(RolloModel $rollo)
    {
        $this->rollo = $rollo;
    }

    /**
     * @return RolloModel
     */
    public function getOponente()
    {
        return $this->oponente;
    }

    /**
     * @param RolloModel $oponente
     */
    public function setOponente(RolloModel $oponente)
    {
        $this->oponente = $oponente;
    }

    /**
     * @return EstadoDueloModel
     */
    public function getEstado()
    {
        return $this->estado;
    }

    /**
     * @param EstadoDueloModel $estado
     */
    public function setEstado(EstadoDueloModel $estado)
    {
        $this->estado = $estado;
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
            'rollo' => $this->rollo->jsonSerialize(),
            'oponente' => $this->oponente->jsonSerialize(),
            'estado' => $this->estado->jsonSerialize()
        );
    }
}