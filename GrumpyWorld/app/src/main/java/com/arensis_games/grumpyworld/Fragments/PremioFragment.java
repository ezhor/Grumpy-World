package com.arensis_games.grumpyworld.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;
import com.arensis_games.grumpyworld.ViewModels.PremioFragmentVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class PremioFragment extends Fragment implements View.OnClickListener {
    Button btnCazarDeNuevo;
    PremioFragmentVM vm;
    Observer<Void> premioObserver;

    public PremioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premio, container, false);

        btnCazarDeNuevo = view.findViewById(R.id.btnCazarDeNuevo);

        btnCazarDeNuevo.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(PremioFragmentVM.class);

        premioObserver = new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void premio) {

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
