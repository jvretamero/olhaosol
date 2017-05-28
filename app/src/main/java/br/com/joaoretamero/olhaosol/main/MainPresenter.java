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

    public void inicia() {
        view.exibeLista();
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

    public void trocaUnidadeTemperatura() {
        view.atualizaMenu();

        if (unidadeTemperatura == UnidadeTemperatura.CELSIUS) {
            unidadeTemperatura = UnidadeTemperatura.FAHRENHEIT;
        } else {
            unidadeTemperatura = UnidadeTemperatura.CELSIUS;
        }
    }
}
