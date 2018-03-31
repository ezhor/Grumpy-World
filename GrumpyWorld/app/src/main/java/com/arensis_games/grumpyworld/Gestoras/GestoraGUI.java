package com.arensis_games.grumpyworld.Gestoras;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.arensis_games.grumpyworld.R;

/**
 * Created by ezhor on 13/02/2018.
 */

public class GestoraGUI {
    public Drawable getDrawableSombrero(Resources resources, String nombre){
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

    public Drawable getDrawableArma(Resources resources, String nombre) {
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

    public Drawable getDrawableZona(Resources resources, String nombre) {
        Drawable drawable = null;
        if(nombre != null){
            switch (nombre){
                case "bano":
                    drawable = resources.getDrawable(R.drawable.fondo_bano);
                    break;
                case "cocina":
                    drawable = resources.getDrawable(R.drawable.fondo_cocina);
                    break;
                case "oficina":
                    drawable = resources.getDrawable(R.drawable.fondo_oficina);
                    break;
            }
        }
        return drawable;
    }

    public String getTiempoRestanteBonito(Resources resources, int finEntrenamiento){
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
                tiempoBonito = resources.getString(R.string.quedan_anos_meses, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnMes){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMes;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnMes)/segundosEnUnDia);
                tiempoBonito = resources.getString(R.string.quedan_meses_dias, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnDia){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnDia;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnDia)/segundosEnUnaHora);
                tiempoBonito = resources.getString(R.string.quedan_dias_horas, tiempo1, tiempo2);
                }
            else if(tiempoEnSegundos >= segundosEnUnaHora){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnaHora;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)(((tiempoDecimal-tiempo1)*segundosEnUnaHora)/segundosEnUnMinuto);
                tiempoBonito = resources.getString(R.string.quedan_horas_minutos, tiempo1, tiempo2);
            }
            else if(tiempoEnSegundos >= segundosEnUnMinuto){
                tiempoDecimal = tiempoEnSegundos/(float)segundosEnUnMinuto;
                tiempo1 = (int)tiempoDecimal;
                tiempo2 = (int)((tiempoDecimal-tiempo1)*segundosEnUnMinuto);
                tiempoBonito = resources.getString(R.string.quedan_minutos_segundos, tiempo1, tiempo2);
            }
            else{
                tiempoBonito = resources.getString(R.string.quedan_segundos, tiempoEnSegundos);
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
                    drawable = resources.getDrawable(R.drawable.icono_pacto);
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

    public Drawable getDrawableEnemigo(Resources resources, String enemigo) {
        Drawable drawable = null;
        if(enemigo!= null){
            switch(enemigo){
                case "cepillo":
                    drawable = resources.getDrawable(R.drawable.enemigo_cepillo);
                    break;
                case "champu":
                    drawable = resources.getDrawable(R.drawable.enemigo_champu);
                    break;
                case "cuchilla":
                    drawable = resources.getDrawable(R.drawable.enemigo_cuchilla);
                    break;
                case "stripper":
                    drawable = resources.getDrawable(R.drawable.enemigo_stripper);
                    break;
                case "vater":
                    drawable = resources.getDrawable(R.drawable.enemigo_vater);
                    break;

                case "calabaza":
                    drawable = resources.getDrawable(R.drawable.enemigo_calabaza);
                    break;
                case "cuchara":
                    drawable = resources.getDrawable(R.drawable.enemigo_cuchara);
                    break;
                case "leche":
                    drawable = resources.getDrawable(R.drawable.enemigo_leche);
                    break;
                case "limon":
                    drawable = resources.getDrawable(R.drawable.enemigo_limon);
                    break;
                case "zanahoria":
                    drawable = resources.getDrawable(R.drawable.enemigo_zanahoria);
                    break;
            }
        }
        return drawable;
    }

    public Drawable getDrawableRango(Resources resources, int rango){
        Drawable drawable = null;
        if(rango<=10){
            switch (rango){
                case 1:
                    drawable = resources.getDrawable(R.drawable.rango1);
                    break;
                case 2:
                    drawable = resources.getDrawable(R.drawable.rango2);
                    break;
                case 3:
                    drawable = resources.getDrawable(R.drawable.rango3);
                    break;
                case 4:
                    drawable = resources.getDrawable(R.drawable.rango4);
                    break;
                case 5:
                    drawable = resources.getDrawable(R.drawable.rango5);
                    break;
                case 6:
                    drawable = resources.getDrawable(R.drawable.rango6);
                    break;
                case 7:
                    drawable = resources.getDrawable(R.drawable.rango7);
                    break;
                case 8:
                    drawable = resources.getDrawable(R.drawable.rango8);
                    break;
                case 9:
                    drawable = resources.getDrawable(R.drawable.rango9);
                    break;
                case 10:
                    drawable = resources.getDrawable(R.drawable.rango10);
                    break;
            }
        }
        return drawable;
    }

    public Drawable getDrawableRango(Resources resources, boolean esJefe){
        Drawable drawable = null;
        if(esJefe) {
            drawable = resources.getDrawable(R.drawable.icono_jefe);
        }
        return drawable;
    }

    public Drawable getDrawableAtaque(Resources resources, byte ataque){
        Drawable drawable = null;
        if(ataque == 1 || ataque == 2 || ataque == 3){
            switch(ataque){
                case 1:
                    drawable = resources.getDrawable(R.drawable.icono_ataque);
                    break;
                case 2:
                    drawable = resources.getDrawable(R.drawable.icono_especial);
                    break;
                case 3:
                    drawable = resources.getDrawable(R.drawable.icono_contra);
                    break;
            }
        }
        return drawable;
    }

    public String getNombreCortoEnemigo(Resources resources, String enemigo){
        String nombreCorto = "";

        switch(enemigo){
            case "cepillo":
                nombreCorto = resources.getString(R.string.enemigo_cepillo_corto);
                break;
            case "champu":
                nombreCorto = resources.getString(R.string.enemigo_champu_corto);
                break;
            case "cuchilla":
                nombreCorto = resources.getString(R.string.enemigo_cuchilla_corto);
                break;
            case "stripper":
                nombreCorto = resources.getString(R.string.enemigo_stripper_corto);
                break;
            case "vater":
                nombreCorto = resources.getString(R.string.enemigo_vater_corto);
                break;

            case "calabaza":
                nombreCorto = resources.getString(R.string.enemigo_calabaza_corto);
                break;
            case "cuchara":
                nombreCorto = resources.getString(R.string.enemigo_cuchara_corto);
                break;
            case "leche":
                nombreCorto = resources.getString(R.string.enemigo_leche_corto);
                break;
            case "limon":
                nombreCorto = resources.getString(R.string.enemigo_limon_corto);
                break;
            case "zanahoria":
                nombreCorto = resources.getString(R.string.enemigo_zanahoria_corto);
                break;
        }
        return nombreCorto;
    }

    public String getNombreZona(Resources resources, String nombre) {
        String nombreCorto = null;
        if(nombre != null){
            switch (nombre){
                case "bano":
                    nombreCorto = resources.getString(R.string.zona_bano);
                    break;
                case "cocina":
                    nombreCorto = resources.getString(R.string.zona_cocina);
                    break;
                case "oficina":
                    nombreCorto = resources.getString(R.string.zona_oficina);
                    break;
                case "parque":
                    nombreCorto = resources.getString(R.string.zona_parque);
                    break;
                case "cementerio":
                    nombreCorto = resources.getString(R.string.zona_cementerio);
                    break;
                case "infierno":
                    nombreCorto = resources.getString(R.string.zona_infierno);
                    break;

            }
        }
        return nombreCorto;
    }

    public String getNombreMaterial(Resources resources, String nombre) {
        String nombreBonito = null;
        if(nombre != null){
            switch (nombre){
                case "buen_rollo":
                    nombreBonito = resources.getString(R.string.material_buen_rollo);
                    break;
                case "plastico":
                    nombreBonito = resources.getString(R.string.material_plastico);
                    break;
                case "madera":
                    nombreBonito = resources.getString(R.string.material_madera);
                    break;
                case "trozo_calabaza":
                    nombreBonito = resources.getString(R.string.material_trozo_calabaza);
                    break;

            }
        }
        return nombreBonito;
    }

    public Drawable getDrawableIconoEquipable(Resources resources, char tipo) {
        Drawable drawable = null;
        switch (tipo){
            case 'A':
                drawable = resources.getDrawable(R.drawable.icono_arma);
                break;
            case 'S':
                drawable = resources.getDrawable(R.drawable.icono_sombrero);
                break;
        }
        return drawable;
    }

    public String getNombreEquipable(Resources resources, String nombre) {
        String nombreBonito = "";
        switch (nombre){
            case "tenedor":
                nombreBonito = resources.getString(R.string.equipable_tenedor);
                break;
            case "casco_obra":
                nombreBonito = resources.getString(R.string.equipable_casco_obra);
                break;
            case "mazo_juez":
                nombreBonito = resources.getString(R.string.equipable_mazo_juez);
                break;
            case "casco_calabaza":
                nombreBonito = resources.getString(R.string.equipable_casco_calabaza);
                break;

        }
        return nombreBonito;
    }
}
