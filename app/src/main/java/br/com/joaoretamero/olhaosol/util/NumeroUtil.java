package br.com.joaoretamero.olhaosol.util;


import java.text.DecimalFormat;

public class NumeroUtil {

    public static String formatar(float numero, int casasDecimais) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(casasDecimais);
        decimalFormat.setMinimumFractionDigits(casasDecimais);

        return decimalFormat.format(numero);
    }
}
