package br.com.joaoretamero.olhaosol.main;


import java.util.List;

import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import br.com.joaoretamero.olhaosol.util.temperatura.ConversorTemperatura;

public interface PrevisoesView {

    void exibePrevisoes(List<PrevisaoClimatica> previsoes, ConversorTemperatura conversorTemperatura);

    void setConversorTemperatura(ConversorTemperatura conversorTemperatura);
}
