package com.arensis_games.grumpyworld.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arensis_games.grumpyworld.Conexion.Authentication;
import com.arensis_games.grumpyworld.ViewModels.LoginActivityVM;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.Models.Rollo;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private LoginActivityVM vm;
    private Observer<Rollo> observerRollo;
    private Observer<Integer> observerError;
    private LoginActivity thisActivity = this;
    private Button btnEnviar;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnEnviar = findViewById(R.id.btnEnviar);
        progress = findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(LoginActivityVM.class);

        observerRollo = new Observer<Rollo>() {
            @Override
            public void onChanged(@Nullable Rollo rollo) {
                Intent intent;
                if(rollo!=null){
                    intent = new Intent(thisActivity, MainActivity.class);
                    intent.putExtra("rollo", rollo);
                    startActivity(intent);
                    finish();
                }
            }
        };
        vm.getRolloLiveData().observe(this, observerRollo);

        observerError = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

                if(integer != null){
                    btnEnviar.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    int error = integer;
                    switch (error){
                        case 401:
                            Toast.makeText(LoginActivity.this, getString(R.string.error_usuario_contrasena_incorrectos), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this, getString(R.string.error_desconocido, error), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
        };
        vm.getErrorLiveData().observe(this, observerError);
    }


    public void enviar(View view) {
        if(etUsuario.getText().toString().equals("") || etContrasena.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, getString(R.string.error_campo_vacio), Toast.LENGTH_SHORT).show();
        }else{
            Authentication authentication = new Authentication();
            authentication.setUsuario(etUsuario.getText().toString());
            authentication.setContrasena(etContrasena.getText().toString());

            btnEnviar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);

            vm.obtenerRollo(authentication);
        }
    }

    public void registrarse(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }
}
