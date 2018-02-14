package com.arensis_games.grumpyworld.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arensis_games.grumpyworld.Adapters.AdaptadorDrawer;
import com.arensis_games.grumpyworld.Fragments.EntrenamientoFragment;
import com.arensis_games.grumpyworld.Fragments.InicioFragment;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvDrawer;
    private DrawerLayout drawerLayout;
    private MainActivityVM vm;
    private Rollo rollo;
    private String[] elementos ={
            "Entrenamiento", "Caza", "Mapa",
            "Fabricación", "Equipamiento",
            "Amigos","Duelo", "Ranking",
            "Cerrar sesión"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = ViewModelProviders.of(this).get(MainActivityVM.class);
        rollo = getIntent().getParcelableExtra("rollo");
        vm.setRollo(rollo);

        drawerLayout = findViewById(R.id.drawerLayout);
        lvDrawer = findViewById(R.id.lvDrawer);

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
}
