package br.com.joaoretamero.olhaosol.util.temperatura;


import br.com.joaoretamero.olhaosol.util.NumeroUtil;

public class TemperaturaUtil {

    public static String formatar(float temperatura, String formato) {
        return String.format(formato, NumeroUtil.formatar(temperatura, 1));
    }
}
