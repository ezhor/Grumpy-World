package com.arensis_games.grumpyworld.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arensis_games.grumpyworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditsFragment extends Fragment{
    public CreditsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }
}