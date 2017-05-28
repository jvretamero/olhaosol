package br.com.joaoretamero.olhaosol.http;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServiceProvider {

    private static Retrofit retrofit;

    private static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new OpenWeatherIterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static synchronized OpenWeatherApi provideOpenWeatherApi() {
        return getRetrofit().create(OpenWeatherApi.class);
    }
}
