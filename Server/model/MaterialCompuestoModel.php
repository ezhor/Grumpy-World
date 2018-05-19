<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 07/04/2018
 * Time: 14:10
 */

class MaterialCompuestoModel
{
    private $nombre;
    private $enemigosRelacionados;
    private $materialesNecesarios;

    /**
     * MaterialCompuestoModel constructor.
     * @param $nombre
     * @param $enemigosRelacionados
     * @param $materialesNecesarios
     */
    public function __construct($nombre, $enemigosRelacionados, $materialesNecesarios)
    {
        $this->nombre = $nombre;
        $this->enemigosRelacionados = $enemigosRelacionados;
        $this->materialesNecesarios = $materialesNecesarios;
    }

    /**
     * @return mixed
     */
    public function getNombre()
    {
        return $this->nombre;
    }

    /**
     * @param mixed $nombre
     */
    public function setNombre($nombre)
    {
        $this->nombre = $nombre;
    }

    /**
     * @return array
     */
    public function getEnemigosRelacionados()
    {
        return $this->enemigosRelacionados;
    }

    /**
     * @param array $enemigosRelacionados
     */
    public function setEnemigosRelacionados($enemigosRelacionados)
    {
        $this->enemigosRelacionados = $enemigosRelacionados;
    }

    /**
     * @return array
     */
    public function getMaterialesNecesarios()
    {
        return $this->materialesNecesarios;
    }

    /**
     * @param array $materialesNecesarios
     */
    public function setMaterialesNecesarios($materialesNecesarios)
    {
        $this->materialesNecesarios = $materialesNecesarios;
    }




}