package com.arensis_games.grumpyworld.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.arensis_games.grumpyworld.Adapters.AdaptadorDrawer;
import com.arensis_games.grumpyworld.Fragments.InicioFragment;
import com.arensis_games.grumpyworld.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    FrameLayout framePrincipal;
    ListView lvDrawer;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        lvDrawer = findViewById(R.id.lvDrawer);
        framePrincipal = findViewById(R.id.framePrincipal);

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
        String[] elementos = {
                "Entrenamiento", "Caza", "Mapa",
                "Fabricación", "Equipamiento",
                "Amigos","Duelo", "Ranking"
        };

        lvDrawer.setAdapter(new AdaptadorDrawer(getApplicationContext(),
                R.layout.fila_drawer, R.id.texto, elementos));
        lvDrawer.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //lvDrawer.setItemChecked(position, true);
        drawerLayout.closeDrawer(lvDrawer);
    }
}
