package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Atributos;
import com.arensis_games.grumpyworld.model.Entrenamiento;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by dparrado on 15/02/18.
 */

public interface AtributosInterface {
    @GET("/atributos")
    Call<Atributos> getAtributos();

    @POST("/atributos")
    @Headers("Content-Type: application/json")
    Call<Atributos> postAtributos(@Body Entrenamiento entrenamiento);
}
