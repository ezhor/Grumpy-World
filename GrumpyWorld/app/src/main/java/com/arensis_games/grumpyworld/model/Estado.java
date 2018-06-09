package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public abstract class Estado {
    private Integer vidaRollo;
    private byte ataqueRollo;

    public Estado(Integer vidaRollo, byte ataqueRollo) {
        this.vidaRollo = vidaRollo;
        this.ataqueRollo = ataqueRollo;
    }

    public Integer getVidaRollo() {
        return vidaRollo;
    }

    public void setVidaRollo(Integer vidaRollo) {
        this.vidaRollo = vidaRollo;
    }

    public byte getAtaqueRollo() {
        return ataqueRollo;
    }

    public void setAtaqueRollo(byte ataqueRollo) {
        this.ataqueRollo = ataqueRollo;
    }
}
