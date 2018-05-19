package com.arensis_games.grumpyworld.conection;

import com.arensis_games.grumpyworld.model.Equipable;
import com.arensis_games.grumpyworld.model.EquipableDetalle;
import com.arensis_games.grumpyworld.model.MaterialNecesario;
import com.arensis_games.grumpyworld.model.Supermaterial;

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
    Call<EquipableDetalle> fabricarEquipable(@Path("id") int id);

    @GET("/material/{id}")
    Call<Supermaterial> getSupermaterial(@Path("id") int id);

    @POST("/material/{id}")
    Call<Supermaterial> fabricarSupermaterial(@Path("id") int id);
}
