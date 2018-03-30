package com.arensis_games.grumpyworld.Models;

import java.io.Serializable;

/**
 * Created by ezhor on 24/03/2018.
 */

public class Material implements Serializable{
    private int id;
    private String nombre;
    private int cantidad;

    public Material(int id, String nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
