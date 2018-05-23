<?php

/**
 * Created by PhpStorm.
 * User: dparrado
 * Date: 31/01/18
 * Time: 8:42
 */
class RankingModel implements JsonSerializable
{
    private $rolloRanked;
    private $topRanking;

    /**
     * RankingModel constructor.
     * @param $rolloRanked
     * @param $topRanking
     */
    public function __construct($rolloRanked, $topRanking)
    {
        $this->rolloRanked = $rolloRanked;
        $this->topRanking = $topRanking;
    }

    /**
     * @return mixed
     */
    public function getRolloRanked()
    {
        return $this->rolloRanked;
    }

    /**
     * @param mixed $rolloRanked
     */
    public function setRolloRanked($rolloRanked)
    {
        $this->rolloRanked = $rolloRanked;
    }

    /**
     * @return mixed
     */
    public function getTopRanking()
    {
        return $this->topRanking;
    }

    /**
     * @param mixed $topRanking
     */
    public function setTopRanking($topRanking)
    {
        $this->topRanking = $topRanking;
    }


    /**
     * Specify data which should be serialized to JSON
     * @link http://php.net/manual/en/jsonserializable.jsonserialize.php
     * @return mixed data which can be serialized by <b>json_encode</b>,
     * which is a value of any type other than a resource.
     * @since 5.4.0
     */
    function jsonSerialize()
    {
        return array(
            'rolloRanked' => $this->rolloRanked,
            'topRanking' => $this->topRanking
        );
    }
}
