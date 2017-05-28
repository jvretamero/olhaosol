package br.com.joaoretamero.olhaosol.http;


import java.io.IOException;

import br.com.joaoretamero.olhaosol.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OpenWeatherIterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request requestOriginal = chain.request();
        HttpUrl urlOriginal = requestOriginal.url();

        HttpUrl url = urlOriginal.newBuilder()
                .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
                .addQueryParameter("lang", "pt")
                .addQueryParameter("cnt", "10")
                .build();

        Request request = requestOriginal
                .newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
