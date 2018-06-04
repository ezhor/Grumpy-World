package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.DueloInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.Duelo;
import com.arensis_games.grumpyworld.model.EstadoDuelo;
import com.arensis_games.grumpyworld.model.LobbyDuelo;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 15/02/18.
 */

public class DueloFragmentVM extends AndroidViewModel {
    private MutableLiveData<LobbyDuelo> ldLobbyDuelo;
    private MutableLiveData<Duelo> ldDuelo;
    private MutableLiveData<EstadoDuelo> ldEstado;
    private MutableLiveData<String> ldError;

    public DueloFragmentVM(@NonNull Application application) {
        super(application);
        ldLobbyDuelo = new MutableLiveData<>();
        ldDuelo = new MutableLiveData<>();
        ldEstado = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
    }

    public MutableLiveData<LobbyDuelo> getLdLobbyDuelo() {
        return ldLobbyDuelo;
    }

    public MutableLiveData<Duelo> getLdDuelo() {
        return ldDuelo;
    }

    public MutableLiveData<EstadoDuelo> getLdEstado() {
        return ldEstado;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }

    public void obtenerLobbyDuelo(){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.getLobbyDuelo().enqueue(new Callback<LobbyDuelo>() {
                @Override
                public void onResponse(Call<LobbyDuelo> call, Response<LobbyDuelo> response) {
                    if(response.isSuccessful()){
                        ldLobbyDuelo.postValue(response.body());
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
                public void onFailure(Call<LobbyDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void retarADuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.retarADuelo(id).enqueue(new Callback<LobbyDuelo>() {
                @Override
                public void onResponse(Call<LobbyDuelo> call, Response<LobbyDuelo> response) {
                    if(response.isSuccessful()){
                        ldLobbyDuelo.postValue(response.body());
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
                public void onFailure(Call<LobbyDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void rechazarDuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.rechazarDuelo(id).enqueue(new Callback<LobbyDuelo>() {
                @Override
                public void onResponse(Call<LobbyDuelo> call, Response<LobbyDuelo> response) {
                    if(response.isSuccessful()){
                        ldLobbyDuelo.postValue(response.body());
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
                public void onFailure(Call<LobbyDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void obtenerDuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.getDuelo(id).enqueue(new Callback<Duelo>() {
                @Override
                public void onResponse(Call<Duelo> call, Response<Duelo> response) {
                    if(response.isSuccessful()){
                        ldDuelo.postValue(response.body());
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
                public void onFailure(Call<Duelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }
}