package br.com.joaoretamero.olhaosol.http.modelos;


import com.google.gson.annotations.SerializedName;

public class Clima {

    @SerializedName("id")
    public int identificador;

    @SerializedName("icon")
    public String icone;

    @SerializedName("description")
    public String descricao;

    @Override
    public String toString() {
        return "Clima{" +
                "identificador=" + identificador +
                ", icone='" + icone + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
