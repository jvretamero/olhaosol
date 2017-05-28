package br.com.joaoretamero.olhaosol.http;


import br.com.joaoretamero.olhaosol.http.modelos.Clima;
import br.com.joaoretamero.olhaosol.http.modelos.Coordenadas;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import rx.Observable;

public class ServicoHttp {

    private OpenWeatherApi api;

    public ServicoHttp(OpenWeatherApi api) {
        this.api = api;
    }

    public Observable<PrevisaoClimatica> getPrevisoesClimaticas(float latitude, float longitude) {
        return api.listarPrevisoes(latitude, longitude)
                .concatMap(dadosClimaticos -> Observable.from(dadosClimaticos.cidades))
                .map(cidade -> {
                    Clima clima = cidade.climas.get(0);
                    Coordenadas coordenadas = cidade.coordenadas;

                    PrevisaoClimatica previsaoClimatica = new PrevisaoClimatica();
                    previsaoClimatica.nomeCidade = cidade.nome;
                    previsaoClimatica.temperaturaAtual = cidade.temperatura.atual;
                    previsaoClimatica.temperaturaMinima = cidade.temperatura.minima;
                    previsaoClimatica.temperaturaMaxima = cidade.temperatura.maxima;
                    previsaoClimatica.icone = clima.icone;
                    previsaoClimatica.descricaoClima = clima.descricao;

                    return previsaoClimatica;
                });
    }
}
