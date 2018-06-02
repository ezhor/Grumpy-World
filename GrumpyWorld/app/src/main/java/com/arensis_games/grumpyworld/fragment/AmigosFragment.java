package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorAmigos;
import com.arensis_games.grumpyworld.adapter.AdaptadorPeticionesAmistad;
import com.arensis_games.grumpyworld.model.ListadoAmigosCompleto;
import com.arensis_games.grumpyworld.viewmodel.AmigosFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

public class AmigosFragment extends Fragment implements View.OnClickListener {

    private AmigosFragmentVM vm;
    private Observer<ListadoAmigosCompleto> amigosObserver;
    private Observer<String> errorObserver;
    private TextView tvPeticiones;
    private ListView listaPeticiones;
    private TextView tvAmigos;
    private ListView listaAmigos;
    private ImageButton ibBuscar;
    private TextView tvBuscar;
    private AmigosFragment thisFragment = this;
    private ProgressBar progress;

    public AmigosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amigos, container, false);

        tvPeticiones = view.findViewById(R.id.tvPeticionesAmistad);
        listaPeticiones = view.findViewById(R.id.listaPeticiones);
        tvAmigos = view.findViewById(R.id.tvAmigos);
        listaAmigos = view.findViewById(R.id.listaAmigos);
        tvBuscar = view.findViewById(R.id.tvBuscar);
        ibBuscar = view.findViewById(R.id.ibBuscar);
        progress = view.findViewById(R.id.progress);

        tvBuscar.setOnClickListener(this);
        ibBuscar.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(AmigosFragmentVM.class);

        amigosObserver = new Observer<ListadoAmigosCompleto>() {
            @Override
            public void onChanged(@Nullable ListadoAmigosCompleto listadoAmigosCompleto) {
                if(listadoAmigosCompleto != null){
                    if(listadoAmigosCompleto.getPeticionesAmistad() == null || listadoAmigosCompleto.getPeticionesAmistad().size() == 0){
                        tvPeticiones.setVisibility(View.GONE);
                        listaPeticiones.setVisibility(View.GONE);
                    }else{
                        tvPeticiones.setVisibility(View.VISIBLE);
                        listaPeticiones.setVisibility(View.VISIBLE);
                        listaPeticiones.setAdapter(new AdaptadorPeticionesAmistad<>(getContext(),R.layout.fila_amigo,
                                R.id.tvNombre, listadoAmigosCompleto.getPeticionesAmistad().toArray(),
                                thisFragment));
                    }
                    if(listadoAmigosCompleto.getAmigosMutuos() == null || listadoAmigosCompleto.getAmigosMutuos().size() == 0){
                        tvAmigos.setVisibility(View.GONE);
                        listaAmigos.setVisibility(View.GONE);
                    }else{
                        tvAmigos.setVisibility(View.VISIBLE);
                        listaAmigos.setVisibility(View.VISIBLE);
                        listaAmigos.setAdapter(new AdaptadorAmigos<>(getContext(),R.layout.fila_amigo,
                                R.id.tvNombre, listadoAmigosCompleto.getAmigosMutuos().toArray(),
                                thisFragment));
                    }
                    progress.setVisibility(View.GONE);
                }
            }
        };
        vm.getLdListadoAmigosCompleto().observe(this, amigosObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);


        vm.obtenerListadoAmigosCompleto();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvBuscar:
            case R.id.ibBuscar:
                ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new BusquedaAmigosFragment());
                break;
            case R.id.ibRechazar:
            case R.id.ibBorrarAmigo:
                vm.borrarAmigo((int)view.getTag());
                break;
            case R.id.ibAceptar:
                vm.agregarAmigo((int)view.getTag());
                break;
        }
    }
}
