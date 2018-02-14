package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.Authentication;
import com.arensis_games.grumpyworld.Conexion.RolloInterface;
import com.arensis_games.grumpyworld.Conexion.UsuarioInterface;
import com.arensis_games.grumpyworld.Models.Rollo;
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getApplication().getString(R.string.SERVER_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsuarioInterface usuarioInterface = retrofit.create(UsuarioInterface.class);

        usuarioInterface.postUsuario(authentication).enqueue(new Callback<Rollo>() {
            @Override
            public void onResponse(Call<Rollo> call, Response<Rollo> response) {
                ldCodigo.setValue(response.code());
            }

            @Override
            public void onFailure(Call<Rollo> call, Throwable t) {

            }
        });
    }


}
