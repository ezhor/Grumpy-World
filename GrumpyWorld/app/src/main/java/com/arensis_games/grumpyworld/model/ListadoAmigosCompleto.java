package com.arensis_games.grumpyworld.model;

import java.util.List;

public class ListadoAmigosCompleto {
    private List<Amigo> peticionesAmistad;
    private List<Amigo> amigosMutuos;

    public ListadoAmigosCompleto(List<Amigo> peticionesAmistad, List<Amigo> amigosMutuos) {
        this.peticionesAmistad = peticionesAmistad;
        this.amigosMutuos = amigosMutuos;
    }

    public List<Amigo> getPeticionesAmistad() {
        return peticionesAmistad;
    }

    public void setPeticionesAmistad(List<Amigo> peticionesAmistad) {
        this.peticionesAmistad = peticionesAmistad;
    }

    public List<Amigo> getAmigosMutuos() {
        return amigosMutuos;
    }

    public void setAmigosMutuos(List<Amigo> amigosMutuos) {
        this.amigosMutuos = amigosMutuos;
    }
}
