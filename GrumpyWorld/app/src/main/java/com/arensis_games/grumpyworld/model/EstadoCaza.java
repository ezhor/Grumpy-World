package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public class EstadoCaza extends Estado{
    private int vidaEnemigo;
    private byte ataqueEnemigo;

    public EstadoCaza(int vidaRollo, byte ataqueRollo, int vidaEnemigo, byte ataqueEnemigo) {
        super(vidaRollo, ataqueRollo);
        this.vidaEnemigo = vidaEnemigo;
        this.ataqueEnemigo = ataqueEnemigo;
    }

    public int getVidaEnemigo() {
        return vidaEnemigo;
    }

    public void setVidaEnemigo(int vidaEnemigo) {
        this.vidaEnemigo = vidaEnemigo;
    }

    public byte getAtaqueEnemigo() {
        return ataqueEnemigo;
    }

    public void setAtaqueEnemigo(byte ataqueEnemigo) {
        this.ataqueEnemigo = ataqueEnemigo;
    }
}
