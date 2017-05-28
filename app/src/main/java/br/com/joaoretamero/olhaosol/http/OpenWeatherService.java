package br.com.joaoretamero.olhaosol.http;


import java.util.List;

import br.com.joaoretamero.olhaosol.http.modelos.Cidade;
import rx.Observable;

public class OpenWeatherService {

    private OpenWeatherApi api;

    public OpenWeatherService(OpenWeatherApi api) {
        this.api = api;
    }

    public Observable<List<Cidade>> getPrevisoesClimaticas(float latitude, float longitude) {
        return api.listarPrevisoes(latitude, longitude)
                .concatMap(dadosClimaticos -> Observable.just(dadosClimaticos.cidades));
    }
}
