package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.FabricacionInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.Equipable;
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

public class FabricacionFragmentVM extends AndroidViewModel {
    private MutableLiveData<Equipable[]> ldEquipables;
    private MutableLiveData<String> ldError;

    public FabricacionFragmentVM(@NonNull Application application) {
        super(application);
        ldEquipables = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
    }

    public MutableLiveData<Equipable[]> getLdEquipables() {
        return ldEquipables;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }


    public void obtenerEquipablesDisponibles(){
        OkHttpClient client;
        Retrofit retrofit;
        FabricacionInterface fabricacionInterface;

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

            fabricacionInterface = retrofit.create(FabricacionInterface.class);

            fabricacionInterface.getEquipablesDisponibles().enqueue(new Callback<Equipable[]>() {
                @Override
                public void onResponse(Call<Equipable[]> call, Response<Equipable[]> response) {
                    if(response.isSuccessful()){
                        ldEquipables.postValue(response.body());
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
                public void onFailure(Call<Equipable[]> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }
}
