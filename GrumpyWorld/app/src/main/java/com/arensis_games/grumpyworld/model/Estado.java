package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public class Estado {
    private int vidaRollo;
    private int vidaEnemigo;
    private byte ataqueRollo;
    private byte ataqueEnemigo;

    public Estado(int vidaRollo, int vidaEnemigo, byte ataqueRollo, byte ataqueEnemigo) {
        this.vidaRollo = vidaRollo;
        this.vidaEnemigo = vidaEnemigo;
        this.ataqueRollo = ataqueRollo;
        this.ataqueEnemigo = ataqueEnemigo;
    }

    public int getVidaRollo() {
        return vidaRollo;
    }

    public void setVidaRollo(int vidaRollo) {
        this.vidaRollo = vidaRollo;
    }

    public int getVidaEnemigo() {
        return vidaEnemigo;
    }

    public void setVidaEnemigo(int vidaEnemigo) {
        this.vidaEnemigo = vidaEnemigo;
    }

    public byte getAtaqueRollo() {
        return ataqueRollo;
    }

    public void setAtaqueRollo(byte ataqueRollo) {
        this.ataqueRollo = ataqueRollo;
    }

    public byte getAtaqueEnemigo() {
        return ataqueEnemigo;
    }

    public void setAtaqueEnemigo(byte ataqueEnemigo) {
        this.ataqueEnemigo = ataqueEnemigo;
    }
}
