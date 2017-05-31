package br.com.joaoretamero.olhaosol.http;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProvedorHttp {

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
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static synchronized OpenWeatherApi getOpenWeatherApi() {
        return getRetrofit().create(OpenWeatherApi.class);
    }

    public static ServicoClimaticoImpl getServicoHttp() {
        return new ServicoClimaticoImpl(getOpenWeatherApi());
    }

    public static String montarUrlIcone(String icone) {
        return String.format("http://openweathermap.org/img/w/%s.png", icone);
    }
}
