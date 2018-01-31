package com.arensis_games.grumpyworld;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

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

    public void obtenerRollo(Authentication authentication){
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
