package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public class EstadoDuelo extends Estado{
    private int vidaOponente;
    private byte ataqueOponente;
    private int tiempoLimiteTurno;

    public EstadoDuelo(int vidaRollo, byte ataqueRollo, int vidaOponente, byte ataqueOponente, int tiempoLimiteTurno) {
        super(vidaRollo, ataqueRollo);
        this.vidaOponente = vidaOponente;
        this.ataqueOponente = ataqueOponente;
        this.tiempoLimiteTurno = tiempoLimiteTurno;
    }

    public int getVidaOponente() {
        return vidaOponente;
    }

    public void setVidaOponente(int vidaOponente) {
        this.vidaOponente = vidaOponente;
    }

    public byte getAtaqueOponente() {
        return ataqueOponente;
    }

    public void setAtaqueOponente(byte ataqueOponente) {
        this.ataqueOponente = ataqueOponente;
    }

    public int getTiempoLimiteTurno() {
        return tiempoLimiteTurno;
    }

    public void setTiempoLimiteTurno(int tiempoLimiteTurno) {
        this.tiempoLimiteTurno = tiempoLimiteTurno;
    }
}
