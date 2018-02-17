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

    public String getTiempoRestanteBonito(int finEntrenamiento){
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
                tiempoBonito = "Quedan "+tiempo1+" años y "+tiempo2+" meses";
            }
            else if(tiempoEnSegundos >= segundosEnUnMes){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMes;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnMes)/segundosEnUnDia);
                tiempoBonito = "Quedan "+tiempo1+" meses y "+tiempo2+" días";
            }
            else if(tiempoEnSegundos >= segundosEnUnDia){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnDia;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnDia)/segundosEnUnaHora);
                tiempoBonito = "Quedan "+tiempo1+" días y "+tiempo2+" horas";
                }
            else if(tiempoEnSegundos >= segundosEnUnaHora){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnaHora;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnaHora)/segundosEnUnMinuto);
                tiempoBonito = "Quedan "+tiempo1+" horas y "+tiempo2+" minutos";
            }
            else if(tiempoEnSegundos >= segundosEnUnMinuto){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMinuto;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)((tiempoDecimal-tiempo1)*segundosEnUnMinuto);
                tiempoBonito = "Quedan "+tiempo1+" minutos y "+tiempo2+" segundos";
            }
            else{
                tiempoBonito = "Quedan "+tiempoEnSegundos+" segundos";
            }
        }

        return tiempoBonito;
    }

    public Drawable getDrawableIconoMenu(Resources resources, String elemento) {
        Drawable drawable = null;

        if(elemento != null){
            switch (elemento){
                case "Entrenamiento":
                    drawable = resources.getDrawable(R.drawable.icono_entrenamiento);
                    break;
                case "Caza":
                    drawable = resources.getDrawable(R.drawable.icono_caza);
                    break;
                case "Mapa":
                    drawable = resources.getDrawable(R.drawable.icono_mapa);
                    break;
                case "??????":
                    drawable = resources.getDrawable(R.drawable.icono_sello);
                    break;
                case "Amigos":
                    drawable = resources.getDrawable(R.drawable.icono_amigos);
                    break;
                case "Duelo":
                    drawable = resources.getDrawable(R.drawable.icono_duelo);
                    break;
                case "Ranking":
                    drawable = resources.getDrawable(R.drawable.icono_ranking);
                    break;
                case "Fabricación":
                    drawable = resources.getDrawable(R.drawable.icono_fabricacion);
                    break;
                case "Equipamiento":
                    drawable = resources.getDrawable(R.drawable.icono_equipamiento);
                    break;
                case "Historia":
                    drawable = resources.getDrawable(R.drawable.icono_historia);
                    break;
                case "Cerrar sesión":
                    drawable = resources.getDrawable(R.drawable.icono_cerrar_sesion);
                    break;
            }
        }

        return drawable;
    }
}
