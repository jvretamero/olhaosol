package br.com.joaoretamero.olhaosol.http;


import br.com.joaoretamero.olhaosol.http.modelos.Resposta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("find")
    Call<Resposta> listarPrevisoes(@Query("lat") float latitude, @Query("lon") float longitude,
                                   @Query("cnt") int quatidade);
}
