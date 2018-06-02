package com.arensis_games.grumpyworld.model;

public class Amigo {
    private int id;
    private String nombre;
    private int rango;
    private int honor;
    private int nivel;

    public Amigo(int id, String nombre, int rango, int honor, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.rango = rango;
        this.honor = honor;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRango() {
        return rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }

    public int getHonor() {
        return honor;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
