package br.com.joaoretamero.olhaosol.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.DaggerComponenteHttp;
import br.com.joaoretamero.olhaosol.http.ModuloHttp;
import br.com.joaoretamero.olhaosol.lista.ListaFragment;
import br.com.joaoretamero.olhaosol.mapa.MapaFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configuraToolbar();

        presenter = new MainPresenter(this);
    }

    private void configuraDependencias() {
        DaggerComponenteHttp.builder()
                .moduloHttp(new ModuloHttp())
                .build()
                .inject(this);
    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
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

    private void atualizaMenuTemperatura(Menu menu) {
        UnidadeTemperatura unidadeTemperatura = presenter.getUnidadeTemperatura();
        MenuItem menuTemperatura = menu.findItem(R.id.menu_temperatura);

        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS)
            menuTemperatura.setTitle(stringCelcius);
        else
            menuTemperatura.setTitle(stringFahrenheit);
    }

    private void atualizaMenuExibicao(Menu menu) {
        ModoExibicao modoExibicao = presenter.getModoExibicao();
        MenuItem menuExibicao = menu.findItem(R.id.menu_exibicao);

        if (modoExibicao == ModoExibicao.LISTA)
            menuExibicao.setIcon(iconeLista);
        else
            menuExibicao.setIcon(iconeMapa);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_exibicao) {
            presenter.trocarModoExibicao();
            return true;
        } else if (id == R.id.menu_temperatura) {
            presenter.trocarUnidadeTemperatura();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void exibirLista() {
        exibirFragment(ListaFragment.newInstance());
    }

    @Override
    public void exibirMapa() {
        exibirFragment(MapaFragment.newInstance());
    }

    private void exibirFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.conteudo, fragment)
                .commit();
    }

    @Override
    public void atualizarMenu() {
        invalidateOptionsMenu();
    }
}
