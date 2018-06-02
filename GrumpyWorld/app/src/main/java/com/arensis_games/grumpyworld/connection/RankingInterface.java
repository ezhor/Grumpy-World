package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Amigo;
import com.arensis_games.grumpyworld.model.ListadoAmigosCompleto;
import com.arensis_games.grumpyworld.model.Ranking;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dparrado on 15/02/18.
 */

public interface RankingInterface {
    @GET("/ranking")
    Call<Ranking> getRanking();
}
