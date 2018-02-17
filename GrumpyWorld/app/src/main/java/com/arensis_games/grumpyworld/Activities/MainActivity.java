package com.arensis_games.grumpyworld.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.Adapters.AdaptadorDrawer;
import com.arensis_games.grumpyworld.Fragments.EntrenamientoFragment;
import com.arensis_games.grumpyworld.Fragments.InicioFragment;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.LoginActivityVM;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvDrawer;
    private DrawerLayout drawerLayout;
    private MainActivityVM vm;
    private Observer<Integer> errorObserver;
    private Rollo rollo;
    private String[] elementos ={
            "Campaña",
            "Entrenamiento", "Caza", "Mapa", "??????",
            "Multijugador",
            "Amigos","Duelo", "Ranking",
            "Rollo",
            "Fabricación", "Equipamiento",
            "Extras",
            "Historia", "Cerrar sesión"
    };
    private MainActivity thisActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = ViewModelProviders.of(this).get(MainActivityVM.class);
        rollo = getIntent().getParcelableExtra("rollo");
        vm.setRollo(rollo);

        drawerLayout = findViewById(R.id.drawerLayout);
        lvDrawer = findViewById(R.id.lvDrawer);

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
                            Toast.makeText(MainActivity.this, "Error desconocido ("+error+")", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        };

        mostrarFragmentInicio();
        rellenarDrawer();
    }

    private void mostrarFragmentInicio() {
        Fragment fragment = new InicioFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framePrincipal, fragment)
                .commit();
    }

    private void rellenarDrawer() {
        lvDrawer.setAdapter(new AdaptadorDrawer(getApplicationContext(),
                R.layout.fila_drawer, R.id.texto, elementos));
        lvDrawer.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        Intent intent;

        drawerLayout.closeDrawer(lvDrawer);
        switch (elementos[position]){
            case "Entrenamiento":
                fragment = new EntrenamientoFragment();
                break;
            case "Cerrar sesión":
                vm.cerrarSesion();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framePrincipal, fragment)
                    .commit();
        }
    }


    public void alternarMenu(View view) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
