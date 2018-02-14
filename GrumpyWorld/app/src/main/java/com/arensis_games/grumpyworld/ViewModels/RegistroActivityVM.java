package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.Authentication;
import com.arensis_games.grumpyworld.Conexion.UsuarioInterface;
import com.arensis_games.grumpyworld.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 14/02/18.
 */

public class RegistroActivityVM extends AndroidViewModel{
    private MutableLiveData<Integer> ldCodigo;

    public RegistroActivityVM(@NonNull Application application) {
        super(application);
        ldCodigo = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getLdCodigo() {
        return ldCodigo;
    }

    public void registrarUsuario(Authentication authentication) {
        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApplication().getString(R.string.SERVER_URL))
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);

        usuarioInterface.postUsuario(authentication).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ldCodigo.setValue(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
