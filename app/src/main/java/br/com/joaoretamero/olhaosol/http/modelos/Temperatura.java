package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Temperatura {

    @SerializedName("temp")
    float atual;

    @SerializedName("temp_min")
    float minima;

    @SerializedName("temp_max")
    float maxima;

    @Override
    public String toString() {
        return "Temperatura{" +
                "atual=" + atual +
                ", minima=" + minima +
                ", maxima=" + maxima +
                '}';
    }
}
