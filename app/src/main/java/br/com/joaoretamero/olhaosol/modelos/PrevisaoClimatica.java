package br.com.joaoretamero.olhaosol.modelos;


public class PrevisaoClimatica {

    public String nomeCidade;
    public float temperaturaAtual;
    public float temperaturaMinima;
    public float temperaturaMaxima;
    public float distanciaEmKm;
    public String icone;
    public String descricaoClima;

    @Override
    public String toString() {
        return "PrevisaoClimatica{" +
                "nomeCidade='" + nomeCidade + '\'' +
                ", temperaturaAtual=" + temperaturaAtual +
                ", temperaturaMinima=" + temperaturaMinima +
                ", temperaturaMaxima=" + temperaturaMaxima +
                ", distanciaEmKm=" + distanciaEmKm +
                ", icone='" + icone + '\'' +
                ", descricaoClima='" + descricaoClima + '\'' +
                '}';
    }
}
