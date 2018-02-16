package com.arensis_games.grumpyworld.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.EntrenamientoFragmentVM;

public class EntrenamientoFragment extends Fragment implements View.OnClickListener {

    private TextView tvTiempoRestante;
    private TextView tvFuerza;
    private TextView tvConstitucion;
    private TextView tvDestreza;
    private ProgressBar progress;
    private EntrenamientoFragmentVM vm;
    private Observer<Atributos> atributosObserver;

    public EntrenamientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrenamiento, container, false);

        view.findViewById(R.id.ibAumentarFuerza).setOnClickListener(this);
        view.findViewById(R.id.ibAumentarConstitucion).setOnClickListener(this);
        view.findViewById(R.id.ibAumentarDestreza).setOnClickListener(this);
        tvTiempoRestante = view.findViewById(R.id.tvTiempoRestante);
        tvFuerza = view.findViewById(R.id.tvFuerza);
        tvConstitucion = view.findViewById(R.id.tvConstitucion);
        tvDestreza = view.findViewById(R.id.tvDestreza);
        progress = view.findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(EntrenamientoFragmentVM.class);

        atributosObserver = new Observer<Atributos>() {
            @Override
            public void onChanged(@Nullable Atributos atributos) {
                long tiempoUnix = System.currentTimeMillis()/1000;
                if(atributos != null){
                    tvFuerza.setText(String.valueOf(atributos.getFuerza()));
                    tvConstitucion.setText(String.valueOf(atributos.getConstitucion()));
                    tvDestreza.setText(String.valueOf(atributos.getDestreza()));
                    tvTiempoRestante.setText(String.valueOf(atributos.getFinEntrenamiento()-tiempoUnix)+" segundos restantes");
                    progress.setVisibility(View.GONE);
                }
            }
        };

        vm.getLdAtributos().observe(this, atributosObserver);
        vm.obtenerAtributos(); //Descomentar cuando el server esté actualizado

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibAumentarFuerza:
                vm.entrenar("fuerza");
                break;

            case R.id.ibAumentarConstitucion:
                vm.entrenar("constitucion");
                break;

            case R.id.ibAumentarDestreza:
                vm.entrenar("destreza");
                break;
        }
    }
}
