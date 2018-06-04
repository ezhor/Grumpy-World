package com.arensis_games.grumpyworld.model;

public class RolloOponente extends Rollo {
    private boolean masRapido;

    public RolloOponente() {
    }

    public RolloOponente(String nombre, String sombrero, String arma, String zona, int nivel, int rango, boolean masRapido) {
        super(nombre, sombrero, arma, zona, nivel, rango);
        this.masRapido = masRapido;
    }

    public boolean isMasRapido() {
        return masRapido;
    }

    public void setMasRapido(boolean masRapido) {
        this.masRapido = masRapido;
    }
}
