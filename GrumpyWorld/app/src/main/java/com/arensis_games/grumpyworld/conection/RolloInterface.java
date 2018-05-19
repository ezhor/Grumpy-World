package com.arensis_games.grumpyworld.conection;

import com.arensis_games.grumpyworld.model.Rollo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dparrado on 31/01/18.
 */

public interface RolloInterface {
    @GET("/rollo")
    Call<Rollo> getRollo();
}
