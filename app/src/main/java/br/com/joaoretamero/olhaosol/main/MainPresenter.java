package br.com.joaoretamero.olhaosol.main;


import br.com.joaoretamero.olhaosol.http.ServicoHttp;
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
    private ServicoHttp servicoHttp;
    private CompositeSubscription subscriptions;

    public MainPresenter(MainView view, ServicoHttp servicoHttp) {
        this.view = view;
        this.servicoHttp = servicoHttp;
        this.modoExibicao = ModoExibicao.LISTA;
        this.unidadeTemperatura = UnidadeTemperatura.CELSIUS;
        this.subscriptions = new CompositeSubscription();
    }

    public void inicia() {
        view.exibeLista();
    }

    public void exibicaoPronta() {
        carregaPrevisoes();
    }

    public void exibicaoPausada() {
        subscriptions.clear();
    }

    private void carregaPrevisoes() {
        view.exibeCarregamento(true);

        Subscription subscription = servicoHttp
                .getPrevisoesClimaticas(50.34f, 34.0f)
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

        carregaPrevisoes();
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
}
