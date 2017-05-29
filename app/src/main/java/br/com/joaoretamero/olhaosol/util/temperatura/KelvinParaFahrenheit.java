package br.com.joaoretamero.olhaosol.util.temperatura;


public class KelvinParaFahrenheit implements ConversorTemperatura {

    private static final float ZERO_KELVIN_EM_FAHRENHEIT = 459.67f;

    @Override
    public float converter(float temperatura) {
        return (temperatura * 1.8f) - ZERO_KELVIN_EM_FAHRENHEIT;
    }
}
