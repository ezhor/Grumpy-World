package com.arensis_games.grumpyworld.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorBusquedaAmigos;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Amigo;
import com.arensis_games.grumpyworld.model.Ranking;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import com.arensis_games.grumpyworld.viewmodel.RankingFragmentVM;

public class RankingFragment extends Fragment {

    private RankingFragmentVM vm;
    private Observer<Ranking> rankingObserver;
    private Observer<String> errorObserver;
    private ListView lista;
    private RankingFragment thisFragment = this;
    private ProgressBar progress;
    private GestoraGUI gesGUI = new GestoraGUI();

    public RankingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        lista = view.findViewById(R.id.lista);
        progress = view.findViewById(R.id.progress);

        vm = ViewModelProviders.of(this).get(RankingFragmentVM.class);

        rankingObserver = new Observer<Ranking>() {
            @Override
            public void onChanged(@Nullable Ranking ranking) {
                if (ranking != null) {
                    lista.setAdapter(new AdaptadorBusquedaAmigos<>(getContext(),
                            R.layout.fila_busqueda_amigo, R.id.tvNombre, ranking.getTopRanking()));
                    mostrarRankedRollo(view, ranking.getRolloRanked());
                    progress.setVisibility(View.GONE);
                }
            }
        };
        vm.getLdRanking().observe(this, rankingObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (error != null) {
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);

        vm.obtenerRanking();

        return view;
    }

    private void mostrarRankedRollo(View parent, Amigo rollo){
        View filaUsuario = parent.findViewById(R.id.filaUsuario);
        ((ImageView)filaUsuario.findViewById(R.id.ivRango)).setImageDrawable(gesGUI.getDrawableRango(getResources(), rollo.getRango()));
        ((TextView)filaUsuario.findViewById(R.id.tvNombre)).setText(rollo.getNombre());
        ((TextView)filaUsuario.findViewById(R.id.tvHonor)).setText(String.valueOf(rollo.getHonor()));
        ((TextView)filaUsuario.findViewById(R.id.tvNivel)).setText(getString(R.string.nivel, rollo.getNivel()));
    }
}
