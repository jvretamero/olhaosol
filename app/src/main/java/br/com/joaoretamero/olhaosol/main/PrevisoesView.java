package br.com.joaoretamero.olhaosol.main;


import java.util.List;

import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;

public interface PrevisoesView {

    void exibeCarregamento(boolean visivel);

    void exibirPrevisoes(List<PrevisaoClimatica> previsoes);
}
