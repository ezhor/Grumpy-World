package com.arensis_games.grumpyworld.model;

public class EquipablePoseido {
    private int id;
    private String nombre;
    private char tipo;
    private int bonus;
    private boolean equipado;

    public EquipablePoseido() {
    }

    public EquipablePoseido(int id, String nombre, char tipo, int bonus, boolean equipado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.bonus = bonus;
        this.equipado = equipado;
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

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public boolean isEquipado() {
        return equipado;
    }

    public void setEquipado(boolean equipado) {
        this.equipado = equipado;
    }
}
