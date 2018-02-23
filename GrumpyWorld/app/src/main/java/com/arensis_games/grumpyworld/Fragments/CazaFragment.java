package com.arensis_games.grumpyworld.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arensis_games.grumpyworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CazaFragment extends Fragment {


    public CazaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caza, container, false);
    }

}
