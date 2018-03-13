package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.AtributosInterface;
import com.arensis_games.grumpyworld.Conexion.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.CazaInterface;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.Models.Caza;
import com.arensis_games.grumpyworld.Models.Entrenamiento;
import com.arensis_games.grumpyworld.Models.Estado;
import com.arensis_games.grumpyworld.Models.Turno;
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
    private MutableLiveData<Integer> ldError;
    private MutableLiveData<Estado> ldEstado;

    public CazaFragmentVM(@NonNull Application application) {
        super(application);
        this.ldCaza = new MutableLiveData<>();
        this.ldError = new MutableLiveData<>();
        this.ldEstado = new MutableLiveData<>();
    }

    public MutableLiveData<Caza> getLdCaza() {
        return ldCaza;
    }

    public MutableLiveData<Integer> getLdError() {
        return ldError;
    }

    public MutableLiveData<Estado> getLdEstado() {
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
                    //.addInterceptor(new BasicAuthInterceptor("dani", "hola"))
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
                        ldError.setValue(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Caza> call, Throwable t) {

                }
            });
        }else{
            ldError.setValue(401);
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

            cazaInterface.postTurno(turno).enqueue(new Callback<Estado>() {
                @Override
                public void onResponse(Call<Estado> call, Response<Estado> response) {
                    if(response.isSuccessful()){
                        ldEstado.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        ldError.setValue(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Estado> call, Throwable t) {

                }
            });
        }else{
            ldError.setValue(401);
        }
    }
}
