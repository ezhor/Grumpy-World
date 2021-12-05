package com.arensis_games.grumpyworld.fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorBusquedaAmigos;
import com.arensis_games.grumpyworld.adapter.AdaptadorPeticionesAmistad;
import com.arensis_games.grumpyworld.model.LobbyDuelo;
import com.arensis_games.grumpyworld.viewmodel.DueloFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;
import android.os.Handler;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;

public class LobbyDueloFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private DueloFragmentVM vm;
    private Observer<LobbyDuelo> lobbyDueloObserver;
    private Observer<String> errorObserver;
    private LinearLayout llRetos;
    private ListView listaRetos;
    private LinearLayout llRanked;
    private ListView listaRanked;
    private LinearLayout llAmistoso;
    private ListView listaAmistoso;
    private LobbyDueloFragment thisFragment = this;
    private ProgressBar progress;
    private Handler handler = new Handler();
    private static Socket socket;

    public LobbyDueloFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lobby_duelo, container, false);

        llRetos = view.findViewById(R.id.llRetos);
        listaRetos = view.findViewById(R.id.listaRetos);
        llRanked = view.findViewById(R.id.llRanked);
        listaRanked = view.findViewById(R.id.listaRanked);
        llAmistoso = view.findViewById(R.id.llAmistoso);
        listaAmistoso = view.findViewById(R.id.listaAmistoso);
        progress = view.findViewById(R.id.progress);

        listaRanked.setOnItemClickListener(this);
        listaAmistoso.setOnItemClickListener(this);

        vm = ViewModelProviders.of(this).get(DueloFragmentVM.class);

        lobbyDueloObserver = new Observer<LobbyDuelo>() {
            @Override
            public void onChanged(@Nullable LobbyDuelo lobbyDuelo) {
                if(lobbyDuelo != null){
                    if(lobbyDuelo.getEnDueloCon() != null){
                        DueloFragment dueloFragment = new DueloFragment();
                        Bundle args = new Bundle();
                        args.putInt("id", lobbyDuelo.getEnDueloCon());
                        dueloFragment.setArguments(args);
                        ViewModelProviders.of(getActivity()).get(MainActivityVM.class).cambiarFragment(dueloFragment);
                    }else{
                        if(lobbyDuelo.getRetosDuelo() == null || lobbyDuelo.getRetosDuelo().length == 0){
                            llRetos.setVisibility(View.GONE);
                        }else{
                            llRetos.setVisibility(View.VISIBLE);
                            listaRetos.setAdapter(new AdaptadorPeticionesAmistad<>(getContext(),R.layout.fila_amigo,
                                    R.id.tvNombre, lobbyDuelo.getRetosDuelo(),
                                    thisFragment));
                        }
                        if(lobbyDuelo.getAmigosRanked() == null || lobbyDuelo.getAmigosRanked().length == 0){
                            llRanked.setVisibility(View.GONE);
                        }else{
                            llRanked.setVisibility(View.VISIBLE);
                            listaRanked.setAdapter(new AdaptadorBusquedaAmigos<>(getContext(),R.layout.fila_amigo,
                                    R.id.tvNombre, lobbyDuelo.getAmigosRanked()));
                        }
                        if(lobbyDuelo.getAmigosAmistoso() == null || lobbyDuelo.getAmigosAmistoso().length == 0){
                            llAmistoso.setVisibility(View.GONE);
                        }else{
                            llAmistoso.setVisibility(View.VISIBLE);
                            listaAmistoso.setAdapter(new AdaptadorBusquedaAmigos<>(getContext(),R.layout.fila_amigo,
                                    R.id.tvNombre, lobbyDuelo.getAmigosAmistoso()));
                        }
                        progress.setVisibility(View.GONE);
                    }
                }
            }
        };
        vm.getLdLobbyDuelo().observe(this, lobbyDueloObserver);

        errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if(error != null){
                    ViewModelProviders.of(getActivity()).get(MainActivityVM.class).emitirErrorGlobal(error);
                }
            }
        };
        vm.getLdError().observe(this, errorObserver);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibAceptar:
                vm.retarADuelo((int)view.getTag());
                break;
            case R.id.ibRechazar:
                vm.rechazarDuelo((int)view.getTag());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AdaptadorBusquedaAmigos.ViewHolder holder = (AdaptadorBusquedaAmigos.ViewHolder) view.getTag();
        vm.retarADuelo(holder.getAmigo().getId());
        Toast.makeText(getContext(), getString(R.string.has_retado_a, holder.getAmigo().getNombre()), Toast.LENGTH_SHORT).show();
    }
}
