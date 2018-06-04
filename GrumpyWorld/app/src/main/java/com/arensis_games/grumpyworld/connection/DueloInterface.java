package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Duelo;
import com.arensis_games.grumpyworld.model.LobbyDuelo;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dparrado on 15/02/18.
 */

public interface DueloInterface {
    @GET("/duelo")
    Call<LobbyDuelo> getLobbyDuelo();

    @POST("/duelo/{id}")
    Call<LobbyDuelo> retarADuelo(@Path("id") int id);

    @DELETE("/duelo/{id}")
    Call<LobbyDuelo> rechazarDuelo(@Path("id") int id);

    @GET("/duelo/{id}")
    Call<Duelo> getDuelo(@Path("id") int id);

    @GET("/duelo/{id}/estado")
    Call<Duelo> getEstado(@Path("id") int id);
}
