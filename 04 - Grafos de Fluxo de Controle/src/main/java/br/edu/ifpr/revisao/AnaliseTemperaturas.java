package br.edu.ifpr.revisao;

public class AnaliseTemperaturas {
    public int contarAlertas(double[] temperaturas) {
        int alertas = 0;
        int indice = 0;

        while (indice < temperaturas.length) {
            if (temperaturas[indice] < 0) {
                alertas += 2;
            } else if (temperaturas[indice] > 35) {
                alertas++;
            }

            indice++;
        }

        return alertas;
    }
}
