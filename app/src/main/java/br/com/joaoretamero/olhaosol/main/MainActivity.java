package br.com.joaoretamero.olhaosol.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.DaggerComponenteHttp;
import br.com.joaoretamero.olhaosol.http.ModuloHttp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configuraDependencias();
    }

    private void configuraDependencias() {
        DaggerComponenteHttp.builder()
                .moduloHttp(new ModuloHttp())
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
