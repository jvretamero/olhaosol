package br.com.joaoretamero.olhaosol.util.temperatura;


public class KelvinParaCelcius implements ConversorTemperatura {

    private static final float ZERO_KELVIN_EM_CELCIUS = 273.15f;

    @Override
    public float converter(float temperatura) {
        return temperatura - ZERO_KELVIN_EM_CELCIUS;
    }
}
