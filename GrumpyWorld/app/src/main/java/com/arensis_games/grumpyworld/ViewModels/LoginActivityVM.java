package com.arensis_games.grumpyworld.ViewModels;


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
                    GestoraToken.setToken(response.headers().get("Authorization"));
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
