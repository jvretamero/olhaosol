package br.com.joaoretamero.olhaosol.http;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("find&cnt=10")
    Call<String> listarPrevisoes(@Query("lat") float latitude, @Query("lon") float longitude);
}
