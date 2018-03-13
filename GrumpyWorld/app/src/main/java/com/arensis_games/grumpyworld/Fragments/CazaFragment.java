package com.arensis_games.grumpyworld.Fragments;


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

import com.arensis_games.grumpyworld.Gestoras.GestoraGUI;
import com.arensis_games.grumpyworld.Models.Caza;
import com.arensis_games.grumpyworld.Models.Enemigo;
import com.arensis_games.grumpyworld.Models.Estado;
import com.arensis_games.grumpyworld.Models.Rollo;
import com.arensis_games.grumpyworld.Models.Turno;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.CazaFragmentVM;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class CazaFragment extends Fragment implements View.OnClickListener {
    CazaFragmentVM vm;
    Observer<Caza> cazaObserver;
    Observer<Estado> estadoObserver;
    Observer<Integer> errorObserver;
    GestoraGUI gesGUI = new GestoraGUI();

    RelativeLayout rlFondo;

    ProgressBar progress;
    ProgressBar progress2;

    TextView tvNombreRollo;
    ProgressBar barraVidaRollo;
    ImageView ivRangoRollo;
    TextView tvNivelRollo;
    ImageView ivAtaqueRollo;

    TextView tvNombreEnemigo;
    ProgressBar barraVidaEnemigo;
    ImageView ivRangoEnemigo;
    TextView tvNivelEnemigo;
    ImageView ivAtaqueEnemigo;

    ImageView ivSombreroRollo;
    ImageView ivArmaRollo;

    ImageView ivEnemigo;

    ImageView btnAtaque;
    ImageView btnEspecial;
    ImageView btnContra;

    Caza cazaActual;
    boolean enemigoEsMasRapido;
    private Handler handler = new Handler();
    boolean cargando = true;
    boolean botonesActivos = true;

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

        estadoObserver = new Observer<Estado>() {
            @Override
            public void onChanged(@Nullable Estado estado) {
                if(estado != null){
                    cazaActual.setEstado(estado);
                    ivAtaqueRollo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estado.getAtaqueRollo()));
                    ivAtaqueEnemigo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estado.getAtaqueEnemigo()));
                    if(enemigoEsMasRapido){
                        barraVidaRollo.setProgress(estado.getVidaRollo());
                    }else{
                        barraVidaEnemigo.setProgress(estado.getVidaEnemigo());
                    }
                    cargando = false;
                    progress2.setVisibility(View.INVISIBLE);
                }
            }
        };

        vm.getLdEstado().observe(this, estadoObserver);

        errorObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer error) {
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
        Estado estado = caza.getEstado();

        rlFondo.setBackgroundDrawable(gesGUI.getDrawableZonaByNombre(getResources(), rollo.getZona()));

        tvNombreRollo.setText(rollo.getNombre());
        ivRangoRollo.setImageDrawable(gesGUI.getDrawableRango(getResources(), rollo.getRango()));
        tvNivelRollo.setText(getString(R.string.nivel, rollo.getNivel()));

        tvNombreEnemigo.setText(gesGUI.getNombreCortoEnemigo(getResources(), enemigo.getNombre()));
        ivRangoEnemigo.setImageDrawable(gesGUI.getDrawableRango(getResources(), enemigo.isJefe()));
        tvNivelEnemigo.setText(getString(R.string.nivel, enemigo.getNivel()));

        ivSombreroRollo.setImageDrawable(gesGUI.getDrawableSombreroByNombre(getResources(), rollo.getSombrero()));
        ivArmaRollo.setImageDrawable(gesGUI.getDrawableArmaByNombre(getResources(), rollo.getArma()));

        ivEnemigo.setImageDrawable(gesGUI.getDrawableEnemigo(getResources(), enemigo.getNombre()));

        barraVidaRollo.setProgress(estado.getVidaRollo());
        barraVidaRollo.setSecondaryProgress(estado.getVidaRollo());
        ivAtaqueRollo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estado.getAtaqueRollo()));
        barraVidaEnemigo.setProgress(estado.getVidaEnemigo());
        barraVidaEnemigo.setSecondaryProgress(estado.getVidaEnemigo());
        ivAtaqueEnemigo.setImageDrawable(gesGUI.getDrawableAtaque(getResources(), estado.getAtaqueEnemigo()));

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
        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(new PremioFragment());
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
