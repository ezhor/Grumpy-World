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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.adapter.AdaptadorBusquedaAmigos;
import com.arensis_games.grumpyworld.adapter.AdaptadorEquipamiento;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Amigo;
import com.arensis_games.grumpyworld.viewmodel.AmigosFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

public class BusquedaAmigosFragment extends Fragment implements AdapterView.OnItemClickListener, TextView.OnEditorActionListener, View.OnClickListener {

    private AmigosFragmentVM vm;
    private Observer<Amigo[]> busquedaAmigosObserver;
    private Observer<String> errorObserver;
    private ListView lista;
    private TextView tvAmigoNoEncontrado;
    private BusquedaAmigosFragment thisFragment = this;
    private ProgressBar progress;
    private EditText etBuscar;
    private ImageButton ibBuscar;

    public BusquedaAmigosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busqueda_amigos, container, false);

        lista = view.findViewById(R.id.lista);
        progress = view.findViewById(R.id.progress);
        tvAmigoNoEncontrado = view.findViewById(R.id.tvAmigoNoEncontrado);
        etBuscar = view.findViewById(R.id.etBuscar);
        ibBuscar = view.findViewById(R.id.ibBuscar);

        etBuscar.setOnEditorActionListener(this);
        ibBuscar.setOnClickListener(this);

        vm = ViewModelProviders.of(this).get(AmigosFragmentVM.class);

        busquedaAmigosObserver = new Observer<Amigo[]>() {
            @Override
            public void onChanged(@Nullable Amigo[] busqueda) {
                if(busqueda != null){
                    if(busqueda.length > 0){
                        lista.setAdapter(new AdaptadorBusquedaAmigos<>(getContext(),
                                R.layout.fila_fabricacion, R.id.tvNombre, busqueda));
                        lista.setOnItemClickListener(thisFragment);
                    }else{
                        tvAmigoNoEncontrado.setVisibility(View.VISIBLE);
                    }
                    progress.setVisibility(View.GONE);
                }
            }
        };
        vm.getLdListadoUsuariosBusqueda().observe(this, busquedaAmigosObserver);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AdaptadorBusquedaAmigos.ViewHolder holder = (AdaptadorBusquedaAmigos.ViewHolder) view.getTag();
        vm.agregarAmigo(holder.getAmigo().getId());
        Toast.makeText(getContext(), getString(R.string.amigo_anadido, holder.getAmigo().getNombre()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        buscarAmigo();
        return true;
    }

    @Override
    public void onClick(View view) {
        buscarAmigo();
    }

    private void buscarAmigo(){
        String nombre = etBuscar.getText().toString();
        if(nombre.length()>0){
            vm.buscarUsuario(nombre);
            progress.setVisibility(View.VISIBLE);
        }
    }
}
