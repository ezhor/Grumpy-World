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

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorEquipamiento;
import com.arensis_games.grumpyworld.adapter.AdaptadorFabricacion;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Equipable;
import com.arensis_games.grumpyworld.model.EquipablePoseido;
import com.arensis_games.grumpyworld.viewmodel.EquipamientoFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.FabricacionFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

public class EquipamientoFragment extends Fragment implements AdapterView.OnItemClickListener{

    private EquipamientoFragmentVM vm;
    private Observer<EquipablePoseido[]> equipablesObserver;
    private Observer<String> errorObserver;
    private ListView lista;
    private TextView texto;
    private EquipamientoFragment thisFragment = this;
    private GestoraGUI gesGUI = new GestoraGUI();
    private ProgressBar progress;
    private TextView tvNadaFabricado;

    public EquipamientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipamiento, container, false);

        lista = view.findViewById(R.id.lista);
        texto = view.findViewById(R.id.texto);
        progress = view.findViewById(R.id.progress);
        tvNadaFabricado = view.findViewById(R.id.tvNadaFabricado);

        vm = ViewModelProviders.of(this).get(EquipamientoFragmentVM.class);

        equipablesObserver = new Observer<EquipablePoseido[]>() {
            @Override
            public void onChanged(@Nullable EquipablePoseido[] equipables) {
                if(equipables != null){
                    if(equipables.length > 0){
                        lista.setAdapter(new AdaptadorEquipamiento<>(getContext(),
                                R.layout.fila_fabricacion, R.id.tvNombre, equipables));
                        lista.setOnItemClickListener(thisFragment);
                    }else{
                        tvNadaFabricado.setVisibility(View.VISIBLE);
                    }

                    progress.setVisibility(View.GONE);
                }
            }
        };
        vm.getLdEquipables().observe(this, equipablesObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);


        vm.obtenerEquipablesPoseidos();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AdaptadorEquipamiento.ViewHolder holder = (AdaptadorEquipamiento.ViewHolder) view.getTag();
        Fragment fragment = new EquipamientoDetalleFragment();
        Bundle args = new Bundle();
        args.putInt("id", holder.getEquipable().getId());
        fragment.setArguments(args);
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(fragment);
    }
}
