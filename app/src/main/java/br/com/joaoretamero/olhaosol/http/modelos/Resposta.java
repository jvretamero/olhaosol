package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resposta {

    @SerializedName("list")
    List<Cidade> cidades;

    @Override
    public String toString() {
        return "Resposta{" +
                "cidades=" + cidades +
                '}';
    }
}
