<?php
/**
 * Created by PhpStorm.
 * User: ezhor
 * Date: 24/02/2018
 * Time: 21:31
 */


/*
    INFO: ESTA CLASE ES UNA VERSIÓN SIMPLIFICADA DEL ENEMIGO.
    SE USARÁ CUANDO SE ENVÍEN LOS DATOS DEL ENEMIGO AL JUGADOR.
    NO INTERESA QUE EL JUGADOR CONOZCA TODOS LOS DATOS DEL ENEMIGO, SOLO QUIÉN ES, SU NIVEL Y SI ES JEFE O NO.
    LA PROPIEDAD esMasRapido LA USARÁ LA GUI DE LA CAZA Y EL JUGADOR LA SABRÁ DE FORMA IMPLÍCITA.
 */
class EnemigoSimplificadoModel implements JsonSerializable
{
    private $nombre;
    private $nivel;
    private $esJefe;
    private $esMasRapido;

    /**
     * EnemigoSimplificadoModel constructor.
     * @param $nombre
     * @param $nivel
     * @param $esJefe
     * @param $esMasRapido
     */
    public function __construct($nombre, $nivel, $esJefe, $esMasRapido)
    {
        $this->nombre = $nombre;
        $this->nivel = $nivel;
        $this->esJefe = $esJefe;
        $this->esMasRapido = $esMasRapido;
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
     * @return mixed
     */
    public function getNivel()
    {
        return $this->nivel;
    }

    /**
     * @param mixed $nivel
     */
    public function setNivel($nivel)
    {
        $this->nivel = $nivel;
    }

    /**
     * @return mixed
     */
    public function getEsJefe()
    {
        return $this->esJefe;
    }

    /**
     * @param mixed $esJefe
     */
    public function setEsJefe($esJefe)
    {
        $this->esJefe = $esJefe;
    }

    /**
     * @return mixed
     */
    public function getEsMasRapido()
    {
        return $this->esMasRapido;
    }

    /**
     * @param mixed $esMasRapido
     */
    public function setEsMasRapido($esMasRapido)
    {
        $this->esMasRapido = $esMasRapido;
    }



    public function jsonSerialize()
    {
        return array(
            'nombre' => $this->nombre,
            'nivel' => $this->nivel,
            'jefe' => $this->esJefe,
            'masRapido' => $this->esMasRapido
        );
    }
}