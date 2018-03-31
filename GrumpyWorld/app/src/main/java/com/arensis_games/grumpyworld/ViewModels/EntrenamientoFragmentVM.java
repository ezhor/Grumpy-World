package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.AtributosInterface;
import com.arensis_games.grumpyworld.Conexion.BasicAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.Models.Entrenamiento;
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
    private MutableLiveData<Atributos> ldAtributos;
    private MutableLiveData<Integer> ldError;

    public EntrenamientoFragmentVM(@NonNull Application application) {
        super(application);
        ldAtributos = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
    }

    public MutableLiveData<Atributos> getLdAtributos() {
        return ldAtributos;
    }

    public MutableLiveData<Integer> getLdError() {
        return ldError;
    }

    public void obtenerAtributos(){
        OkHttpClient client;
        Retrofit retrofit;
        AtributosInterface atributosInterface;

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

            atributosInterface = retrofit.create(AtributosInterface.class);

            atributosInterface.getAtributos().enqueue(new Callback<Atributos>() {
                @Override
                public void onResponse(Call<Atributos> call, Response<Atributos> response) {
                    if(response.isSuccessful()){
                        ldAtributos.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        /*
                            Puede haber pasado una hora desde que el usuario uso la app por última
                            vez y que Android aún no haya borrado el token de memoria por lo que
                            dejaría de ser válido (401 Unauthorized)
                            En ese caso se manda al usuario a la pantalla de inicio para que
                            el sistema inicie sesión de nuevo.
                         */
                        ldError.postValue(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Atributos> call, Throwable t) {
                    ldError.postValue(0);
                }
            });
        }else{
            ldError.setValue(401);
        }
    }

    public void entrenar(Entrenamiento entrenamiento) {
        OkHttpClient client;
        Retrofit retrofit;
        AtributosInterface atributosInterface;

        if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            atributosInterface = retrofit.create(AtributosInterface.class);

            atributosInterface.postAtributos(entrenamiento).enqueue(new Callback<Atributos>() {
                @Override
                public void onResponse(Call<Atributos> call, Response<Atributos> response) {
                    if(response.isSuccessful()){
                        ldAtributos.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        ldError.setValue(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Atributos> call, Throwable t) {
                    ldError.postValue(0);
                }
            });
        }else{
            ldError.setValue(401);
        }
    }
}
