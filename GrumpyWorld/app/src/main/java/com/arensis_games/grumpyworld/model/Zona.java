package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 14/03/2018.
 */

public class Zona {
    private String nombre;
    private int nivel;

    public Zona(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
