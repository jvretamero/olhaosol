package br.com.joaoretamero.olhaosol.main;


import br.com.joaoretamero.olhaosol.http.ServicoClimatico;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaCelcius;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaFahrenheit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter {

    private MainView view;
    private ModoExibicao modoExibicao;
    private UnidadeTemperatura unidadeTemperatura;
    private ServicoClimatico servicoHttp;
    private CompositeSubscription subscriptions;
    private float latitude;
    private float longitude;

    public MainPresenter(MainView view, ServicoClimatico servicoHttp) {
        this.view = view;
        this.servicoHttp = servicoHttp;
        this.modoExibicao = ModoExibicao.LISTA;
        this.unidadeTemperatura = UnidadeTemperatura.CELSIUS;
        this.subscriptions = new CompositeSubscription();
    }

    public void exibicaoPausada() {
        subscriptions.clear();
    }

    public void carregaPrevisoes(float latitude, float longitude) {
        view.exibeCarregamento(true);

        Subscription subscription = servicoHttp
                .getPrevisoesClimaticas(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(previsoes -> view.exibePrevisoes(previsoes, getConversorTemperatura()),
                        erro -> view.exibeCarregamento(false),
                        () -> view.exibeCarregamento(false));

        subscriptions.add(subscription);
    }

    public ModoExibicao getModoExibicao() {
        return modoExibicao;
    }

    public UnidadeTemperatura getUnidadeTemperatura() {
        return unidadeTemperatura;
    }

    public void trocaModoExibicao() {
        view.atualizaMenu();

        if (modoExibicao == ModoExibicao.LISTA) {
            modoExibicao = ModoExibicao.MAPA;
            view.exibeMapa();
        } else {
            modoExibicao = ModoExibicao.LISTA;
            view.exibeLista();
        }
    }

    private ConversorTemperatura getConversorTemperatura() {
        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS)
            return new KelvinParaCelcius();
        else
            return new KelvinParaFahrenheit();
    }

    private void setConversorTemperatura() {
        ConversorTemperatura conversorTemperatura = getConversorTemperatura();
        view.setConversorTemperatura(conversorTemperatura);
    }

    public void trocaUnidadeTemperatura() {
        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS)
            unidadeTemperatura = UnidadeTemperatura.FAHRENHEIT;
        else
            unidadeTemperatura = UnidadeTemperatura.CELSIUS;

        setConversorTemperatura();
        view.atualizaMenu();
    }

    public void permissaoLocalizacaoConcedida() {
        view.exibeObtendoLocalizacao(true);
        view.requisitarLocalizacao();
    }

    public void exibicaoIniciada() {
        carregaPrevisoes(latitude, longitude);
    }

    public void localizacaoObtida(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        view.exibeObtendoLocalizacao(false);
        view.exibeLista();
    }

    public void mapaMovimentado(float latitude, float longitude) {
        carregaPrevisoes(latitude, longitude);
    }

    public void semPermissaoLocalizacao() {
        view.requisitarPermissao();
    }

    public void deveExplicarPermissaoLocalizacao() {
        view.explicarMotivoPermissao();
    }

    public void permissaoLocalizacaoNegada() {
        view.exibirMensagemSemPermissao();
    }

    public void semLocalizacao() {
        view.exibeObtendoLocalizacao(false);
        view.exibieMensagemSemLocalizacao();
    }
}
