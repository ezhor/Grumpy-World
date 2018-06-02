package com.arensis_games.grumpyworld.connection;

/**
 * Created by ezhor on 12/02/2018.
 */

public class GestoraToken {
    private static String authorization;

    public static String getAuthorization() {
        return authorization;
    }

    public static void setAuthorization(String authorization) {
        GestoraToken.authorization = authorization;
    }
}
