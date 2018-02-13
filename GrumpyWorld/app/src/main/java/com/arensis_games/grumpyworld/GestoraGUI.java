package com.arensis_games.grumpyworld;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by ezhor on 13/02/2018.
 */

public class GestoraGUI {
    public Drawable getDrawableSombreroByNombre(Resources resources, String nombre){
        Drawable drawable = null;
        if(nombre != null){
            switch (nombre){
                case "casco_obra":
                    drawable = resources.getDrawable(R.drawable.casco_obra);
                    break;
            }
        }
        return drawable;
    }

    public Drawable getDrawableArmaByNombre(Resources resources, String nombre) {
        Drawable drawable = null;
        if(nombre != null){
            switch (nombre){
                case "tenedor":
                    drawable = resources.getDrawable(R.drawable.tenedor);
                    break;
            }
        }
        return drawable;
    }

    public Drawable getDrawableZonaByNombre(Resources resources, String nombre) {
        Drawable drawable = null;
        if(nombre != null){
            switch (nombre){
                case "bano":
                    drawable = resources.getDrawable(R.drawable.fondo_bano);
                    break;
                case "oficina":
                    drawable = resources.getDrawable(R.drawable.fondo_oficina);
                    break;
            }
        }
        return drawable;
    }
}
