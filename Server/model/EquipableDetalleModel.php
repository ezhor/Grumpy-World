<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 30/03/2018
 * Time: 14:08
 */

class EquipableDetalleModel extends EquipableModel implements JsonSerializable
{
    private $destrezaNecesaria;
    private $fuerzaNecesaria;
    private $nivelNecesario;
    private $materialesNecesarios;
    private $poseido;

    /**
     * EquipableDetalleModel constructor.
     * @param $nombre
     * @param $tipo
     * @param $bonus
     * @param $destrezaNecesaria
     * @param $fuerzaNecesaria
     * @param $nivelNecesario
     * @param $materialesNecesarios
     */
    public function __construct($id, $nombre, $tipo, $bonus, $destrezaNecesaria, $fuerzaNecesaria, $nivelNecesario, $materialesNecesarios, $poseido)
    {
        parent::__construct($id, $nombre, $tipo, $bonus);
        $this->destrezaNecesaria = $destrezaNecesaria;
        $this->fuerzaNecesaria = $fuerzaNecesaria;
        $this->nivelNecesario = $nivelNecesario;
        $this->materialesNecesarios = $materialesNecesarios;
        $this->poseido = $poseido;
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
    public function getMaterialesNecesarios()
    {
        return $this->materialesNecesarios;
    }

    /**
     * @param mixed $materialesNecesarios
     */
    public function setMaterialesNecesarios($materialesNecesarios)
    {
        $this->materialesNecesarios = $materialesNecesarios;
    }

    /**
     * @return mixed
     */
    public function getposeido()
    {
        return $this->poseido;
    }

    /**
     * @param mixed $poseido
     */
    public function setposeido($poseido)
    {
        $this->poseido = $poseido;
    }



    public function jsonSerialize()
    {
        $array = parent::jsonSerialize();
        $array['destrezaNecesaria'] = $this->destrezaNecesaria;
        $array['fuerzaNecesaria'] = $this->fuerzaNecesaria;
        $array['nivelNecesario'] = $this->nivelNecesario;
        $array['poseido'] = $this->poseido;
        $array['materialesNecesarios'] = $this->materialesNecesarios;
        return $array;
    }
}