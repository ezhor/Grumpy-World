package com.arensis_games.grumpyworld.Models;

/**
 * Created by ezhor on 12/03/2018.
 */

public class Turno {
    private byte ataque;

    public Turno(byte ataque) {
        this.ataque = ataque;
    }

    public byte getAtaque() {
        return ataque;
    }

    public void setAtaque(byte ataque) {
        this.ataque = ataque;
    }
}
