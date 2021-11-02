package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Ranking;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dparrado on 15/02/18.
 */

public interface RankingInterface {
    @GET("ranking")
    Call<Ranking> getRanking();
}
