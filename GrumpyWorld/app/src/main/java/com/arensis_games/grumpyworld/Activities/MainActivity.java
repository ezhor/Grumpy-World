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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.Adapters.AdaptadorDrawer;
import com.arensis_games.grumpyworld.Fragments.CazaFragment;
import com.arensis_games.grumpyworld.Fragments.EntrenamientoFragment;
import com.arensis_games.grumpyworld.Fragments.InicioFragment;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvDrawer;
    private DrawerLayout drawerLayout;
    private MainActivityVM vm;
    private Observer<Integer> errorObserver;
    private Rollo rollo;
    private String[] elementos;
    private MainActivity thisActivity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elementos = new String[]{
                getString(R.string.menu_campana),
                getString(R.string.menu_entrenamiento),
                getString(R.string.menu_caza),
                getString(R.string.menu_mapa),
                getString(R.string.menu_sello),
                getString(R.string.menu_multijugador),
                getString(R.string.menu_amigos),
                getString(R.string.menu_duelo),
                getString(R.string.menu_ranking),
                getString(R.string.menu_inventario),
                getString(R.string.menu_fabricacion),
                getString(R.string.menu_equipamiento),
                getString(R.string.menu_extras),
                getString(R.string.menu_historia),
                getString(R.string.menu_cerrar_sesion),
        };

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
                            intent = new Intent(thisActivity, SplashActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, getString(R.string.error_desconocido, error), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);

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
        String opcionElegida = elementos[position];

        drawerLayout.closeDrawer(lvDrawer);

        if(opcionElegida.equals(getString(R.string.menu_entrenamiento))){
            fragment = new EntrenamientoFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_cerrar_sesion))){
            vm.cerrarSesion();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else if(opcionElegida.equals(getString(R.string.menu_caza))){
            fragment = new CazaFragment();
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
