package com.arensis_games.grumpyworld.activity;

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

import com.arensis_games.grumpyworld.connection.Authentication;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.viewmodel.RegistroActivityVM;

public class RegistroActivity extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etContrasena;
    private EditText etRepetirContrasena;
    private Button btnEnviar;
    private ProgressBar progress;
    private RegistroActivityVM vm;
    private Observer<Integer> codigoObserver;
    private Authentication authentication = new Authentication();
    private RegistroActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        btnEnviar = findViewById(R.id.btnEnviar);
        progress = findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(RegistroActivityVM.class);

        codigoObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Intent intent;

                if(integer != null){
                    btnEnviar.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    int error = integer;
                    switch (error){
                        case 200:
                            intent = new Intent(thisActivity, SplashActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case 409:
                            Toast.makeText(RegistroActivity.this, getString(R.string.error_usuario_ya_existe), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(RegistroActivity.this, getString(R.string.error_desconocido, error), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }
        };
        vm.getLdCodigo().observe(this, codigoObserver);
    }

    public void enviar(View view){
        if(etUsuario.getText().toString().equals("") || etContrasena.getText().toString().equals("")
                || etRepetirContrasena.getText().toString().equals("")){
            Toast.makeText(RegistroActivity.this, getString(R.string.error_campo_vacio), Toast.LENGTH_SHORT).show();
        }else if(!etContrasena.getText().toString().equals(etRepetirContrasena.getText().toString())){
            Toast.makeText(RegistroActivity.this, getString(R.string.error_contrasenas_no_coinciden), Toast.LENGTH_SHORT).show();
        }else if(etUsuario.getText().toString().length() > 15){
            Toast.makeText(RegistroActivity.this, getString(R.string.error_contrasenas_no_coinciden), Toast.LENGTH_SHORT).show();
        }else {
            authentication.setUsuario(etUsuario.getText().toString());
            authentication.setContrasena(etContrasena.getText().toString());

            btnEnviar.setVisibility(View.INVISIBLE);
            progress.setVisibility(View.VISIBLE);

            vm.registrarUsuario(authentication);
        }
    }
}
