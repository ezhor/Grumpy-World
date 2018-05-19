package com.arensis_games.grumpyworld.conection;

/**
 * Created by dparrado on 31/01/18.
 */

public class Authentication {
    private String usuario;
    private String contrasena;

    public Authentication() {
    }

    public Authentication(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
