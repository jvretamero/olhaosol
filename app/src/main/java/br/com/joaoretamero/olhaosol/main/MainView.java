package br.com.joaoretamero.olhaosol.main;


public interface MainView extends PrevisoesView {

    void exibeLista();

    void exibeMapa();

    void atualizaMenu();

    void exibeCarregamento(boolean visivel);

    void requisitarLocalizacao();

    void exibeObtendoLocalizacao(boolean visivel);

    void explicarMotivoPermissao();

    void requisitarPermissao();

    void exibirMensagemSemPermissao();

    void exibieMensagemSemLocalizacao();
}
