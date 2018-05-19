package com.arensis_games.grumpyworld.conection;

import com.arensis_games.grumpyworld.model.Material;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dparrado on 15/02/18.
 */

public interface PremioInterface {
    @GET("/caza/premio")
    Call<Material[]> getPremio();
}
