package com.arensis_games.grumpyworld.model;

public class ListadoAmigosCompleto {
    private Amigo[] peticionesAmistad;
    private Amigo[] amigosMutuos;

    public ListadoAmigosCompleto(Amigo[] peticionesAmistad, Amigo[] amigosMutuos) {
        this.peticionesAmistad = peticionesAmistad;
        this.amigosMutuos = amigosMutuos;
    }

    public Amigo[] getPeticionesAmistad() {
        return peticionesAmistad;
    }

    public void setPeticionesAmistad(Amigo[] peticionesAmistad) {
        this.peticionesAmistad = peticionesAmistad;
    }

    public Amigo[] getAmigosMutuos() {
        return amigosMutuos;
    }

    public void setAmigosMutuos(Amigo[] amigosMutuos) {
        this.amigosMutuos = amigosMutuos;
    }
}
