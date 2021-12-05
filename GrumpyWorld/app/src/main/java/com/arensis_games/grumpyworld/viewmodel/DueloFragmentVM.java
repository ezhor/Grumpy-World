package com.arensis_games.grumpyworld.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.connection.BearerAuthInterceptor;
import com.arensis_games.grumpyworld.connection.DueloInterface;
import com.arensis_games.grumpyworld.connection.GestoraToken;
import com.arensis_games.grumpyworld.model.Duelo;
import com.arensis_games.grumpyworld.model.EstadoDuelo;
import com.arensis_games.grumpyworld.model.LobbyDuelo;
import com.arensis_games.grumpyworld.model.Turno;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dparrado on 15/02/18.
 */

public class DueloFragmentVM extends AndroidViewModel {
    private MutableLiveData<LobbyDuelo> ldLobbyDuelo;
    private MutableLiveData<Duelo> ldDuelo;
    private MutableLiveData<EstadoDuelo> ldEstado;
    private MutableLiveData<String> ldError;
    private Socket socket;

    public DueloFragmentVM(@NonNull Application application) throws URISyntaxException {
        super(application);
        ldLobbyDuelo = new MutableLiveData<>();
        ldDuelo = new MutableLiveData<>();
        ldEstado = new MutableLiveData<>();
        ldError = new MutableLiveData<>();
    }

    public MutableLiveData<LobbyDuelo> getLdLobbyDuelo() {
        return ldLobbyDuelo;
    }

    public MutableLiveData<Duelo> getLdDuelo() {
        return ldDuelo;
    }

    public MutableLiveData<EstadoDuelo> getLdEstado() {
        return ldEstado;
    }

    public MutableLiveData<String> getLdError() {
        return ldError;
    }

    public void obtenerLobbyDuelo(){
        if(GestoraToken.getAuthorization() != null){
            /*socket.emit("DuelLobbyAck", (Ack) args -> {
                //GestoraToken.setAuthorization((String)args[1]);
                ldLobbyDuelo.postValue(parseLobbyDuelo((JSONObject)args[0]));
            });*/
        }else{
            ldError.setValue("401");
        }
    }

    public void retarADuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;
         if(GestoraToken.getAuthorization() != null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BearerAuthInterceptor(GestoraToken.getAuthorization()))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(getApplication().getString(R.string.SERVER_URL))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.retarADuelo(id).enqueue(new Callback<LobbyDuelo>() {
                @Override
                public void onResponse(Call<LobbyDuelo> call, Response<LobbyDuelo> response) {
                    if(response.isSuccessful()){
                        ldLobbyDuelo.postValue(response.body());
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
                public void onFailure(Call<LobbyDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void rechazarDuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.rechazarDuelo(id).enqueue(new Callback<LobbyDuelo>() {
                @Override
                public void onResponse(Call<LobbyDuelo> call, Response<LobbyDuelo> response) {
                    if(response.isSuccessful()){
                        ldLobbyDuelo.postValue(response.body());
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
                public void onFailure(Call<LobbyDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void obtenerDuelo(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.getDuelo(id).enqueue(new Callback<Duelo>() {
                @Override
                public void onResponse(Call<Duelo> call, Response<Duelo> response) {
                    if(response.isSuccessful()){
                        ldDuelo.postValue(response.body());
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
                public void onFailure(Call<Duelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void elegirTurno(Turno turno){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.elegirTurno(turno).enqueue(new Callback<EstadoDuelo>() {
                @Override
                public void onResponse(Call<EstadoDuelo> call, Response<EstadoDuelo> response) {
                    if(response.isSuccessful()){
                        ldEstado.postValue(response.body());
                        GestoraToken.setAuthorization(response.headers().get("Authorization"));
                    }else{
                        /*
                            Puede haber pasado una hora desde que el usuario uso la app por última
                            vez y que Android aún no haya borrado el token de memoria por lo que
                            dejaría de ser válido (401 Unauthorized)
                            En ese caso se manda al usuario a la pantalla de inicio para que
                            el sistema inicie sesión de nuevo.


                        En este caso concreto, el 403 es un error conocido que indica que el jugador
                        ya había elegido su turno.
                        */
                        if(response.code() == 403){
                            ldEstado.postValue(null);
                        }else{
                            ldError.setValue(String.valueOf(response.code()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<EstadoDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    public void obtenerEstado(int id){
        OkHttpClient client;
        Retrofit retrofit;
        DueloInterface dueloInterface;

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

            dueloInterface = retrofit.create(DueloInterface.class);

            dueloInterface.getEstado(id).enqueue(new Callback<EstadoDuelo>() {
                @Override
                public void onResponse(Call<EstadoDuelo> call, Response<EstadoDuelo> response) {
                    if(response.isSuccessful()){
                        ldEstado.postValue(response.body());
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
                public void onFailure(Call<EstadoDuelo> call, Throwable t) {
                    ldError.postValue(t.getMessage());
                }
            });
        }else{
            ldError.setValue("401");
        }
    }

    private LobbyDuelo parseLobbyDuelo(JSONObject arg){
        return new LobbyDuelo();
    }

    private void configBearerAuthentication() {
        socket.io().on(Manager.EVENT_TRANSPORT, args -> {
            Transport transport = (Transport) args[0];
            transport.on(Transport.EVENT_REQUEST_HEADERS, args1 -> {
                @SuppressWarnings("unchecked")
                Map<String, List<String>> headers = (Map<String, List<String>>) args1[0];
                headers.put("authorization", Arrays.asList("Bearer " + GestoraToken.getAuthorization()));
            });
        });
    }

    private void startSocket(){
        try {
            socket = IO.socket("http://192.168.0.27:82");
            socket.io().on(Manager.EVENT_TRANSPORT, args -> {
                Transport transport = (Transport) args[0];
                transport.on(Transport.EVENT_ERROR, args1 -> {
                    Exception e = (Exception) args1[0];
                    Log.e("Socket IO Error", "Transport error " + e);
                    e.printStackTrace();
                    e.getCause().printStackTrace();
                });
            });
            socket.on(Socket.EVENT_CONNECT, args -> Log.e("SOCKET ID", (socket.id())));
            socket.connect();
            Log.e("Socket IO", "\"Connect\" called");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}