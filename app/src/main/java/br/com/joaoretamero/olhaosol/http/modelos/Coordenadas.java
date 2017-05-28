package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Coordenadas {

    @SerializedName("lat")
    public float latitude;

    @SerializedName("lon")
    public float longitude;

    @Override
    public String toString() {
        return "Coordenadas{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
