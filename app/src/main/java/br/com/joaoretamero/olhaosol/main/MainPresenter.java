package br.com.joaoretamero.olhaosol.main;


public class MainPresenter {

    private MainView view;
    private ModoExibicao modoExibicao;
    private UnidadeTemperatura unidadeTemperatura;

    public MainPresenter(MainView view) {
        this.view = view;
        this.modoExibicao = ModoExibicao.LISTA;
        this.unidadeTemperatura = UnidadeTemperatura.CELSIUS;
    }

    public ModoExibicao getModoExibicao() {
        return modoExibicao;
    }

    public UnidadeTemperatura getUnidadeTemperatura() {
        return unidadeTemperatura;
    }

    public void trocarModoExibicao() {
        view.atualizarMenu();

        if (modoExibicao == ModoExibicao.LISTA) {
            modoExibicao = ModoExibicao.MAPA;
            view.exibirMapa();
        } else {
            modoExibicao = ModoExibicao.LISTA;
            view.exibirLista();
        }
    }

    public void trocarUnidadeTemperatura() {
        view.atualizarMenu();

        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS) {
            unidadeTemperatura = UnidadeTemperatura.FAHRENHEIT;
        } else {
            unidadeTemperatura = UnidadeTemperatura.CELSIUS;
        }
    }
}
