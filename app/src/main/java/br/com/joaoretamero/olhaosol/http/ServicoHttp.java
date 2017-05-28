package br.com.joaoretamero.olhaosol.http;


import java.util.List;

import br.com.joaoretamero.olhaosol.http.modelos.Cidade;
import br.com.joaoretamero.olhaosol.http.modelos.Clima;
import br.com.joaoretamero.olhaosol.http.modelos.Coordenada;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.CoordenadaUtil;
import rx.Observable;

public class ServicoHttp {

    private OpenWeatherApi api;

    public ServicoHttp(OpenWeatherApi api) {
        this.api = api;
    }

    public static String montarUrlIcone(String icone) {
        return String.format("http://openweathermap.org/img/w/%s.png", icone);
    }

    public Observable<List<PrevisaoClimatica>> getPrevisoesClimaticas(float latitude, float longitude) {
        Coordenada coordenadaUsuario = new Coordenada();
        coordenadaUsuario.latitude = latitude;
        coordenadaUsuario.longitude = longitude;

        return api.listarPrevisoes(latitude, longitude)
                .flatMap(dadosClimaticos -> Observable.from(dadosClimaticos.cidades)
                        .map(cidade -> cidadeEmPrevisao(cidade, coordenadaUsuario))
                        .filter(this::distanciaAte50Km)
                        .toList());
    }

    private PrevisaoClimatica cidadeEmPrevisao(Cidade cidade, Coordenada coordenadaUsuario) {
        Clima clima = cidade.climas.get(0);
        Coordenada coordenadaCidade = cidade.coordenadas;

        PrevisaoClimatica previsaoClimatica = new PrevisaoClimatica();
        previsaoClimatica.nomeCidade = cidade.nome;
        previsaoClimatica.temperaturaAtual = cidade.temperatura.atual;
        previsaoClimatica.temperaturaMinima = cidade.temperatura.minima;
        previsaoClimatica.temperaturaMaxima = cidade.temperatura.maxima;
        previsaoClimatica.icone = clima.icone;
        previsaoClimatica.descricaoClima = clima.descricao;
        previsaoClimatica.distanciaEmKm = CoordenadaUtil.distanciaKm(coordenadaUsuario, coordenadaCidade);

        return previsaoClimatica;
    }

    private boolean distanciaAte50Km(PrevisaoClimatica previsaoClimatica) {
        return Float.compare(previsaoClimatica.distanciaEmKm, 50.0f) <= 0;
    }
}
