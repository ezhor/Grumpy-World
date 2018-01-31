package com.arensis_games.grumpyworld;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private LoginActivityVM vm;
    private Observer<Rollo> observerRollo;
    private Observer<Integer> observerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);

        etUsuario.setText("dani"); //Debug
        etContrasena.setText("hola");

        vm = ViewModelProviders.of(this).get(LoginActivityVM.class);

        observerRollo = new Observer<Rollo>() {
            @Override
            public void onChanged(@Nullable Rollo rollo) {
                etUsuario.setText(rollo.getZona());
            }
        };
        vm.getRolloLiveData().observe(this, observerRollo);

        observerError = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                int error = integer;
                switch (error){
                    case 401:
                        Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "Error desconocido ("+error+")", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        vm.getErrorLiveData().observe(this, observerError);
    }


    public void enviar(View view) {
        if(etUsuario.getText().toString().equals("") || etContrasena.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this, "Ningún campo puede quedar vacío", Toast.LENGTH_SHORT).show();
        }else{
            Authentication authentication = new Authentication();
            authentication.setUsuario(etUsuario.getText().toString());
            authentication.setContrasena(etContrasena.getText().toString());

            vm.obtenerRollo(authentication);
        }
    }
}
