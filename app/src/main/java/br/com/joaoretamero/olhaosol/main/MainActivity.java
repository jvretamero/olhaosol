package br.com.joaoretamero.olhaosol.main;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.ProvedorHttp;
import br.com.joaoretamero.olhaosol.lista.ListaFragment;
import br.com.joaoretamero.olhaosol.mapa.MapaFragment;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindDrawable(R.drawable.ic_lista)
    Drawable iconeLista;

    @BindDrawable(R.drawable.ic_mapa)
    Drawable iconeMapa;

    @BindString(R.string.celcius)
    String stringCelcius;

    @BindString(R.string.fahrenheit)
    String stringFahrenheit;

    private MainPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configuraToolbar();

        presenter = new MainPresenter(this, ProvedorHttp.getServicoHttp());
    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizaMenuExibicao(menu);
        atualizaMenuTemperatura(menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.inicia();
    }

    private void atualizaMenuTemperatura(Menu menu) {
        UnidadeTemperatura unidadeTemperatura = presenter.getUnidadeTemperatura();
        MenuItem menuTemperatura = menu.findItem(R.id.menu_temperatura);

        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS)
            menuTemperatura.setTitle(stringFahrenheit);
        else
            menuTemperatura.setTitle(stringCelcius);
    }

    private void atualizaMenuExibicao(Menu menu) {
        ModoExibicao modoExibicao = presenter.getModoExibicao();
        MenuItem menuExibicao = menu.findItem(R.id.menu_exibicao);

        if (modoExibicao == ModoExibicao.LISTA)
            menuExibicao.setIcon(iconeMapa);
        else
            menuExibicao.setIcon(iconeLista);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_exibicao) {
            presenter.trocaModoExibicao();
            return true;
        } else if (id == R.id.menu_temperatura) {
            presenter.trocaUnidadeTemperatura();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void exibeLista() {
        exibirFragment(ListaFragment.newInstance());
    }

    @Override
    public void exibeMapa() {
        exibirFragment(MapaFragment.newInstance());
    }

    private void exibirFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.conteudo, fragment)
                .commit();
    }

    @Override
    public void atualizaMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public void exibeCarregamento(boolean visivel) {
        if (visivel) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Carregando previsões");
            }
            progressDialog.show();
        } else {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    @Override
    public void exibirPrevisoes(List<PrevisaoClimatica> previsoes) {
        PrevisoesView fragment = (PrevisoesView) getSupportFragmentManager().findFragmentById(R.id.conteudo);

        if (fragment != null) {
            fragment.exibirPrevisoes(previsoes);
        }
    }
}
