package br.com.joaoretamero.olhaosol.http;


import java.util.List;

import br.com.joaoretamero.olhaosol.modelos.PrevisaoClimatica;
import rx.Observable;

public interface ServicoClimatico {

    Observable<List<PrevisaoClimatica>> getPrevisoesClimaticas(float latitude, float longitude);
}
