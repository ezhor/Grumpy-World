package com.arensis_games.grumpyworld.Models;

/**
 * Created by ezhor on 31/03/2018.
 */

public class EquipableDetalle extends Equipable {
    private int destrezaNecesaria;
    private int fuerzaNecesaria;
    private MaterialNecesario[] materialesNecesaio;

    public EquipableDetalle(){

    }

    public EquipableDetalle(int destrezaNecesaria, int fuerzaNecesaria, MaterialNecesario[] materialesNecesaio) {
        this.destrezaNecesaria = destrezaNecesaria;
        this.fuerzaNecesaria = fuerzaNecesaria;
        this.materialesNecesaio = materialesNecesaio;
    }

    public int getDestrezaNecesaria() {
        return destrezaNecesaria;
    }

    public void setDestrezaNecesaria(int destrezaNecesaria) {
        this.destrezaNecesaria = destrezaNecesaria;
    }

    public int getFuerzaNecesaria() {
        return fuerzaNecesaria;
    }

    public void setFuerzaNecesaria(int fuerzaNecesaria) {
        this.fuerzaNecesaria = fuerzaNecesaria;
    }

    public MaterialNecesario[] getMaterialesNecesaio() {
        return materialesNecesaio;
    }

    public void setMaterialesNecesaio(MaterialNecesario[] materialesNecesaio) {
        this.materialesNecesaio = materialesNecesaio;
    }
}
