package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.adapter.AdaptadorMapa;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Zona;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import com.arensis_games.grumpyworld.viewmodel.MapaFragmentVM;

public class MapaFragment extends Fragment implements AdapterView.OnItemClickListener{

    private MapaFragmentVM vm;
    private Observer<Zona[]> zonaObserver;
    private Observer<String> errorObserver;
    private Observer<Zona> zonaCambiadaObserver;
    private ListView lista;
    private TextView texto;
    private MapaFragment thisFragment = this;
    private GestoraGUI gesGUI = new GestoraGUI();
    private ProgressBar progress;

    public MapaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        lista = view.findViewById(R.id.lista);
        texto = view.findViewById(R.id.texto);
        progress = view.findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(MapaFragmentVM.class);

        zonaObserver = new Observer<Zona[]>() {
            @Override
            public void onChanged(@Nullable Zona[] zonas) {
                if(zonas != null){
                    texto.setText(getString(R.string.zonas_desbloqueadas, zonas.length));
                    lista.setAdapter(new AdaptadorMapa(getContext(),
                            R.layout.fila_mapa, R.id.tvNombre, zonas));
                    lista.setOnItemClickListener(thisFragment);
                    progress.setVisibility(View.GONE);
                }
            }
        };
        vm.getLdZona().observe(this, zonaObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);

        zonaCambiadaObserver = new Observer<Zona>() {
            @Override
            public void onChanged(@Nullable Zona zona) {
                if(zona != null){
                    Toast.makeText(getContext(), getString(R.string.zona_cambiada,
                            gesGUI.getNombreZona(getResources(), zona.getNombre())),
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        vm.getLdZonaCambiada().observe(this, zonaCambiadaObserver);

        vm.obtenerZonasDisponibles();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AdaptadorMapa.ViewHolder holder = (AdaptadorMapa.ViewHolder) view.getTag();
        vm.cambiarZona(holder.getZona());
    }
}
