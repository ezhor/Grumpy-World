package com.arensis_games.grumpyworld.Fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arensis_games.grumpyworld.Gestoras.GestoraGUI;
import com.arensis_games.grumpyworld.Models.Atributos;
import com.arensis_games.grumpyworld.Models.Entrenamiento;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.EntrenamientoFragmentVM;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;

public class  EntrenamientoFragment extends Fragment implements View.OnClickListener {

    private TextView tvTiempoRestante;
    private TextView tvFuerza;
    private TextView tvConstitucion;
    private TextView tvDestreza;
    private ProgressBar progress;
    private EntrenamientoFragmentVM vm;
    private Observer<Atributos> atributosObserver;
    private Observer<Integer> errorObserver;
    private GestoraGUI gesGUI = new GestoraGUI();
    private int finEntrenamiento;
    private Handler handler = new Handler();
    long milis;
    private ImageButton ibAumentarFuerza;
    private ImageButton ibAumentarConstitucion;
    private ImageButton ibAumentarDestreza;
    private boolean botonesActivados;
    private boolean cargando = false;

    public EntrenamientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrenamiento, container, false);

        ibAumentarFuerza = view.findViewById(R.id.ibAumentarFuerza);
        ibAumentarConstitucion = view.findViewById(R.id.ibAumentarConstitucion);
        ibAumentarDestreza = view.findViewById(R.id.ibAumentarDestreza);

        ibAumentarFuerza.setOnClickListener(this);
        ibAumentarConstitucion.setOnClickListener(this);
        ibAumentarDestreza.setOnClickListener(this);

        desactivarBotones();

        tvTiempoRestante = view.findViewById(R.id.tvTiempoRestante);
        tvFuerza = view.findViewById(R.id.tvFuerza);
        tvConstitucion = view.findViewById(R.id.tvConstitucion);
        tvDestreza = view.findViewById(R.id.tvDestreza);
        progress = view.findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(EntrenamientoFragmentVM.class);

        atributosObserver = new Observer<Atributos>() {
            @Override
            public void onChanged(@Nullable Atributos atributos) {
                if(atributos != null){
                    cargando = false;
                    finEntrenamiento = atributos.getFinEntrenamiento();
                    tvFuerza.setText(String.valueOf(atributos.getFuerza()));
                    tvConstitucion.setText(String.valueOf(atributos.getConstitucion()));
                    tvDestreza.setText(String.valueOf(atributos.getDestreza()));
                    tvTiempoRestante.setText(gesGUI.getTiempoRestanteBonito(getResources(), finEntrenamiento));
                    progress.setVisibility(View.GONE);
                    milis = System.currentTimeMillis();
                    if(finEntrenamiento-(milis/1000) <= 0){
                        activarBotones();
                    }
                    handler.postDelayed(new Runnable(){
                        public void run(){
                            Context context = getContext();
                            if(context != null){
                                tvTiempoRestante.setText(gesGUI.getTiempoRestanteBonito(getResources(), finEntrenamiento));
                                if(!cargando){
                                    milis = System.currentTimeMillis();
                                    if(finEntrenamiento-(milis/1000) <= 0){
                                        if(!botonesActivados){
                                            activarBotones();
                                        }
                                    }else{
                                        if(botonesActivados){
                                            desactivarBotones();
                                        }
                                    }
                                }

                                handler.postDelayed(this, 300); //Corrección de tiempo de ejecución
                            }
                        }
                    }, 300);
                }
            }
        };
        vm.getLdAtributos().observe(this, atributosObserver);

        errorObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);


        vm.obtenerAtributos();

        return view;
    }

    @Override
    public void onClick(View view) {
        if(botonesActivados){
            switch (view.getId()){
                case R.id.ibAumentarFuerza:
                    desactivarBotones();
                    cargando = true;
                    vm.entrenar(new Entrenamiento("fuerza"));
                    break;

                case R.id.ibAumentarConstitucion:
                    desactivarBotones();
                    cargando = true;
                    vm.entrenar(new Entrenamiento("constitucion"));
                    break;

                case R.id.ibAumentarDestreza:
                    desactivarBotones();
                    cargando = true;
                    vm.entrenar(new Entrenamiento("destreza"));
                    break;
            }
        }
    }

    private void desactivarBotones(){
        botonesActivados = false;
        ibAumentarFuerza.setBackgroundResource(R.drawable.boton_desactivado);
        ibAumentarFuerza.setImageResource(R.drawable.icono_aumentar_desactivado);
        ibAumentarConstitucion.setBackgroundResource(R.drawable.boton_desactivado);
        ibAumentarConstitucion.setImageResource(R.drawable.icono_aumentar_desactivado);
        ibAumentarDestreza.setBackgroundResource(R.drawable.boton_desactivado);
        ibAumentarDestreza.setImageResource(R.drawable.icono_aumentar_desactivado);
    }

    private void activarBotones(){
        botonesActivados = true;
        ibAumentarFuerza.setBackgroundResource(R.drawable.boton);
        ibAumentarFuerza.setImageResource(R.drawable.icono_aumentar);
        ibAumentarConstitucion.setBackgroundResource(R.drawable.boton);
        ibAumentarConstitucion.setImageResource(R.drawable.icono_aumentar);
        ibAumentarDestreza.setBackgroundResource(R.drawable.boton);
        ibAumentarDestreza.setImageResource(R.drawable.icono_aumentar);
    }
}
