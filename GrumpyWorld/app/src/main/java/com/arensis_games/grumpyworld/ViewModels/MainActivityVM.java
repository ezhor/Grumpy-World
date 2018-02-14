package com.arensis_games.grumpyworld.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.arensis_games.grumpyworld.Models.Rollo;

/**
 * Created by ezhor on 13/02/2018.
 */

public class MainActivityVM extends AndroidViewModel {
    private Rollo rollo;
    private SharedPreferences sharedPref = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = sharedPref.edit();

    public MainActivityVM(@NonNull Application application) {
        super(application);
    }

    public Rollo getRollo() {
        return rollo;
    }

    public void setRollo(Rollo rollo) {
        this.rollo = rollo;
    }

    public void cerrarSesion(){
        editor.putString("usuario", "");
        editor.putString("contrasena", "");
        editor.commit();
    }
}
