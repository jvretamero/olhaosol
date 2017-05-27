package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Clima {

    @SerializedName("id")
    int identificador;

    @SerializedName("icon")
    String icone;

    @Override
    public String toString() {
        return "Clima{" +
                "identificador=" + identificador +
                ", icone='" + icone + '\'' +
                '}';
    }
}
