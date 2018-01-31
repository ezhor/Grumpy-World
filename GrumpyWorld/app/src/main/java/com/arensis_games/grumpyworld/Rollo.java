package com.arensis_games.grumpyworld;

/**
 * Created by dparrado on 31/01/18.
 */

public class Rollo {
    private String nombre;
    private String sombrero;
    private String arma;
    private String zona;

    public Rollo(){

    }

    public Rollo(String nombre, String sombrero, String arma, String zona) {
        this.nombre = nombre;
        this.sombrero = sombrero;
        this.arma = arma;
        this.zona = zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSombrero() {
        return sombrero;
    }

    public void setSombrero(String sombrero) {
        this.sombrero = sombrero;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
