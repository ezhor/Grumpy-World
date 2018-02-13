package com.arensis_games.grumpyworld.Conexion;

/**
 * Created by ezhor on 12/02/2018.
 */

public class GestoraToken {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GestoraToken.token = token;
    }
}
