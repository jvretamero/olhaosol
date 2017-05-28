package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cidade {

    @SerializedName("name")
    public String nome;

    @SerializedName("coord")
    public Coordenada coordenadas;

    @SerializedName("main")
    public Temperatura temperatura;

    @SerializedName("weather")
    public List<Clima> climas;

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
