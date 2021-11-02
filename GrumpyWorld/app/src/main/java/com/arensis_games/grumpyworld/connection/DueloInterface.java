package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Duelo;
import com.arensis_games.grumpyworld.model.EstadoCaza;
import com.arensis_games.grumpyworld.model.EstadoDuelo;
import com.arensis_games.grumpyworld.model.LobbyDuelo;
import com.arensis_games.grumpyworld.model.Turno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dparrado on 15/02/18.
 */

public interface DueloInterface {
    @GET("duelo")
    Call<LobbyDuelo> getLobbyDuelo();

    @POST("duelo/{id}")
    Call<LobbyDuelo> retarADuelo(@Path("id") int id);

    @DELETE("duelo/{id}")
    Call<LobbyDuelo> rechazarDuelo(@Path("id") int id);

    @GET("duelo/{id}")
    Call<Duelo> getDuelo(@Path("id") int id);

    @GET("duelo/{id}/estado")
    Call<EstadoDuelo> getEstado(@Path("id") int id);

    @GET("duelo/{id}/premio")
    Call<Integer> getPremio(@Path("id") int id);

    @POST("duelo")
    @Headers("Content-Type: application/json")
    Call<EstadoDuelo> elegirTurno(@Body Turno turno);
}
