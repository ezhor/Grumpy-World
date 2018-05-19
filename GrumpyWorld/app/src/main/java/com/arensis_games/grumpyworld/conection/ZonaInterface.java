package com.arensis_games.grumpyworld.conection;

import com.arensis_games.grumpyworld.model.Zona;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by dparrado on 15/02/18.
 */

public interface ZonaInterface {
    @GET("/zona")
    Call<Zona[]> getZonasDisponibles();

    @POST("/zona")
    @Headers("Content-Type: application/json")
    Call<Void> postZona(@Body Zona zona);
}
