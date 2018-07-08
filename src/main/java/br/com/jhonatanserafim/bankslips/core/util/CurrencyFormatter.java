package br.com.jhonatanserafim.bankslips.core.util;

import java.math.BigDecimal;

public class CurrencyFormatter {

    private static final BigDecimal CEM = BigDecimal.valueOf(100d);

    public static String format(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        long valorSemSeparador = valor.multiply(CEM).longValue();
        return String.valueOf(valorSemSeparador);
    }

    public static BigDecimal parse(String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        double valorComSeparador = Double.parseDouble(valor) / CEM.doubleValue();
        return BigDecimal.valueOf(valorComSeparador);
    }
}
