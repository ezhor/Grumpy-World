package com.arensis_games.grumpyworld.model;

public class EquipablePoseidoDetalle extends EquipablePoseido {
    private int fuerzaNecesaria;
    private int destrezaNecesaria;
    private int nivelNecesario;
    private boolean fuerzaSuficiente;
    private boolean destrezaSuficiente;


    public EquipablePoseidoDetalle() {
    }

    public EquipablePoseidoDetalle(int id, String nombre, char tipo, int bonus, boolean equipado, int fuerzaNecesaria, int destrezaNecesaria, int nivelNecesario, boolean fuerzaSuficiente, boolean destrezaSuficiente) {
        super(id, nombre, tipo, bonus, equipado);
        this.fuerzaNecesaria = fuerzaNecesaria;
        this.destrezaNecesaria = destrezaNecesaria;
        this.nivelNecesario = nivelNecesario;
        this.fuerzaSuficiente = fuerzaSuficiente;
        this.destrezaSuficiente = destrezaSuficiente;
    }

    public int getFuerzaNecesaria() {
        return fuerzaNecesaria;
    }

    public void setFuerzaNecesaria(int fuerzaNecesaria) {
        this.fuerzaNecesaria = fuerzaNecesaria;
    }
    
    public int getDestrezaNecesaria() {
        return destrezaNecesaria;
    }

    public void setDestrezaNecesaria(int destrezaNecesaria) {
        this.destrezaNecesaria = destrezaNecesaria;
    }

    public int getNivelNecesario() {
        return nivelNecesario;
    }

    public void setNivelNecesario(int nivelNecesario) {
        this.nivelNecesario = nivelNecesario;
    }

    public boolean isFuerzaSuficiente() {
        return fuerzaSuficiente;
    }

    public void setFuerzaSuficiente(boolean fuerzaSuficiente) {
        this.fuerzaSuficiente = fuerzaSuficiente;
    }

    public boolean isDestrezaSuficiente() {
        return destrezaSuficiente;
    }

    public void setDestrezaSuficiente(boolean destrezaSuficiente) {
        this.destrezaSuficiente = destrezaSuficiente;
    }
}
