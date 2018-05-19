package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.conection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.conection.GestoraToken;
import com.arensis_games.grumpyworld.conection.PremioInterface;
import com.arensis_games.grumpyworld.model.Material;
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

public class PremioFragmentVM extends AndroidViewModel {
    private MutableLiveData<Material[]> ldPremio;
    private MutableLiveData<Integer> ldError;

    public PremioFragmentVM(@NonNull Application application) {
        super(application);
        this.ldPremio = new MutableLiveData<>();
        this.ldError = new MutableLiveData<>();
    }

    public MutableLiveData<Material[]> getLdPremio() {
        return ldPremio;
    }

    public MutableLiveData<Integer> getLdError() {
        return ldError;
    }


    public void obtenerPremio(){
        OkHttpClient client;
        Retrofit retrofit;
        PremioInterface premioInterface;

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

            premioInterface = retrofit.create(PremioInterface.class);

            premioInterface.getPremio().enqueue(new Callback<Material[]>() {
                @Override
                public void onResponse(Call<Material[]> call, Response<Material[]> response) {
                    if(response.isSuccessful()){
                        ldPremio.postValue(response.body());
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
                public void onFailure(Call<Material[]> call, Throwable t) {
                    ldError.postValue(0);
                }
            });
        }else{
            ldError.setValue(401);
        }
    }

}
