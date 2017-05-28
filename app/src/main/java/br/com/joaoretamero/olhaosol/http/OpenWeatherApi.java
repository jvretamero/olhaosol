package br.com.joaoretamero.olhaosol.http;


import br.com.joaoretamero.olhaosol.http.modelos.DadosClimaticos;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface OpenWeatherApi {

    @GET("find")
    Observable<DadosClimaticos> listarPrevisoes(@Query("lat") float latitude, @Query("lon") float longitude);
}
