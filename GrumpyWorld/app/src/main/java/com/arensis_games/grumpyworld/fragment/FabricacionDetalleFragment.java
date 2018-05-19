package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Observable;
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
import com.arensis_games.grumpyworld.adapter.AdaptadorFabricacion;
import com.arensis_games.grumpyworld.adapter.AdaptadorFabricacionDetalle;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.EquipableDetalle;
import com.arensis_games.grumpyworld.model.MaterialNecesario;
import com.arensis_games.grumpyworld.viewmodel.FabricacionDetalleFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.FabricacionFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class FabricacionDetalleFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private FabricacionDetalleFragmentVM vm;
    private Observer<EquipableDetalle> equipableDetalleObserver;
    private ListView lista;
    private ImageView ivIcono;
    private TextView tvNombre;
    private TextView tvBonus;
    private TextView tvFuerza;
    private TextView tvDestreza;
    private ProgressBar progress;
    private FabricacionDetalleFragment thisFragment = this;
    private GestoraGUI gesGUI = new GestoraGUI();
    private Observer<Integer> errorObserver;
    private Button btnFabricar;
    private ProgressBar progress2;
    private int id;
    private TextView tvPoseido;

    public FabricacionDetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fabricacion_detalle, container, false);

        id = getArguments().getInt("id");

        lista = view.findViewById(R.id.lista);
        ivIcono = view.findViewById(R.id.ivIcono);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvBonus = view.findViewById(R.id.tvBonus);
        tvFuerza = view.findViewById(R.id.tvFuerza);
        tvDestreza = view.findViewById(R.id.tvDestreza);
        progress = view.findViewById(R.id.progress);
        btnFabricar = view.findViewById(R.id.btnFabricar);
        progress2 = view.findViewById(R.id.progress2);
        tvPoseido = view.findViewById(R.id.tvPoseido);

        btnFabricar.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(FabricacionDetalleFragmentVM.class);

        equipableDetalleObserver = new Observer<EquipableDetalle>() {
            @Override
            public void onChanged(@Nullable EquipableDetalle equipableDetalle) {
                if(equipableDetalle != null){
                    ivIcono.setImageDrawable(gesGUI.getDrawableIconoEquipable(getResources(), equipableDetalle.getTipo()));
                    tvNombre.setText(gesGUI.getNombreEquipable(getResources(), equipableDetalle.getNombre()));
                    if(equipableDetalle.getTipo() == 'A'){
                        tvBonus.setText(getString(R.string.bonus_ataque, equipableDetalle.getBonus()));
                    }else if(equipableDetalle.getTipo() == 'S'){
                        tvBonus.setText(getString(R.string.bonus_defensa, equipableDetalle.getBonus()));
                    }
                    tvFuerza.setText(getString(R.string.fuerza_requerida, equipableDetalle.getFuerzaNecesaria()));
                    tvDestreza.setText(getString(R.string.destreza_requerida, equipableDetalle.getDestrezaNecesaria()));
                    lista.setAdapter(new AdaptadorFabricacionDetalle<>(getContext(),
                            R.layout.fila_mapa, R.id.tvNombre, equipableDetalle.getMaterialesNecesarios()));
                    lista.setOnItemClickListener(thisFragment);
                    if(materialesSuficientes(equipableDetalle.getMaterialesNecesarios()) && !equipableDetalle.isPoseido()){
                        btnFabricar.setVisibility(View.VISIBLE);
                    }else{
                        if(equipableDetalle.isPoseido()){
                           tvPoseido.setVisibility(View.VISIBLE);
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


        vm.getLdEquipableDetalle().observe(this, equipableDetalleObserver);
        vm.obtenerEquipableDetalle(id);

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
