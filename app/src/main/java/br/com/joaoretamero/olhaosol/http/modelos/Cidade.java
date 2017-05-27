package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cidade {

    @SerializedName("name")
    String nome;

    @SerializedName("coord")
    Coordenadas coordenadas;

    @SerializedName("main")
    Temperatura temperatura;

    @SerializedName("weather")
    List<Clima> climas;

    @Override
    public String toString() {
        return "Cidade{" +
                "nome='" + nome + '\'' +
                ", coordenadas=" + coordenadas +
                ", temperatura=" + temperatura +
                ", climas=" + climas +
                '}';
    }
}
