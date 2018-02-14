package com.arensis_games.grumpyworld.Conexion;

import com.arensis_games.grumpyworld.Models.Rollo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dparrado on 31/01/18.
 */

public interface UsuarioInterface {
    @POST("/usuario")
    Call<Rollo> postUsuario(@Body Authentication authentication);
}
