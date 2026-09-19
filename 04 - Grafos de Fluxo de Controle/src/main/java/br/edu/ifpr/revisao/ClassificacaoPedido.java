package br.edu.ifpr.revisao;

public class ClassificacaoPedido {
    public String classificarPedido(
            double valor,
            boolean clienteVip,
            boolean pagamentoAprovado) {

        double desconto = 0;

        if (valor >= 500) {
            desconto = 10;
        }

        if (clienteVip) {
            desconto += 5;
        }

        if (!pagamentoAprovado) {
            return "PAGAMENTO RECUSADO";
        }

        double valorFinal = valor - (valor * desconto / 100);
        return "PEDIDO APROVADO: " + valorFinal;
    }
}
