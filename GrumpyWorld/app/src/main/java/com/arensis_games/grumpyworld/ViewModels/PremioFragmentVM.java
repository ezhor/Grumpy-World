package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.CazaInterface;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Conexion.PremioInterface;
import com.arensis_games.grumpyworld.Models.Caza;
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

public class PremioFragmentVM extends AndroidViewModel {
    private MutableLiveData<Void> ldPremio;
    private MutableLiveData<Integer> ldError;

    public PremioFragmentVM(@NonNull Application application) {
        super(application);
        this.ldPremio = new MutableLiveData<>();
        this.ldError = new MutableLiveData<>();
    }

    public MutableLiveData<Void> getLdPremio() {
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
                    //.addInterceptor(new BasicAuthInterceptor("dani", "hola"))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            premioInterface = retrofit.create(PremioInterface.class);

            premioInterface.getPremio().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
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
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }else{
            ldError.setValue(401);
        }
    }

}
