package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Coordenada {

    @SerializedName("lat")
    public float latitude;

    @SerializedName("lon")
    public float longitude;

    @Override
    public String toString() {
        return "Coordenada{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
