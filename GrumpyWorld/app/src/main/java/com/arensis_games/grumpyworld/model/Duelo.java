package com.arensis_games.grumpyworld.model;

public class Duelo {
    private Rollo rollo;
    private RolloOponente oponente;
    private EstadoDuelo estado;

    public Duelo(Rollo rollo, RolloOponente oponente, EstadoDuelo estado) {
        this.rollo = rollo;
        this.oponente = oponente;
        this.estado = estado;
    }

    public Rollo getRollo() {
        return rollo;
    }

    public void setRollo(Rollo rollo) {
        this.rollo = rollo;
    }

    public RolloOponente getOponente() {
        return oponente;
    }

    public void setOponente(RolloOponente oponente) {
        this.oponente = oponente;
    }

    public EstadoDuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoDuelo estado) {
        this.estado = estado;
    }
}
