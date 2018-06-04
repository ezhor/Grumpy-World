package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.CazaInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.Caza;
import com.arensis_games.grumpyworld.model.EstadoCaza;
import com.arensis_games.grumpyworld.model.Turno;
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

public class CazaFragmentVM extends AndroidViewModel {
    private MutableLiveData<Caza> ldCaza;
    private MutableLiveData<String> ldError;
    private MutableLiveData<EstadoCaza> ldEstado;

    public CazaFragmentVM(@NonNull Application application) {
        super(application);
        this.ldCaza = new MutableLiveData<>();
        this.ldError = new MutableLiveData<>();
        this.ldEstado = new MutableLiveData<>();
    }

    public MutableLiveData<Caza> getLdCaza() {
        return ldCaza;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }

    public MutableLiveData<EstadoCaza> getLdEstado() {
        return ldEstado;
    }

    public void obtenerCaza(){
        OkHttpClient client;
        Retrofit retrofit;
        CazaInterface cazaInterface;

        /*
            Android puede haber borrado el dato estático si necesita memoria.
            En ese caso se manda al usuario a la pantalla de inicio para que
            el sistema inicie sesión de nuevo.
        */
        if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            cazaInterface = retrofit.create(CazaInterface.class);

            cazaInterface.getCaza().enqueue(new Callback<Caza>() {
                @Override
                public void onResponse(Call<Caza> call, Response<Caza> response) {
                    if(response.isSuccessful()){
                        ldCaza.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        /*
                            Puede haber pasado una hora desde que el usuario uso la app por última
                            vez y que Android aún no haya borrado el token de memoria por lo que
                            dejaría de ser válido (401 Unauthorized)
                            En ese caso se manda al usuario a la pantalla de inicio para que
                            el sistema inicie sesión de nuevo.
                         */
                        ldError.setValue(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<Caza> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void jugarTurno(Turno turno){
        OkHttpClient client;
        Retrofit retrofit;
        CazaInterface cazaInterface;

        if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            cazaInterface = retrofit.create(CazaInterface.class);

            cazaInterface.postTurno(turno).enqueue(new Callback<EstadoCaza>() {
                @Override
                public void onResponse(Call<EstadoCaza> call, Response<EstadoCaza> response) {
                    if(response.isSuccessful()){
                        ldEstado.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        ldError.setValue(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<EstadoCaza> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }
}
