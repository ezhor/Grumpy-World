package com.arensis_games.grumpyworld.Conexion;

import com.arensis_games.grumpyworld.Models.Equipable;
import com.arensis_games.grumpyworld.Models.EquipableDetalle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dparrado on 15/02/18.
 */

public interface FabricacionInterface {
    @GET("/fabricacion")
    Call<Equipable[]> getEquipablesDisponibles();

    @GET("/fabricacion/{id}")
    Call<EquipableDetalle> getEquipableDetalle(@Path("id") int id);

    @POST("/fabricacion/{id}")
    Call<Void> fabricarEquipable(@Path("id") int id);
}
