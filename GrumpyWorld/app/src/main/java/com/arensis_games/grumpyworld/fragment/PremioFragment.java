package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.adapter.AdaptadorPremio;
import com.arensis_games.grumpyworld.model.Material;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import com.arensis_games.grumpyworld.viewmodel.PremioFragmentVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class PremioFragment extends Fragment implements View.OnClickListener {
    Button btnCazarDeNuevo;
    TextView tvVictoriaDerrota;
    ListView lista;
    TextView tvMontonesDeNada;
    PremioFragmentVM vm;
    Observer<Material[]> premioObserver;
    ProgressBar progress;

    public PremioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premio, container, false);

        btnCazarDeNuevo = view.findViewById(R.id.btnCazarDeNuevo);
        tvVictoriaDerrota = view.findViewById(R.id.tvVictoriaDerrota);
        tvMontonesDeNada = view.findViewById(R.id.tvMontonesDeNada);
        lista = view.findViewById(R.id.lista);
        progress = view.findViewById(R.id.progress);

        btnCazarDeNuevo.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(PremioFragmentVM.class);

        premioObserver = new Observer<Material[]>() {
            @Override
            public void onChanged(@Nullable Material[] materiales) {
                if(materiales != null){
                    if(materiales.length > 0){
                        tvVictoriaDerrota.setText(getString(R.string.victoria));
                        lista.setAdapter(new AdaptadorPremio<>(getContext(),
                                R.layout.fila_material, R.id.tvVictoriaDerrota, materiales));
                    }else{
                        tvVictoriaDerrota.setText(getString(R.string.derrota));
                        lista.setVisibility(View.INVISIBLE);
                        tvMontonesDeNada.setVisibility(View.VISIBLE);
                    }
                    progress.setVisibility(View.GONE);
                }
            }
        };

        vm.getLdPremio().observe(this, premioObserver);

        vm.obtenerPremio();

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new CazaFragment());
    }


}
