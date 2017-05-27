package br.com.joaoretamero.olhaosol.http;


import javax.inject.Singleton;

import br.com.joaoretamero.olhaosol.main.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {ModuloHttp.class})
public interface ComponenteHttp {

    void inject(MainActivity activity);
}
