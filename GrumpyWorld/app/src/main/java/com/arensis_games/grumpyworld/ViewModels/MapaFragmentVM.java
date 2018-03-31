package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.AtributosInterface;
import com.arensis_games.grumpyworld.Conexion.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Conexion.ZonaInterface;
import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.Models.Entrenamiento;
import com.arensis_games.grumpyworld.Models.Zona;
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

public class MapaFragmentVM extends AndroidViewModel {
    private MutableLiveData<Zona[]> ldZona;
    private MutableLiveData<Integer> ldError;
    private MutableLiveData<Zona> ldZonaCambiada;

    public MapaFragmentVM(@NonNull Application application) {
        super(application);
        ldZona = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
        ldZonaCambiada = new MutableLiveData<>();
    }

    public MutableLiveData<Zona[]> getLdZona() {
        return ldZona;
    }

    public MutableLiveData<Integer> getLdError() {
        return ldError;
    }

    public MutableLiveData<Zona> getLdZonaCambiada() {
        return ldZonaCambiada;
    }

    public void obtenerZonasDisponibles(){
        OkHttpClient client;
        Retrofit retrofit;
        ZonaInterface zonaInterface;

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

            zonaInterface = retrofit.create(ZonaInterface.class);

            zonaInterface.getZonasDisponibles().enqueue(new Callback<Zona[]>() {
                @Override
                public void onResponse(Call<Zona[]> call, Response<Zona[]> response) {
                    if(response.isSuccessful()){
                        ldZona.postValue(response.body());
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
                public void onFailure(Call<Zona[]> call, Throwable t) {
                    ldError.postValue(0);
                }
            });
        }else{
            ldError.setValue(401);
        }
    }

    public void cambiarZona(final Zona zona) {
        OkHttpClient client;
        Retrofit retrofit;
        ZonaInterface zonaInterface;

        if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            zonaInterface = retrofit.create(ZonaInterface.class);

            zonaInterface.postZona(zona).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        ldZonaCambiada.postValue(zona);
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        ldError.postValue(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    ldError.postValue(0);
                }
            });
        }else{
            ldError.setValue(401);
        }
    }
}
