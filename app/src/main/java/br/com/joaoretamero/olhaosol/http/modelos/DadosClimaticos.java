package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DadosClimaticos {

    @SerializedName("list")
    public List<Cidade> cidades;

    @Override
    public String toString() {
        return "DadosClimaticos{" +
                "cidades=" + cidades +
                '}';
    }
}
