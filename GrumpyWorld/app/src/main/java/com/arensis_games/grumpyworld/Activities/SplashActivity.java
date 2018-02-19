package com.arensis_games.grumpyworld.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.arensis_games.grumpyworld.Conexion.Authentication;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.LoginActivityVM;


public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private Observer<Rollo> rolloObserver;
    private Observer<Integer> errorObserver;
    private LoginActivityVM vm;
    private SplashActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPref = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
        String usuario = sharedPref.getString("usuario","");
        String contrasena = sharedPref.getString("contrasena","");
        final Intent intent;

        if(!usuario.equals("") && !contrasena.equals("")){
            intent = new Intent(this, MainActivity.class);
            vm = ViewModelProviders.of(this).get(LoginActivityVM.class);
            rolloObserver = new Observer<Rollo>() {
                @Override
                public void onChanged(@Nullable Rollo rollo) {
                    intent.putExtra("rollo", rollo);
                    startActivity(intent);
                    finish();
                }
            };
            vm.getRolloLiveData().observe(this, rolloObserver);

            errorObserver = new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    Intent intent;
                    if(integer != null){
                        int error = integer;
                        switch (error){
                            case 401:
                                intent = new Intent(thisActivity, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            default:
                                Toast.makeText(SplashActivity.this, getString(R.string.error_desconocido, error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            };
            vm.getErrorLiveData().observe(this, errorObserver);

            vm.obtenerRollo(new Authentication(usuario, contrasena));


        }else{
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}
