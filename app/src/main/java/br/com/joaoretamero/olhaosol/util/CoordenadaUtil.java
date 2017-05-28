package br.com.joaoretamero.olhaosol.util;


import br.com.joaoretamero.olhaosol.http.modelos.Coordenada;

public class CoordenadaUtil {

    private static final double RAIO_TERRA_EM_METROS = 6371000;

    public static float distanciaKm(Coordenada cInicial, Coordenada cFinal) {
        double latitude = Math.toRadians(cFinal.latitude - cInicial.latitude);
        double longitude = Math.toRadians(cFinal.longitude - cInicial.longitude);

        double a = Math.sin(latitude / 2) * Math.sin(latitude / 2) +
                Math.cos(Math.toRadians(cInicial.latitude)) * Math.cos(Math.toRadians(cFinal.latitude)) *
                        Math.sin(longitude / 2) * Math.sin(longitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (RAIO_TERRA_EM_METROS * c) / 1000.0f;
    }
}
