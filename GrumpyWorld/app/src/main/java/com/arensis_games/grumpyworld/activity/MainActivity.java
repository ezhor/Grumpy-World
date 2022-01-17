package com.arensis_games.grumpyworld.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.adapter.AdaptadorDrawer;
import com.arensis_games.grumpyworld.fragment.AmigosFragment;
import com.arensis_games.grumpyworld.fragment.CazaFragment;
import com.arensis_games.grumpyworld.fragment.CreditsFragment;
import com.arensis_games.grumpyworld.fragment.EntrenamientoFragment;
import com.arensis_games.grumpyworld.fragment.EquipamientoFragment;
import com.arensis_games.grumpyworld.fragment.FabricacionFragment;
import com.arensis_games.grumpyworld.fragment.InicioFragment;
import com.arensis_games.grumpyworld.fragment.LobbyDueloFragment;
import com.arensis_games.grumpyworld.fragment.MapaFragment;
import com.arensis_games.grumpyworld.fragment.RankingFragment;
import com.arensis_games.grumpyworld.fragment.SelloFragment;
import com.arensis_games.grumpyworld.model.Rollo;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvDrawer;
    private DrawerLayout drawerLayout;
    private MainActivityVM vm;
    private Observer<String> errorObserver;
    private Rollo rollo;
    private String[] elementos;
    private MainActivity thisActivity = this;
    private Observer<Fragment> fragmentObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        elementos = new String[]{
                getString(R.string.menu_campana),
                getString(R.string.menu_entrenamiento),
                getString(R.string.menu_caza),
                getString(R.string.menu_mapa),

                getString(R.string.menu_inventario),
                getString(R.string.menu_fabricacion),
                getString(R.string.menu_equipamiento),
                getString(R.string.menu_sello),

                getString(R.string.menu_multijugador),
                getString(R.string.menu_amigos),
                getString(R.string.menu_duelo),
                getString(R.string.menu_ranking),

                getString(R.string.menu_extras),
                getString(R.string.menu_creditos),
                getString(R.string.menu_cerrar_sesion)
        };

        vm = ViewModelProviders.of(this).get(MainActivityVM.class);
        rollo = getIntent().getParcelableExtra("rollo");
        vm.setRollo(rollo);

        drawerLayout = findViewById(R.id.drawerLayout);
        lvDrawer = findViewById(R.id.lvDrawer);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                Intent intent;
                if(error != null){
                    switch (error){
                        case "401":
                            Toast.makeText(thisActivity, getString(R.string.error_sesion_caducada), Toast.LENGTH_SHORT).show();
                            intent = new Intent(thisActivity, SplashActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, getString(R.string.error_desconocido, error), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);

        fragmentObserver = new Observer<Fragment>() {
            @Override
            public void onChanged(@Nullable Fragment fragment) {
                if(fragment != null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framePrincipal, fragment)
                            .commit();
                }
            }
        };

        vm.getLdFragment().observe(this, fragmentObserver);

        mostrarFragmentInicio();
        rellenarDrawer();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
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
        }else if(opcionElegida.equals(getString(R.string.menu_mapa))){
            fragment = new MapaFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_fabricacion))){
            fragment = new FabricacionFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_sello))){
            fragment = new SelloFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_equipamiento))){
            fragment = new EquipamientoFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_amigos))){
            fragment = new AmigosFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_ranking))){
            fragment = new RankingFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_duelo))){
            fragment = new LobbyDueloFragment();
        }else if(opcionElegida.equals(getString(R.string.menu_creditos))){
            fragment = new CreditsFragment();
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
