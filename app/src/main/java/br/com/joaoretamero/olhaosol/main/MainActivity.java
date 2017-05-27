package br.com.joaoretamero.olhaosol.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import javax.inject.Inject;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.DaggerComponenteHttp;
import br.com.joaoretamero.olhaosol.http.ModuloHttp;
import br.com.joaoretamero.olhaosol.http.OpenWeatherApi;
import br.com.joaoretamero.olhaosol.http.modelos.Resposta;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    OpenWeatherApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configuraDependencias();
        configuraToolbar();

        api.listarPrevisoes(55.4951f, 37.5033f, 10).enqueue(new Callback<Resposta>() {
            @Override
            public void onResponse(Call<Resposta> call, Response<Resposta> response) {
                Log.d(TAG, "onResponse");

                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                } else {
                    Log.d(TAG, "sem sucesso");
                }
            }

            @Override
            public void onFailure(Call<Resposta> call, Throwable t) {
                Log.d(TAG, "onFailure");
                Log.d(TAG, call.request().url().toString());
                t.printStackTrace();
            }
        });
    }

    private void configuraDependencias() {
        DaggerComponenteHttp.builder()
                .moduloHttp(new ModuloHttp())
                .build()
                .inject(this);
    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }
}
