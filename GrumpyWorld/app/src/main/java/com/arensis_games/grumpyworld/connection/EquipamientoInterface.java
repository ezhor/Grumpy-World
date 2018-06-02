package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.EquipablePoseido;
import com.arensis_games.grumpyworld.model.EquipablePoseidoDetalle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dparrado on 15/02/18.
 */

public interface EquipamientoInterface {
    @GET("/equipamiento")
    Call<EquipablePoseido[]> getEquipablesPoseidos();

    @GET("/equipamiento/{id}")
    Call<EquipablePoseidoDetalle> getEquipablePoseidoDetalle(@Path("id") int id);

    @POST("/equipamiento/{id}")
    Call<Void> equiparEquipablePoseido(@Path("id") int id);
}
