package com.arensis_games.grumpyworld.ViewModels;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Conexion.Authentication;
import com.arensis_games.grumpyworld.Conexion.BasicAuthInterceptor;
import com.arensis_games.grumpyworld.Conexion.GestoraToken;
import com.arensis_games.grumpyworld.Conexion.RolloInterface;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.R;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 31/01/18.
 */

public class LoginActivityVM extends AndroidViewModel {
    private final MutableLiveData<Rollo> rolloLiveData;
    private final MutableLiveData<Integer> errorLiveData;
    private SharedPreferences sharedPref = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = sharedPref.edit();

    public LoginActivityVM(@NonNull Application application) {
        super(application);
        this.rolloLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();
    }

    public LiveData<Rollo> getRolloLiveData(){
        return this.rolloLiveData;
    }
    public MutableLiveData<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public void obtenerRollo(final Authentication authentication){
        OkHttpClient client;
        Retrofit retrofit;
        RolloInterface rolloInterface;

        client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(authentication.getUsuario(), authentication.getContrasena()))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getApplication().getString(R.string.SERVER_URL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rolloInterface = retrofit.create(RolloInterface.class);

        rolloInterface.getRollo().enqueue(new Callback<Rollo>() {
            @Override
            public void onResponse(Call<Rollo> call, Response<Rollo> response) {
                if(response.isSuccessful()){
                    GestoraToken.setToken(response.headers().get("Authorization"));
                    editor.putString("usuario", authentication.getUsuario());
                    editor.putString("contrasena", authentication.getContrasena());
                    editor.commit();
                    rolloLiveData.postValue(response.body());
                }else{
                    errorLiveData.postValue(response.code());
                }
            }

            @Override
            public void onFailure(Call<Rollo> call, Throwable t) {

            }
        });
    }
}
