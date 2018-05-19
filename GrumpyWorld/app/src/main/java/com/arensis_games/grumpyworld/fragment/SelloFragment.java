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

import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.management.GestoraGUI;
import com.arensis_games.grumpyworld.model.Caza;
import com.arensis_games.grumpyworld.model.Enemigo;
import com.arensis_games.grumpyworld.model.Estado;
import com.arensis_games.grumpyworld.model.Rollo;
import com.arensis_games.grumpyworld.model.Turno;
import com.arensis_games.grumpyworld.viewmodel.CazaFragmentVM;
import com.arensis_games.grumpyworld.viewmodel.MainActivityVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelloFragment extends Fragment{
    public SelloFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sello, container, false);
    }
}
