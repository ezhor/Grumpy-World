<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 23:03
 */

class CazaModel implements JsonSerializable
{
    private $rollo;
    private $enemigo;
    private $estado;

    /**
     * CazaModel constructor.
     * @param RolloModel $rollo
     * @param EnemigoSimplificadoModel $enemigo
     * @param EstadoModel $estado
     */
    public function __construct(RolloModel $rollo, EnemigoSimplificadoModel $enemigo, EstadoCazaModel $estado)
    {
        $this->rollo = $rollo;
        $this->enemigo = $enemigo;
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
     * @return EnemigoSimplificadoModel
     */
    public function getEnemigo()
    {
        return $this->enemigo;
    }

    /**
     * @param EnemigoSimplificadoModel $enemigo
     */
    public function setEnemigo(EnemigoSimplificadoModel $enemigo)
    {
        $this->enemigo = $enemigo;
    }

    /**
     * @return EstadoModel
     */
    public function getEstado()
    {
        return $this->estado;
    }

    /**
     * @param EstadoModel $estado
     */
    public function setEstado(EstadoModel $estado)
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
            'enemigo' => $this->enemigo->jsonSerialize(),
            'estado' => $this->estado->jsonSerialize()
        );
    }
}