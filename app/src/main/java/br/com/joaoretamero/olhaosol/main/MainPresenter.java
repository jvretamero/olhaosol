package br.com.joaoretamero.olhaosol.main;


import br.com.joaoretamero.olhaosol.http.ServicoHttp;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaCelcius;
import br.com.joaoretamero.olhaosol.util.temperatura.KelvinParaFahrenheit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

    private MainView view;
    private ModoExibicao modoExibicao;
    private UnidadeTemperatura unidadeTemperatura;
    private ServicoHttp servicoHttp;

    public MainPresenter(MainView view, ServicoHttp servicoHttp) {
        this.view = view;
        this.servicoHttp = servicoHttp;
        this.modoExibicao = ModoExibicao.LISTA;
        this.unidadeTemperatura = UnidadeTemperatura.CELSIUS;
    }

    public void inicia() {
        view.exibeLista();
        carregaPrevisoes();
    }

    private void carregaPrevisoes() {
        view.exibeCarregamento(true);

        servicoHttp.getPrevisoesClimaticas(50.34f, 34.0f)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(previsoes -> view.exibirPrevisoes(previsoes),
                        erro -> view.exibeCarregamento(false),
                        () -> view.exibeCarregamento(false));
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

    public void trocaUnidadeTemperatura() {
        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS) {
            unidadeTemperatura = UnidadeTemperatura.FAHRENHEIT;
            view.setConversorTemperatura(new KelvinParaFahrenheit());
        } else {
            unidadeTemperatura = UnidadeTemperatura.CELSIUS;
            view.setConversorTemperatura(new KelvinParaCelcius());
        }

        view.atualizaMenu();
    }
}
