package com.arensis_games.grumpyworld.model;

public class Duelo {
    private Integer enDueloCon;
    private Amigo[] retosDuelo;
    private Amigo[] amigosRanked;
    private Amigo[] amigosAmistoso;

    public Duelo() {
    }

    public Duelo(Integer enDueloCon, Amigo[] retosDuelo, Amigo[] amigosRanked, Amigo[] amigosAmistoso) {
        this.enDueloCon = enDueloCon;
        this.retosDuelo = retosDuelo;
        this.amigosRanked = amigosRanked;
        this.amigosAmistoso = amigosAmistoso;
    }

    public Integer getEnDueloCon() {
        return enDueloCon;
    }

    public void setEnDueloCon(Integer enDueloCon) {
        this.enDueloCon = enDueloCon;
    }

    public Amigo[] getRetosDuelo() {
        return retosDuelo;
    }

    public void setRetosDuelo(Amigo[] retosDuelo) {
        this.retosDuelo = retosDuelo;
    }

    public Amigo[] getAmigosRanked() {
        return amigosRanked;
    }

    public void setAmigosRanked(Amigo[] amigosRanked) {
        this.amigosRanked = amigosRanked;
    }

    public Amigo[] getAmigosAmistoso() {
        return amigosAmistoso;
    }

    public void setAmigosAmistoso(Amigo[] amigosAmistoso) {
        this.amigosAmistoso = amigosAmistoso;
    }
}
