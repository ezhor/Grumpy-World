package com.arensis_games.grumpyworld.model;

/**
 * Created by ezhor on 31/03/2018.
 */

public class EquipableDetalle extends Equipable {
    private int destrezaNecesaria;
    private int fuerzaNecesaria;
    private boolean poseido;
    private MaterialNecesario[] materialesNecesarios;

    public EquipableDetalle(){

    }

    public EquipableDetalle(int destrezaNecesaria, int fuerzaNecesaria, MaterialNecesario[] materialesNecesarios) {
        this.destrezaNecesaria = destrezaNecesaria;
        this.fuerzaNecesaria = fuerzaNecesaria;
        this.materialesNecesarios = materialesNecesarios;
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

    public boolean isPoseido() {
        return poseido;
    }

    public void setPoseido(boolean poseido) {
        this.poseido = poseido;
    }

    public MaterialNecesario[] getMaterialesNecesarios() {
        return materialesNecesarios;
    }

    public void setMaterialesNecesarios(MaterialNecesario[] materialesNecesarios) {
        this.materialesNecesarios = materialesNecesarios;
    }
}
