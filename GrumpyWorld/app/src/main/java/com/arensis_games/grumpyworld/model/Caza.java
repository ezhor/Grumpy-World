package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 12/03/2018.
 */

public class Caza {
    private Rollo rollo;
    private Enemigo enemigo;
    private EstadoCaza estado;

    public Caza(Rollo rollo, Enemigo enemigo, EstadoCaza estado) {
        this.rollo = rollo;
        this.enemigo = enemigo;
        this.estado = estado;
    }

    public Rollo getRollo() {
        return rollo;
    }

    public void setRollo(Rollo rollo) {
        this.rollo = rollo;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    public void setEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
    }

    public EstadoCaza getEstado() {
        return estado;
    }

    public void setEstado(EstadoCaza estado) {
        this.estado = estado;
    }
}
