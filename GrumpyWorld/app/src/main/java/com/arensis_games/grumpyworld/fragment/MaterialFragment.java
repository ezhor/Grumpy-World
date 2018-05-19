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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorFabricacionDetalle;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.EquipableDetalle;
import com.arensis_games.grumpyworld.model.MaterialNecesario;
import com.arensis_games.grumpyworld.model.Supermaterial;
import com.arensis_games.grumpyworld.viewmodel.FabricacionDetalleFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import com.arensis_games.grumpyworld.viewmodel.MaterialFragmentVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private MaterialFragmentVM vm;
    private Observer<Supermaterial> supermaterialObserver;
    private ListView lista;
    private TextView tvNombre;
    private ProgressBar progress;
    private MaterialFragment thisFragment = this;
    private GestoraGUI gesGUI = new GestoraGUI();
    private Observer<Integer> errorObserver;
    private Button btnFabricar;
    private ProgressBar progress2;
    private int id;
    private TextView tvNoSubmateriales;
    private TextView tvTienes;

    public MaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material, container, false);

        id = getArguments().getInt("id");

        lista = view.findViewById(R.id.lista);
        tvNombre = view.findViewById(R.id.tvNombre);
        progress = view.findViewById(R.id.progress);
        btnFabricar = view.findViewById(R.id.btnFabricar);
        progress2 = view.findViewById(R.id.progress2);
        tvNoSubmateriales = view.findViewById(R.id.tvNoSubmateriales);
        tvTienes = view.findViewById(R.id.tvTienes);

        btnFabricar.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(MaterialFragmentVM.class);

        supermaterialObserver = new Observer<Supermaterial>() {
            @Override
            public void onChanged(@Nullable Supermaterial supermaterial) {
                if(supermaterial != null){
                    tvNombre.setText(gesGUI.getNombreMaterial(getResources(), supermaterial.getNombre()));
                    tvTienes.setText(getString(R.string.tienes, supermaterial.getCantidad()));
                    if(supermaterial.getMaterialesNecesarios() == null || supermaterial.getMaterialesNecesarios().length == 0){
                        tvNoSubmateriales.setVisibility(View.VISIBLE);
                    }else{
                        lista.setAdapter(new AdaptadorFabricacionDetalle<>(getContext(),
                                R.layout.fila_mapa, R.id.tvNombre, supermaterial.getMaterialesNecesarios()));
                        lista.setOnItemClickListener(thisFragment);
                        if(materialesSuficientes(supermaterial.getMaterialesNecesarios())){
                            btnFabricar.setVisibility(View.VISIBLE);
                        }
                    }
                    progress.setVisibility(View.GONE);
                    progress2.setVisibility(View.GONE);
                }
            }
        };

        errorObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);


        vm.getLdSupermaterial().observe(this, supermaterialObserver);
        vm.obtenerSupermaterial(id);

        return view;
    }

    private boolean materialesSuficientes(MaterialNecesario[] materialesNecesarios){
        boolean suficientes = true;
        for(int i=0; i<materialesNecesarios.length && suficientes; i++){
            if(materialesNecesarios[i].getCantidad() < materialesNecesarios[i].getCantidadNecesaria()){
                suficientes = false;
            }
        }
        return suficientes;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AdaptadorFabricacionDetalle.ViewHolder holder = (AdaptadorFabricacionDetalle.ViewHolder) view.getTag();
        Fragment fragment = new MaterialFragment();
        Bundle args = new Bundle();
        args.putInt("id", holder.getMaterialNecesario().getId());
        fragment.setArguments(args);
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(fragment);
    }

    @Override
    public void onClick(View view) {
        btnFabricar.setVisibility(View.INVISIBLE);
        progress2.setVisibility(View.VISIBLE);
        vm.fabricar(id);
    }
}
