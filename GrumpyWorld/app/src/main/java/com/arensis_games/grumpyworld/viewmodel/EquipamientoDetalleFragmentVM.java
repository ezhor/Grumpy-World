package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.EquipamientoInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.EquipablePoseidoDetalle;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 15/02/18.
 */

public class EquipamientoDetalleFragmentVM extends AndroidViewModel {
    private MutableLiveData<EquipablePoseidoDetalle> ldEquipableDetalle;
    private MutableLiveData<String> ldError;
    private MutableLiveData<Boolean> ldEquipado;

    public EquipamientoDetalleFragmentVM(@NonNull Application application) {
        super(application);
        ldEquipableDetalle = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
        ldEquipado = new MutableLiveData<>();
    }

    public MutableLiveData<EquipablePoseidoDetalle> getLdEquipableDetalle() {
        return ldEquipableDetalle;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }

    public MutableLiveData<Boolean> getLdEquipado() {
        return ldEquipado;
    }

    public void obtenerEquipablePoseidoDetalle(int id){
        OkHttpClient client;
        Retrofit retrofit;
        EquipamientoInterface equipamientoInterface;

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

            equipamientoInterface = retrofit.create(EquipamientoInterface.class);

            equipamientoInterface.getEquipablePoseidoDetalle(id).enqueue(new Callback<EquipablePoseidoDetalle>() {
                @Override
                public void onResponse(Call<EquipablePoseidoDetalle> call, Response<EquipablePoseidoDetalle> response) {
                    if(response.isSuccessful()){
                        ldEquipableDetalle.postValue(response.body());
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
                public void onFailure(Call<EquipablePoseidoDetalle> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void equipar(int id) {
        OkHttpClient client;
        Retrofit retrofit;
        EquipamientoInterface equipamientoInterface;

        if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            equipamientoInterface = retrofit.create(EquipamientoInterface.class);

            equipamientoInterface.equiparEquipablePoseido(id).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        ldEquipado.setValue(true);
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        ldError.setValue(String.valueOf(response.code()));
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }
}
