package br.com.joaoretamero.olhaosol.main;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import br.com.joaoretamero.olhaosol.R;
import br.com.joaoretamero.olhaosol.http.ProvedorHttp;
import br.com.joaoretamero.olhaosol.lista.ListaFragment;
import br.com.joaoretamero.olhaosol.localizacao.ServicoLocalizacao;
import br.com.joaoretamero.olhaosol.mapa.MapaFragment;
import br.com.joaoretamero.olhaosol.mapa.NovaPosicaoListener;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.schedulers.ProvedorSchedulerPadrao;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import java.util.List;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements MainView,
    ExibicaoListener, NovaPosicaoListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int REQUEST_ERRO_PLAY_SERVICES = 1;
    private static final int REQUEST_PERMISSAO_LOCALIZACAO = 2;
    private static final String permissaoLocalizacao = Manifest.permission.ACCESS_FINE_LOCATION;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.celcius)
    String stringCelcius;

    @BindString(R.string.fahrenheit)
    String stringFahrenheit;

    @BindString(R.string.carregando_previsoes)
    String stringCarregandoPrevisoes;

    @BindString(R.string.obtendo_localizacao)
    String stringObtendoLocalizacao;

    private MainPresenter presenter;
    private ProgressDialog progressDialogPrevisoes;
    private ProgressDialog progressDialogLocalizacao;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationClient;
    private ServicoLocalizacao servicoLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        configuraToolbar();
        configurarGoogleApiClient();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        servicoLocalizacao = new ServicoLocalizacao(fusedLocationClient);

        presenter = new MainPresenter(this, new ProvedorSchedulerPadrao(),
            ProvedorHttp.getServicoHttp());
    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
    }

    private void configurarGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
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

        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void atualizaMenuTemperatura(Menu menu) {
        UnidadeTemperatura unidadeTemperatura = presenter.getUnidadeTemperatura();
        MenuItem menuTemperatura = menu.findItem(R.id.menu_temperatura);

        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS) {
            menuTemperatura.setTitle(stringFahrenheit);
        } else {
            menuTemperatura.setTitle(stringCelcius);
        }
    }

    private void atualizaMenuExibicao(Menu menu) {
        ModoExibicao modoExibicao = presenter.getModoExibicao();
        MenuItem menuExibicao = menu.findItem(R.id.menu_exibicao);

        if (modoExibicao == ModoExibicao.LISTA) {
            menuExibicao.setIcon(R.drawable.ic_mapa);
        } else {
            menuExibicao.setIcon(R.drawable.ic_lista);
        }
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
            if (progressDialogPrevisoes == null) {
                progressDialogPrevisoes = new ProgressDialog(this);
                progressDialogPrevisoes.setIndeterminate(true);
                progressDialogPrevisoes.setCancelable(false);
                progressDialogPrevisoes.setMessage(stringCarregandoPrevisoes);
            }
            progressDialogPrevisoes.show();
        } else {
            if (progressDialogPrevisoes != null) {
                progressDialogPrevisoes.dismiss();
            }
        }
    }

    private PrevisoesView getFragment() {
        return (PrevisoesView) getSupportFragmentManager().findFragmentById(R.id.conteudo);
    }

    @Override
    public void exibePrevisoes(List<PrevisaoClimatica> previsoes,
        ConversorTemperatura conversorTemperatura) {
        PrevisoesView fragment = getFragment();

        if (fragment != null) {
            fragment.exibePrevisoes(previsoes, conversorTemperatura);
        }
    }

    @Override
    public void setConversorTemperatura(ConversorTemperatura conversorTemperatura) {
        PrevisoesView fragment = getFragment();

        if (fragment != null) {
            fragment.setConversorTemperatura(conversorTemperatura);
        }
    }

    @Override
    public void onExibicaoIniciada() {
        presenter.exibicaoIniciada();
    }

    @Override
    public void onExibicaoPausada() {
        presenter.exibicaoPausada();
    }

    @Override
    public void onNovaPosicao(float latitude, float longitude) {
        presenter.mapaMovimentado(latitude, longitude);
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSAO_LOCALIZACAO) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.permissaoLocalizacaoConcedida();
            } else {
                presenter.permissaoLocalizacaoNegada();
            }
        }
    }

    @Override
    public void exibirMensagemSemPermissao() {
        exibeMensagem(R.string.sem_permissao);
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onConnected(@Nullable Bundle bundle) {
        if (isPermissaoLocalizacaoConcedida(permissaoLocalizacao)) {
            presenter.permissaoLocalizacaoConcedida();
        } else if (ActivityCompat
            .shouldShowRequestPermissionRationale(this, permissaoLocalizacao)) {
            presenter.deveExplicarPermissaoLocalizacao();
        } else {
            presenter.semPermissaoLocalizacao();
        }
    }

    private boolean isPermissaoLocalizacaoConcedida(String permissao) {
        return ActivityCompat.checkSelfPermission(this, permissao)
            == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void explicarMotivoPermissao() {
        new AlertDialog.Builder(this)
            .setMessage(R.string.explicacao_localizacao)
            .setPositiveButton(R.string.permitir, (dialog, which) -> {
                dialog.dismiss();
                presenter.semPermissaoLocalizacao();
            })
            .setNegativeButton(R.string.fechar_app, (dialog, which) -> {
                dialog.cancel();
                finish();
            })
            .show();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void requisitarLocalizacao() {
        servicoLocalizacao.getDisponibilidadeLocalizacao()
            .switchMap(disponivel -> {
                if (disponivel) {
                    return servicoLocalizacao.getLocalizacao();
                } else {
                    return Observable.just(null);
                }
            })
            .onErrorReturn(erro -> null)
            .subscribe(localizacao -> {
                if (localizacao == null) {
                    presenter.semLocalizacao();
                } else {
                    float latitude = (float) localizacao.getLatitude();
                    float longitude = (float) localizacao.getLongitude();

                    presenter.localizacaoObtida(latitude, longitude);
                }
            });
    }

    @Override
    public void exibeObtendoLocalizacao(boolean visivel) {
        if (visivel) {
            if (progressDialogLocalizacao == null) {
                progressDialogLocalizacao = new ProgressDialog(this);
                progressDialogLocalizacao.setIndeterminate(true);
                progressDialogLocalizacao.setCancelable(false);
                progressDialogLocalizacao.setMessage(stringObtendoLocalizacao);
            }
            progressDialogLocalizacao.show();
        } else {
            if (progressDialogLocalizacao != null) {
                progressDialogLocalizacao.dismiss();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        if (location == null) {
            presenter.semLocalizacao();
        } else {
            presenter
                .localizacaoObtida((float) location.getLatitude(), (float) location.getLongitude());
        }
    }

    @Override
    public void exibieMensagemSemLocalizacao() {
        exibeMensagem(R.string.sem_localizacao);
    }

    private void exibeMensagem(@StringRes int idMensagem) {
        new AlertDialog.Builder(this)
            .setMessage(idMensagem)
            .setNegativeButton(R.string.fechar_app, (dialog, which) -> {
                dialog.cancel();
                finish();
            })
            .show();
    }

    @Override
    public void requisitarPermissao() {
        String[] permissoes = new String[]{permissaoLocalizacao};
        ActivityCompat.requestPermissions(this, permissoes, REQUEST_PERMISSAO_LOCALIZACAO);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, REQUEST_ERRO_PLAY_SERVICES);
            } catch (IntentSender.SendIntentException e) {
                googleApiClient.connect();
            }
        } else {
            exibeMensagem(R.string.problema_play_services);
        }
    }


}
