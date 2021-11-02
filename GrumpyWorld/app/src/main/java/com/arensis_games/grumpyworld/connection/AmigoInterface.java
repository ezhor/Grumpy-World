package com.arensis_games.grumpyworld.connection;

import com.arensis_games.grumpyworld.model.Amigo;
import com.arensis_games.grumpyworld.model.ListadoAmigosCompleto;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dparrado on 15/02/18.
 */

public interface AmigoInterface {
    @GET("amigo")
    Call<ListadoAmigosCompleto> getListadoAmigosCompleto();

    @GET("amigo")
    Call<Amigo[]> buscarUsuario(@Query("nombre") String nombre);

    @POST("amigo/{id}")
    Call<ListadoAmigosCompleto> agregarAmigo(@Path("id") int id);

    @DELETE("amigo/{id}")
    Call<ListadoAmigosCompleto> borrarAmigo(@Path("id") int id);
}
