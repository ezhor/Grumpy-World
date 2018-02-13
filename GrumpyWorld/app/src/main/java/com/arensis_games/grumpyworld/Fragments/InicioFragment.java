package com.arensis_games.grumpyworld.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arensis_games.grumpyworld.GestoraGUI;
import com.arensis_games.grumpyworld.R;
import com.arensis_games.grumpyworld.ViewModels.MainActivityVM;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {
    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/


    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(){//String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivityVM vmMain = ViewModelProviders.of(getActivity()).get(MainActivityVM.class);
        GestoraGUI gesGUI = new GestoraGUI();
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        ImageView ivSombrero = view.findViewById(R.id.ivSombrero);
        ImageView ivArma = view.findViewById(R.id.ivArma);
        RelativeLayout relativePrincipal = view.findViewById(R.id.relativePrincipal);

        ivSombrero.setImageDrawable(gesGUI.getDrawableSombreroByNombre(getResources(),  vmMain.getRollo().getSombrero()));
        ivArma.setImageDrawable(gesGUI.getDrawableArmaByNombre(getResources(), vmMain.getRollo().getArma()));
        relativePrincipal.setBackgroundDrawable(gesGUI.getDrawableZonaByNombre(getResources(), vmMain.getRollo().getZona()));

        return view;
    }

}
