package com.arensis_games.grumpyworld.Conexion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dparrado on 31/01/18.
 */

public interface UsuarioInterface {
    @POST("/usuario")
    Call<Void> postUsuario(@Body Authentication authentication);
}
