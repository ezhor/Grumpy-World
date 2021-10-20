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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.EquipablePoseidoDetalle;
import com.arensis_games.grumpyworld.viewmodel.EquipamientoDetalleFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipamientoDetalleFragment extends Fragment implements View.OnClickListener {
    private EquipamientoDetalleFragmentVM vm;
    private Observer<EquipablePoseidoDetalle> equipableDetalleObserver;
    private ImageView ivIcono;
    private TextView tvNombre;
    private TextView tvBonus;
    private TextView tvFuerza;
    private TextView tvDestreza;
    private ProgressBar progress;
    private EquipamientoDetalleFragment thisFragment = this;
    private GestoraGUI gesGUI = new GestoraGUI();
    private Observer<String> errorObserver;
    private Button btnEquipar;
    private ProgressBar progress2;
    private int id;
    private Observer<Boolean> equipadoObserver;

    public EquipamientoDetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipamiento_detalle, container, false);

        id = getArguments().getInt("id");

        ivIcono = view.findViewById(R.id.ivIcono);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvBonus = view.findViewById(R.id.tvBonus);
        tvFuerza = view.findViewById(R.id.tvFuerza);
        tvDestreza = view.findViewById(R.id.tvDestreza);
        progress = view.findViewById(R.id.progress);
        progress2 = view.findViewById(R.id.progress2);
        btnEquipar = view.findViewById(R.id.btnEquipar);

        btnEquipar.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(EquipamientoDetalleFragmentVM.class);

        equipableDetalleObserver = new Observer<EquipablePoseidoDetalle>() {
            @Override
            public void onChanged(@Nullable EquipablePoseidoDetalle equipableDetalle) {
                if (equipableDetalle != null) {
                    ivIcono.setImageDrawable(gesGUI.getDrawableIconoEquipable(getResources(), equipableDetalle.getTipo()));
                    tvNombre.setText(gesGUI.getNombreEquipable(getResources(), equipableDetalle.getNombre()));
                    if (equipableDetalle.getTipo() == 'A') {
                        tvBonus.setText(getString(R.string.bonus_ataque, equipableDetalle.getBonus()));
                    } else if (equipableDetalle.getTipo() == 'S') {
                        tvBonus.setText(getString(R.string.bonus_defensa, equipableDetalle.getBonus()));
                    }
                    tvFuerza.setText(getString(R.string.fuerza_requerida, equipableDetalle.getFuerzaNecesaria()));
                    if(!equipableDetalle.isFuerzaSuficiente()){
                        tvFuerza.setTextColor(getResources().getColor(R.color.rojo));
                    }
                    if(!equipableDetalle.isDestrezaSuficiente()){
                        tvDestreza.setTextColor(getResources().getColor(R.color.rojo));
                    }
                    tvDestreza.setText(getString(R.string.destreza_requerida, equipableDetalle.getDestrezaNecesaria()));
                    progress.setVisibility(View.GONE);
                    progress2.setVisibility(View.GONE);
                    if(!equipableDetalle.isEquipado() && equipableDetalle.isFuerzaSuficiente() && equipableDetalle.isDestrezaSuficiente()){
                        btnEquipar.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        vm.getLdEquipableDetalle().observe(this, equipableDetalleObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (error != null) {
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);

        equipadoObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean equipado) {
                if (equipado != null && equipado) {
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new EquipamientoFragment());
                }
            }
        };
        vm.getLdEquipado().observe(this, equipadoObserver);

        vm.obtenerEquipablePoseidoDetalle(id);

        return view;
    }

    @Override
    public void onClick(View view) {
        btnEquipar.setVisibility(View.INVISIBLE);
        progress2.setVisibility(View.VISIBLE);
        vm.equipar(id);
    }
}
