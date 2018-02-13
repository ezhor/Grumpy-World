package com.arensis_games.grumpyworld.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dparrado on 31/01/18.
 */

public class Rollo implements Parcelable {
    private String nombre;
    private String sombrero;
    private String arma;
    private String zona;

    public Rollo(){

    }

    public Rollo(String nombre, String sombrero, String arma, String zona) {
        this.nombre = nombre;
        this.sombrero = sombrero;
        this.arma = arma;
        this.zona = zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSombrero() {
        return sombrero;
    }

    public void setSombrero(String sombrero) {
        this.sombrero = sombrero;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.sombrero);
        dest.writeString(this.arma);
        dest.writeString(this.zona);
    }

    protected Rollo(Parcel in) {
        this.nombre = in.readString();
        this.sombrero = in.readString();
        this.arma = in.readString();
        this.zona = in.readString();
    }

    public static final Parcelable.Creator<Rollo> CREATOR = new Parcelable.Creator<Rollo>() {
        @Override
        public Rollo createFromParcel(Parcel source) {
            return new Rollo(source);
        }

        @Override
        public Rollo[] newArray(int size) {
            return new Rollo[size];
        }
    };
}
