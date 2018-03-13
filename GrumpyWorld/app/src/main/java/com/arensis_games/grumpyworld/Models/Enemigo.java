package com.arensis_games.grumpyworld.Models;

/**
 * Created by ezhor on 12/03/2018.
 */

public class Enemigo {
    private String nombre;
    private int nivel;
    private boolean jefe;
    private boolean masRapido;

    public Enemigo(String nombre, int nivel, boolean jefe, boolean masRapido) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.jefe = jefe;
        this.masRapido = masRapido;
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

    public boolean isJefe() {
        return jefe;
    }

    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }

    public boolean isMasRapido() {
        return masRapido;
    }

    public void setMasRapido(boolean masRapido) {
        this.masRapido = masRapido;
    }
}
