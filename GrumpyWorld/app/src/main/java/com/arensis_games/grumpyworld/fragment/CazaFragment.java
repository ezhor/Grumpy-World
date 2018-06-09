package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Caza;
import com.arensis_games.grumpyworld.model.Enemigo;
import com.arensis_games.grumpyworld.model.EstadoCaza;
import com.arensis_games.grumpyworld.model.Rollo;
import com.arensis_games.grumpyworld.model.Turno;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.viewmodel.CazaFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class CazaFragment extends Fragment implements View.OnClickListener {
    private CazaFragmentVM vm;
    private Observer<Caza> cazaObserver;
    private Observer<EstadoCaza> estadoObserver;
    private Observer<String> errorObserver;
    private GestoraGUI gesGUI = new GestoraGUI();

    private RelativeLayout rlFondo;

    private ProgressBar progress;
    private ProgressBar progress2;

    private TextView tvNombreRollo;
    private ProgressBar barraVidaRollo;
    private ImageView ivRangoRollo;
    private TextView tvNivelRollo;
    private ImageView ivAtaqueRollo;

    private TextView tvNombreEnemigo;
    private ProgressBar barraVidaEnemigo;
    private ImageView ivRangoEnemigo;
    private TextView tvNivelEnemigo;
    private ImageView ivAtaqueEnemigo;

    private ImageView ivSombreroRollo;
    private ImageView ivArmaRollo;

    private ImageView ivEnemigo;

    private ImageView btnAtaque;
    private ImageView btnEspecial;
    private ImageView btnContra;

    private Caza cazaActual;
    private boolean enemigoEsMasRapido;
    private Handler handler = new Handler();
    private boolean cargando = true;
    private boolean botonesActivos = true;

    public CazaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caza, container, false);

        rlFondo = view.findViewById(R.id.rlFondo);

        progress = view.findViewById(R.id.progress);
        progress2 = view.findViewById(R.id.progress2);

        tvNombreRollo = view.findViewById(R.id.tvNombreRollo);
        barraVidaRollo = view.findViewById(R.id.barraVidaRollo);
        ivRangoRollo = view.findViewById(R.id.ivRangoRollo);
        tvNivelRollo = view.findViewById(R.id.tvNivelRollo);
        ivAtaqueRollo = view.findViewById(R.id.ivAtaqueRollo);

        tvNombreEnemigo = view.findViewById(R.id.tvNombreEnemigo);
        barraVidaEnemigo = view.findViewById(R.id.barraVidaEnemigo);
        ivRangoEnemigo = view.findViewById(R.id.ivRangoEnemigo);
        tvNivelEnemigo = view.findViewById(R.id.tvNivelEnemigo);
        ivAtaqueEnemigo = view.findViewById(R.id.ivAtaqueEnemigo);

        ivSombreroRollo = view.findViewById(R.id.ivSombreroRollo);
        ivArmaRollo = view.findViewById(R.id.ivArmaRollo);

        ivEnemigo = view.findViewById(R.id.ivEnemigo);

        btnAtaque = view.findViewById(R.id.btnAtaque);
        btnEspecial = view.findViewById(R.id.btnEspecial);
        btnContra = view.findViewById(R.id.btnContra);

        btnAtaque.setOnClickListener(this);
        btnEspecial.setOnClickListener(this);
        btnContra.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(CazaFragmentVM.class);

        cazaObserver = new Observer<Caza>() {
            @Override
            public void onChanged(@Nullable Caza caza) {
                if(caza != null){
                    cazaActual = caza;
                    actualizarVistaCaza(caza);
                    cargando = false;
                }
            }
        };
        vm.getLdCaza().observe(this, cazaObserver);

        estadoObserver = new Observer<EstadoCaza>() {
            @Override
            public void onChanged(@Nullable EstadoCaza estadoCaza) {
                if(estadoCaza != null){
                    cazaActual.setEstado(estadoCaza);
                    ivAtaqueRollo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estadoCaza.getAtaqueRollo()));
                    ivAtaqueEnemigo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estadoCaza.getAtaqueEnemigo()));
                    if(enemigoEsMasRapido){
                        barraVidaRollo.setProgress(estadoCaza.getVidaRollo());
                    }else{
                        barraVidaEnemigo.setProgress(estadoCaza.getVidaEnemigo());
                    }
                    cargando = false;
                    progress2.setVisibility(View.INVISIBLE);
                }
            }
        };

        vm.getLdEstado().observe(this, estadoObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };

        vm.getLdError().observe(this, errorObserver);

        //Animación de barras de vida
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!cargando){
                    if(enemigoEsMasRapido){
                        if(ivAtaqueEnemigo.getVisibility() != View.VISIBLE){
                            ivAtaqueEnemigo.setVisibility(View.VISIBLE);
                        }
                        if(barraVidaRollo.getProgress() < barraVidaRollo.getSecondaryProgress()){
                            barraVidaRollo.setSecondaryProgress(barraVidaRollo.getSecondaryProgress()-1);
                        }else{
                            if(ivAtaqueRollo.getVisibility() != View.VISIBLE){
                                ivAtaqueRollo.setVisibility(View.VISIBLE);
                                barraVidaEnemigo.setProgress(cazaActual.getEstado().getVidaEnemigo());
                            }
                            if(barraVidaEnemigo.getProgress() < barraVidaEnemigo.getSecondaryProgress()){
                                barraVidaEnemigo.setSecondaryProgress(barraVidaEnemigo.getSecondaryProgress()-1);
                            }else{
                                if(cazaActual.getEstado().getVidaRollo() == 0 || cazaActual.getEstado().getVidaEnemigo() == 0){
                                    mostrarPremios();
                                    handler = null;
                                }else{
                                    activarBotones();
                                }
                            }
                        }
                    }else{
                        if(ivAtaqueRollo.getVisibility() != View.VISIBLE){
                            ivAtaqueRollo.setVisibility(View.VISIBLE);
                        }
                        if(barraVidaEnemigo.getProgress() < barraVidaEnemigo.getSecondaryProgress()){
                            barraVidaEnemigo.setSecondaryProgress(barraVidaEnemigo.getSecondaryProgress()-1);
                        }else{
                            if(ivAtaqueEnemigo.getVisibility() != View.VISIBLE){
                                ivAtaqueEnemigo.setVisibility(View.VISIBLE);
                                barraVidaRollo.setProgress(cazaActual.getEstado().getVidaRollo());
                            }
                            if(barraVidaRollo.getProgress() < barraVidaRollo.getSecondaryProgress()){
                                barraVidaRollo.setSecondaryProgress(barraVidaRollo.getSecondaryProgress()-1);
                            }else{
                                if(cazaActual.getEstado().getVidaRollo() == 0 || cazaActual.getEstado().getVidaEnemigo() == 0){
                                    mostrarPremios();
                                    handler = null;
                                }else{
                                    activarBotones();
                                }
                            }
                        }
                    }
                }
                if(handler!=null){
                    handler.postDelayed(this, 20);
                }
            }
        }, 20);

        vm.obtenerCaza();

        return view;
    }

    private void actualizarVistaCaza(Caza caza){
        Rollo rollo = caza.getRollo();
        Enemigo enemigo = caza.getEnemigo();
        EstadoCaza estadoCaza = caza.getEstado();

        rlFondo.setBackgroundDrawable(gesGUI.getDrawableZona(getResources(), rollo.getZona()));

        tvNombreRollo.setText(rollo.getNombre());
        ivRangoRollo.setImageDrawable(gesGUI.getDrawableRango(getResources(), rollo.getRango()));
        ivRangoRollo.setVisibility(View.VISIBLE);

        tvNivelRollo.setText(getString(R.string.nivel, rollo.getNivel()));

        tvNombreEnemigo.setText(gesGUI.getNombreCortoEnemigo(getResources(), enemigo.getNombre()));
        if(enemigo.isJefe()){
            ivRangoEnemigo.setImageDrawable(gesGUI.getDrawableRango(getResources(), enemigo.isJefe()));
            ivRangoEnemigo.setVisibility(View.VISIBLE);
        }
        tvNivelEnemigo.setText(getString(R.string.nivel, enemigo.getNivel()));

        ivSombreroRollo.setImageDrawable(gesGUI.getDrawableSombrero(getResources(), rollo.getSombrero()));
        ivArmaRollo.setImageDrawable(gesGUI.getDrawableArma(getResources(), rollo.getArma()));

        ivEnemigo.setImageDrawable(gesGUI.getDrawableEnemigo(getResources(), enemigo.getNombre()));

        barraVidaRollo.setProgress(estadoCaza.getVidaRollo());
        barraVidaRollo.setSecondaryProgress(estadoCaza.getVidaRollo());
        ivAtaqueRollo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estadoCaza.getAtaqueRollo()));
        barraVidaEnemigo.setProgress(estadoCaza.getVidaEnemigo());
        barraVidaEnemigo.setSecondaryProgress(estadoCaza.getVidaEnemigo());
        ivAtaqueEnemigo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estadoCaza.getAtaqueEnemigo()));

        this.enemigoEsMasRapido = enemigo.isMasRapido();
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if(botonesActivos){
            if(view.getId()==R.id.btnAtaque || view.getId()==R.id.btnEspecial || view.getId()==R.id.btnContra){
                cargando = true;
                desactivarBotones();
                ivAtaqueRollo.setVisibility(View.INVISIBLE);
                ivAtaqueEnemigo.setVisibility(View.INVISIBLE);
                switch (view.getId()){
                    case R.id.btnAtaque:
                        vm.jugarTurno(new Turno((byte)1));
                        break;
                    case R.id.btnEspecial:
                        vm.jugarTurno(new Turno((byte)2));
                        break;
                    case R.id.btnContra:
                        vm.jugarTurno(new Turno((byte)3));
                        break;
                }
            }
        }
    }

    private void mostrarPremios(){
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new PremioCazaFragment());
    }

    private void desactivarBotones(){
        botonesActivos = false;
        btnAtaque.setBackgroundResource(R.drawable.boton_desactivado);
        btnEspecial.setBackgroundResource(R.drawable.boton_desactivado);
        btnContra.setBackgroundResource(R.drawable.boton_desactivado);
    }

    private void activarBotones(){
        botonesActivos = true;
        btnAtaque.setBackgroundResource(R.drawable.boton);
        btnEspecial.setBackgroundResource(R.drawable.boton);
        btnContra.setBackgroundResource(R.drawable.boton);
    }
}
