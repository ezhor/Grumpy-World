package com.arensis_games.grumpyworld.Models;

/**
 * Created by dparrado on 15/02/18.
 */

public class Atributos {
    private int fuerza;
    private int constitucion;
    private int destreza;
    private int finEntrenamiento;

    public Atributos() {
    }

    public Atributos(int fuerza, int constitucion, int destreza, int finEntrenamiento) {
        this.fuerza = fuerza;
        this.constitucion = constitucion;
        this.destreza = destreza;
        this.finEntrenamiento = finEntrenamiento;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getConstitucion() {
        return constitucion;
    }

    public void setConstitucion(int constitucion) {
        this.constitucion = constitucion;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getFinEntrenamiento() {
        return finEntrenamiento;
    }

    public void setFinEntrenamiento(int finEntrenamiento) {
        this.finEntrenamiento = finEntrenamiento;
    }
}
