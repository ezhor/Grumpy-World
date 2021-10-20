package com.arensis_games.grumpyworld.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorPremio;
import com.arensis_games.grumpyworld.model.Material;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import com.arensis_games.grumpyworld.viewmodel.PremioDueloFragmentVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class PremioDueloFragment extends Fragment implements View.OnClickListener {
    Button btnCazarDeNuevo;
    TextView tvVictoriaDerrota;
    ListView lista;
    TextView tvMontonesDeNada;
    PremioDueloFragmentVM vm;
    Observer<Integer> premioObserver;
    ProgressBar progress;

    public PremioDueloFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premio, container, false);

        final boolean victoria = getArguments().getBoolean("victoria");
        final int idOponente = getArguments().getInt("idOponente");

        btnCazarDeNuevo = view.findViewById(R.id.btnCazarDeNuevo);
        tvVictoriaDerrota = view.findViewById(R.id.tvVictoriaDerrota);
        tvMontonesDeNada = view.findViewById(R.id.tvMontonesDeNada);
        lista = view.findViewById(R.id.lista);
        progress = view.findViewById(R.id.progress);

        btnCazarDeNuevo.setOnClickListener(this);
        btnCazarDeNuevo.setText(R.string.duelo_de_nuevo);

        vm = ViewModelProviders.of(this).get(PremioDueloFragmentVM.class);

        premioObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer honor) {
                if(honor != null){
                    Material[] materiales = {new Material(0, "honor", honor)};
                    if(victoria){
                        tvVictoriaDerrota.setText(getString(R.string.victoria));

                    }else{
                        tvVictoriaDerrota.setText(getString(R.string.derrota));
                    }
                    lista.setAdapter(new AdaptadorPremio<>(getContext(),
                            R.layout.fila_material, R.id.tvVictoriaDerrota, materiales, true));
                    progress.setVisibility(View.GONE);
                }
            }
        };

        vm.getLdPremio().observe(this, premioObserver);

        vm.obtenerPremio(idOponente);

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new LobbyDueloFragment());
    }


}
