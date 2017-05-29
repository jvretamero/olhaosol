package br.com.joaoretamero.olhaosol.main;


import java.util.List;

import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;

public interface PrevisoesView {

    void exibeCarregamento(boolean visivel);

    void exibirPrevisoes(List<PrevisaoClimatica> previsoes);

    void setConversorTemperatura(ConversorTemperatura conversorTemperatura);
}
