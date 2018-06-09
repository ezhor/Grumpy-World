package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public class EstadoCaza extends Estado{
    private Integer vidaEnemigo;
    private byte ataqueEnemigo;

    public EstadoCaza(Integer vidaRollo, byte ataqueRollo, Integer vidaEnemigo, byte ataqueEnemigo) {
        super(vidaRollo, ataqueRollo);
        this.vidaEnemigo = vidaEnemigo;
        this.ataqueEnemigo = ataqueEnemigo;
    }

    public Integer getVidaEnemigo() {
        return vidaEnemigo;
    }

    public void setVidaEnemigo(Integer vidaEnemigo) {
        this.vidaEnemigo = vidaEnemigo;
    }

    public byte getAtaqueEnemigo() {
        return ataqueEnemigo;
    }

    public void setAtaqueEnemigo(byte ataqueEnemigo) {
        this.ataqueEnemigo = ataqueEnemigo;
    }
}
