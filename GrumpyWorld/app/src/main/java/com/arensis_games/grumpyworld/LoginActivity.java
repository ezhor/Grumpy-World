package com.arensis_games.grumpyworld;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText etUsuario;
    EditText etContrasena;
    LoginActivityVM vm;
    Observer<Rollo> observerRollo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);

        vm = ViewModelProviders.of(this).get(LoginActivityVM.class);

        vm.getRolloLiveData().observe(this, observerRollo);

        observerRollo = new Observer<Rollo>() {
            @Override
            public void onChanged(@Nullable Rollo rollo) {
                //Pasar o no a la siguiente actividad
            }
        };
    }


    public void enviar(View view) {
        Authentication authentication = new Authentication();
        authentication.setUsuario(etUsuario.getText().toString());
        authentication.setContrasena(etContrasena.getText().toString());

        vm.obtenerRollo(authentication);
    }
}
