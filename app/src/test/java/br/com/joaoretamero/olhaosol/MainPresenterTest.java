package br.com.joaoretamero.olhaosol;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.joaoretamero.olhaosol.http.ServicoClimatico;
import br.com.joaoretamero.olhaosol.main.MainPresenter;
import br.com.joaoretamero.olhaosol.main.MainView;
import br.com.joaoretamero.olhaosol.main.ModoExibicao;
import br.com.joaoretamero.olhaosol.main.UnidadeTemperatura;
import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.schedulers.ProvedorScheduler;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private static List<PrevisaoClimatica> previsoes;
    private static float latitude;
    private static float longitude;

    @Mock
    private ServicoClimatico servicoClimatico;

    @Mock
    private MainView mainView;

    @Mock
    private ProvedorScheduler provedorScheduler;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        latitude = 100;
        longitude = 100;

        previsoes = new ArrayList<>();
        previsoes.add(novaPrevisao("Cidade 1", 100));
        previsoes.add(novaPrevisao("Cidade 2", 40));

        when(provedorScheduler.io()).thenReturn(Schedulers.immediate());
        when(provedorScheduler.ui()).thenReturn(Schedulers.immediate());

        when(servicoClimatico.getPrevisoesClimaticas(anyFloat(), anyFloat()))
                .thenReturn(Observable.just(previsoes));

        mainPresenter = new MainPresenter(mainView, provedorScheduler, servicoClimatico);
        mainPresenter.setLocalizacao(latitude, longitude);
    }

    private PrevisaoClimatica novaPrevisao(String cidade, float temperatura) {
        PrevisaoClimatica previsaoClimatica = new PrevisaoClimatica();
        previsaoClimatica.nomeCidade = cidade;
        previsaoClimatica.temperaturaAtual = temperatura;
        previsaoClimatica.temperaturaMaxima = temperatura + 30;
        previsaoClimatica.temperaturaMinima = temperatura - 20;
        previsaoClimatica.latitude = 100;
        previsaoClimatica.longitude = 100;

        return previsaoClimatica;
    }

    private void verifyCarregarPrevisoes() {
        verify(mainView).exibeCarregamento(true);
        verify(servicoClimatico).getPrevisoesClimaticas(latitude, longitude);
        verify(mainView).exibeCarregamento(false);
    }

    @Test
    public void semPermissaoLocalizacao_deveRequisitarPermissaoLocalizacao() {
        mainPresenter.semPermissaoLocalizacao();

        verify(mainView).requisitarPermissao();
    }

    @Test
    public void deveExplicarPermissaoLocalizacao_explicarPermissaoLocalizacao() {
        mainPresenter.deveExplicarPermissaoLocalizacao();

        verify(mainView).explicarMotivoPermissao();
    }

    @Test
    public void permissaoLocalizacaoNegada_deveExibirMensagemSemPermissao() {
        mainPresenter.permissaoLocalizacaoNegada();

        verify(mainView).exibirMensagemSemPermissao();
    }

    @Test
    public void permissaoLocalizacaoConcedia_deveRequisitarLocalizacao() {
        mainPresenter.permissaoLocalizacaoConcedida();

        verify(mainView).exibeObtendoLocalizacao(true);
        verify(mainView).requisitarLocalizacao();
    }

    @Test
    public void localizacaoObtida_deveExibirLista() {
        mainPresenter.localizacaoObtida(latitude, longitude);

        verify(mainView).exibeObtendoLocalizacao(false);
        verify(mainView).exibeLista();
    }

    @Test
    public void semLocalizacao_deveExibirMensagemSemLocalizacao() {
        mainPresenter.semLocalizacao();

        verify(mainView).exibeObtendoLocalizacao(false);
        verify(mainView).exibieMensagemSemLocalizacao();
    }

    @Test
    public void mapaMovimentado_deveCarregarPrevisoes() {
        mainPresenter.mapaMovimentado(latitude, longitude);

        verifyCarregarPrevisoes();
    }

    @Test
    public void exibicaoIniciada_deveCarregarPrevisoes() {
        mainPresenter.exibicaoIniciada();

        verifyCarregarPrevisoes();
    }

    @Test
    public void modoExibicaoInicial_deveSerLista() {
        ModoExibicao modoExibicao = mainPresenter.getModoExibicao();

        assertThat(modoExibicao, is(ModoExibicao.LISTA));
    }

    @Test
    public void trocarExibicao_deveTrocarDeListaParaMapa() {
        ModoExibicao modoExibicao = mainPresenter.getModoExibicao();
        assertThat(modoExibicao, is(ModoExibicao.LISTA));

        mainPresenter.trocaModoExibicao();

        modoExibicao = mainPresenter.getModoExibicao();
        assertThat(modoExibicao, is(ModoExibicao.MAPA));
    }

    @Test
    public void trocarExibicaoListaParaMapa_deveAtualizarMenu_deveExibirMapa() {
        mainPresenter.trocaModoExibicao();

        verify(mainView).atualizaMenu();
        verify(mainView).exibeMapa();
    }

    @Test
    public void trocarExibicaoMapaParaLista_deveAtualizarMenu_deveExibirLista() {
        mainPresenter.trocaModoExibicao();
        mainPresenter.trocaModoExibicao();

        verify(mainView, times(2)).atualizaMenu();
        verify(mainView).exibeLista();
    }

    @Test
    public void temperaturaInicial_deveSerCelsius() {
        UnidadeTemperatura unidadeTemperatura = mainPresenter.getUnidadeTemperatura();

        assertThat(unidadeTemperatura, is(UnidadeTemperatura.CELSIUS));
    }

    @Test
    public void trocarTemperatura_deveTrocarDeCelciusParaFahrenheit() {
        UnidadeTemperatura unidadeTemperatura = mainPresenter.getUnidadeTemperatura();
        assertThat(unidadeTemperatura, is(UnidadeTemperatura.CELSIUS));

        mainPresenter.trocaUnidadeTemperatura();

        unidadeTemperatura = mainPresenter.getUnidadeTemperatura();
        assertThat(unidadeTemperatura, is(UnidadeTemperatura.FAHRENHEIT));
    }

    @Test
    public void trocarTemperatura_deveTrocarConversor_deveAtualizarMenu() {
        mainPresenter.trocaUnidadeTemperatura();

        verify(mainView).setConversorTemperatura(anyObject());
        verify(mainView).atualizaMenu();
    }
}
