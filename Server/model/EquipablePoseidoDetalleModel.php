<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 30/03/2018
 * Time: 14:08
 */

class EquipablePoseidoDetalleModel extends EquipablePoseidoModel implements JsonSerializable
{
    private $fuerzaNecesaria;
    private $destrezaNecesaria;
    private $nivelNecesario;
    private $equipado;
    private $fuerzaSuficiente;
    private $destrezaSuficiente;

    /**
     * EquipablePoseidoDetalleModel constructor.
     * @param $fuerzaNecesaria
     * @param $destrezaNecesaria
     * @param $nivelNecesario
     * @param $equipado
     * @param $fuerzaSuficiente
     * @param $destrezaSuficiente
     */
    public function __construct($id, $nombre, $tipo, $bonus, $equipado, $fuerzaNecesaria, $destrezaNecesaria, $nivelNecesario, $fuerzaSuficiente, $destrezaSuficiente)
    {
        parent::__construct($id, $nombre, $tipo, $bonus, $equipado);
        $this->fuerzaNecesaria = $fuerzaNecesaria;
        $this->destrezaNecesaria = $destrezaNecesaria;
        $this->nivelNecesario = $nivelNecesario;
        $this->equipado = $equipado;
        $this->fuerzaSuficiente = $fuerzaSuficiente;
        $this->destrezaSuficiente = $destrezaSuficiente;
    }


    /**
     * @return mixed
     */
    public function getFuerzaNecesaria()
    {
        return $this->fuerzaNecesaria;
    }

    /**
     * @param mixed $fuerzaNecesaria
     */
    public function setFuerzaNecesaria($fuerzaNecesaria)
    {
        $this->fuerzaNecesaria = $fuerzaNecesaria;
    }

    /**
     * @return mixed
     */
    public function getDestrezaNecesaria()
    {
        return $this->destrezaNecesaria;
    }

    /**
     * @param mixed $destrezaNecesaria
     */
    public function setDestrezaNecesaria($destrezaNecesaria)
    {
        $this->destrezaNecesaria = $destrezaNecesaria;
    }

    /**
     * @return mixed
     */
    public function getNivelNecesario()
    {
        return $this->nivelNecesario;
    }

    /**
     * @param mixed $nivelNecesario
     */
    public function setNivelNecesario($nivelNecesario)
    {
        $this->nivelNecesario = $nivelNecesario;
    }

    /**
     * @return mixed
     */
    public function getEquipado()
    {
        return $this->equipado;
    }

    /**
     * @param mixed $equipado
     */
    public function setEquipado($equipado)
    {
        $this->equipado = $equipado;
    }

    /**
     * @return mixed
     */
    public function getFuerzaSuficiente()
    {
        return $this->fuerzaSuficiente;
    }

    /**
     * @param mixed $fuerzaSuficiente
     */
    public function setFuerzaSuficiente($fuerzaSuficiente)
    {
        $this->fuerzaSuficiente = $fuerzaSuficiente;
    }

    /**
     * @return mixed
     */
    public function getDestrezaSuficiente()
    {
        return $this->destrezaSuficiente;
    }

    /**
     * @param mixed $destrezaSuficiente
     */
    public function setDestrezaSuficiente($destrezaSuficiente)
    {
        $this->destrezaSuficiente = $destrezaSuficiente;
    }



    public function jsonSerialize()
    {
        $array = parent::jsonSerialize();
        $array['fuerzaNecesaria'] = $this->fuerzaNecesaria;
        $array['destrezaNecesaria'] = $this->destrezaNecesaria;
        $array['nivelNecesario'] = $this->nivelNecesario;
        $array['equipado'] = $this->equipado;
        $array['fuerzaSuficiente'] = $this->fuerzaSuficiente;
        $array['destrezaSuficiente'] = $this->destrezaSuficiente;
        return $array;
    }
}