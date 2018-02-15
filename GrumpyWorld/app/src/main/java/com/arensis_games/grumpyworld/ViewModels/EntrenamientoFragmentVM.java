package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.AtributosInterface;
import com.arensis_games.grumpyworld.Conexion.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.R;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 15/02/18.
 */

public class EntrenamientoFragmentVM extends AndroidViewModel {
    MutableLiveData<Atributos> ldAtributos;

    public EntrenamientoFragmentVM(@NonNull Application application) {
        super(application);
        ldAtributos = new MutableLiveData<>();
    }

    public MutableLiveData<Atributos> getLdAtributos() {
        return ldAtributos;
    }

    public void obtenerAtributos(){
        OkHttpClient client;
        Retrofit retrofit;
        AtributosInterface atributosInterface;

        client = new OkHttpClient.Builder()
                .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getApplication().getString(R.string.SERVER_URL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        atributosInterface = retrofit.create(AtributosInterface.class);

        atributosInterface.getAtributos().enqueue(new Callback<Atributos>() {
            @Override
            public void onResponse(Call<Atributos> call, Response<Atributos> response) {
                if(response.isSuccessful()){
                    ldAtributos.postValue(response.body());
                    GestoraToken.setAuthorization(response.headers().get("Authorization"));
                }else{
                    //Gestionar error
                }
            }

            @Override
            public void onFailure(Call<Atributos> call, Throwable t) {

            }
        });
    }
}
