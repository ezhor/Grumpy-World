package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public abstract class Estado {
    private int vidaRollo;
    private byte ataqueRollo;

    public Estado(int vidaRollo, byte ataqueRollo) {
        this.vidaRollo = vidaRollo;
        this.ataqueRollo = ataqueRollo;
    }

    public int getVidaRollo() {
        return vidaRollo;
    }

    public void setVidaRollo(int vidaRollo) {
        this.vidaRollo = vidaRollo;
    }

    public byte getAtaqueRollo() {
        return ataqueRollo;
    }

    public void setAtaqueRollo(byte ataqueRollo) {
        this.ataqueRollo = ataqueRollo;
    }
}
