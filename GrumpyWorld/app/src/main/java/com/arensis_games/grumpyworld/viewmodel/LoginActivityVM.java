package com.arensis_games.grumpyworld.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import com.arensis_games.grumpyworld.connection.Authentication;
import com.arensis_games.grumpyworld.connection.BasicAuthInterceptor;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.connection.RolloInterface;
import com.arensis_games.grumpyworld.model.Rollo;
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
    private final MutableLiveData<String> errorLiveData;
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
    public MutableLiveData<String> getErrorLiveData() {
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
                    GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    editor.putString("usuario", authentication.getUsuario());
                    editor.putString("contrasena", authentication.getContrasena());
                    editor.commit();
                    rolloLiveData.postValue(response.body());
                    GestoraToken.setAuthorization(response.headers().get("Authorization"));
                }else{
                    errorLiveData.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Rollo> call, Throwable t) {

            }
        });
    }
}
