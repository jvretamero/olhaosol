package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Temperatura {

    @SerializedName("temp")
    public float atual;

    @SerializedName("temp_min")
    public float minima;

    @SerializedName("temp_max")
    public float maxima;

    @Override
    public String toString() {
        return "Temperatura{" +
                "atual=" + atual +
                ", minima=" + minima +
                ", maxima=" + maxima +
                '}';
    }
}
