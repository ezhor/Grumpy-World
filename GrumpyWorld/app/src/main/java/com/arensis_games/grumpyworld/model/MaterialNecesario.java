package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 31/03/2018.
 */

public class MaterialNecesario extends Material {
    private int cantidadNecesaria;

    public MaterialNecesario(){

    }

    public MaterialNecesario(int id, String nombre, int cantidad, int cantidadNecesaria) {
        super(id, nombre, cantidad);
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public int getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(int cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }
}
