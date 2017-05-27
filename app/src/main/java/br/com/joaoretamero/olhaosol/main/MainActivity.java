package br.com.joaoretamero.olhaosol.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.DaggerComponenteHttp;
import br.com.joaoretamero.olhaosol.http.ModuloHttp;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configuraDependencias();
        configuraToolbar();
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
