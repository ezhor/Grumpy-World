package com.arensis_games.grumpyworld.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arensis_games.grumpyworld.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenamientoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrenamientoFragment extends Fragment {

    public EntrenamientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrenamiento, container, false);



        return view;
    }

}
