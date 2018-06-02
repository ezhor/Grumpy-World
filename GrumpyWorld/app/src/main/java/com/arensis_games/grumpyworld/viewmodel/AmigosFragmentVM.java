package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.connection.AmigoInterface;
import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.EquipamientoInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.Amigo;
import com.arensis_games.grumpyworld.model.EquipablePoseido;
import com.arensis_games.grumpyworld.model.ListadoAmigosCompleto;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 15/02/18.
 */

public class AmigosFragmentVM extends AndroidViewModel {
    private MutableLiveData<ListadoAmigosCompleto> ldListadoAmigosCompleto;
    private MutableLiveData<Amigo[]> ldListadoUsuariosBusqueda;
    private MutableLiveData<String> ldError;

    public AmigosFragmentVM(@NonNull Application application) {
        super(application);
        ldListadoAmigosCompleto = new MutableLiveData<>();
        ldListadoUsuariosBusqueda = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
    }

    public MutableLiveData<ListadoAmigosCompleto> getLdListadoAmigosCompleto() {
        return ldListadoAmigosCompleto;
    }

    public MutableLiveData<Amigo[]> getLdListadoUsuariosBusqueda() {
        return ldListadoUsuariosBusqueda;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }


    public void obtenerListadoAmigosCompleto(){
        OkHttpClient client;
        Retrofit retrofit;
        AmigoInterface amigoInterface;

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

            amigoInterface = retrofit.create(AmigoInterface.class);

            amigoInterface.getListadoAmigosCompleto().enqueue(new Callback<ListadoAmigosCompleto>() {
                @Override
                public void onResponse(Call<ListadoAmigosCompleto> call, Response<ListadoAmigosCompleto> response) {
                    if(response.isSuccessful()){
                        ldListadoAmigosCompleto.postValue(response.body());
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
                public void onFailure(Call<ListadoAmigosCompleto> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void buscarUsuario(String nombre){
        OkHttpClient client;
        Retrofit retrofit;
        AmigoInterface amigoInterface;

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

            amigoInterface = retrofit.create(AmigoInterface.class);

            amigoInterface.buscarUsuario(nombre).enqueue(new Callback<Amigo[]>() {
                @Override
                public void onResponse(Call<Amigo[]> call, Response<Amigo[]> response) {
                    if(response.isSuccessful()){
                        ldListadoUsuariosBusqueda.postValue(response.body());
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
                public void onFailure(Call<Amigo[]> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void agregarAmigo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        AmigoInterface amigoInterface;

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

            amigoInterface = retrofit.create(AmigoInterface.class);

            amigoInterface.agregarAmigo(id).enqueue(new Callback<ListadoAmigosCompleto>() {
                @Override
                public void onResponse(Call<ListadoAmigosCompleto> call, Response<ListadoAmigosCompleto> response) {
                    if(response.isSuccessful()){
                        ldListadoAmigosCompleto.postValue(response.body());
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
                public void onFailure(Call<ListadoAmigosCompleto> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void borrarAmigo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        AmigoInterface amigoInterface;

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

            amigoInterface = retrofit.create(AmigoInterface.class);

            amigoInterface.borrarAmigo(id).enqueue(new Callback<ListadoAmigosCompleto>() {
                @Override
                public void onResponse(Call<ListadoAmigosCompleto> call, Response<ListadoAmigosCompleto> response) {
                    if(response.isSuccessful()){
                        ldListadoAmigosCompleto.postValue(response.body());
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
                public void onFailure(Call<ListadoAmigosCompleto> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }
}