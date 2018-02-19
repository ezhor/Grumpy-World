package com.arensis_games.grumpyworld;

import android.content.Context;
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

    public String getTiempoRestanteBonito(Context context, int finEntrenamiento){
        String tiempoBonito = "";
        int tiempoEnSegundos = (int)(finEntrenamiento - (System.currentTimeMillis()/1000));
        int segundosEnUnMinuto = 60;
        int segundosEnUnaHora = segundosEnUnMinuto * 60;
        int segundosEnUnDia = segundosEnUnaHora * 24;
        int segundosEnUnMes = segundosEnUnDia * 30;
        int segundosEnUnAno = segundosEnUnDia * 365;
        int tiempo1;
        int tiempo2;
        float tiempoDecimal;

        if(tiempoEnSegundos > 0){
            if(tiempoEnSegundos >= segundosEnUnAno){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnAno;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnAno)/segundosEnUnMes);
                tiempoBonito = context.getString(R.string.quedan_anos_meses, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnMes){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMes;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnMes)/segundosEnUnDia);
                tiempoBonito = context.getString(R.string.quedan_meses_dias, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnDia){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnDia;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnDia)/segundosEnUnaHora);
                tiempoBonito = context.getString(R.string.quedan_dias_horas, tiempo1, tiempo2);
                }
            else if(tiempoEnSegundos >= segundosEnUnaHora){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnaHora;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnaHora)/segundosEnUnMinuto);
                tiempoBonito = context.getString(R.string.quedan_horas_minutos, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnMinuto){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMinuto;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)((tiempoDecimal-tiempo1)*segundosEnUnMinuto);
                tiempoBonito = context.getString(R.string.quedan_minutos_segundos, tiempo1, tiempo2);
            }
            else{
                tiempoBonito = context.getString(R.string.quedan_segundos, tiempoEnSegundos);
            }
        }

        return tiempoBonito;
    }

    public Drawable getDrawableIconoMenu(Resources resources, String elemento) {
        Drawable drawable = null;

        if(elemento != null){
            if(elemento.equals(resources.getString(R.string.menu_entrenamiento))) {
                drawable = resources.getDrawable(R.drawable.icono_entrenamiento);
                }
            else if(elemento.equals(resources.getString(R.string.menu_caza))) {
                    drawable = resources.getDrawable(R.drawable.icono_caza);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_mapa))) {
                    drawable = resources.getDrawable(R.drawable.icono_mapa);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_sello))) {
                    drawable = resources.getDrawable(R.drawable.icono_sello);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_amigos))) {
                    drawable = resources.getDrawable(R.drawable.icono_amigos);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_duelo))) {
                    drawable = resources.getDrawable(R.drawable.icono_duelo);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_ranking))) {
                    drawable = resources.getDrawable(R.drawable.icono_ranking);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_fabricacion))) {
                    drawable = resources.getDrawable(R.drawable.icono_fabricacion);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_equipamiento))) {
                    drawable = resources.getDrawable(R.drawable.icono_equipamiento);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_historia))) {
                    drawable = resources.getDrawable(R.drawable.icono_historia);
                    }
            else if(elemento.equals(resources.getString(R.string.menu_cerrar_sesion))) {
                    drawable = resources.getDrawable(R.drawable.icono_cerrar_sesion);
                    }
            }

        return drawable;
    }
}
