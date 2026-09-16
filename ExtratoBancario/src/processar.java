import java.math.BigDecimal;
import java.util.*;

public static void processar(Map<String, List<Transacao>> mapa) {

    for (String titular : mapa.keySet()) {

        List<Transacao> lista = mapa.get(titular);

        // Coloca as transações em ordem de data/hora
        lista.sort(Comparator.comparing(Transacao::getDataHora));

        BigDecimal saldo = BigDecimal.ZERO;

        System.out.println("=================================");
        System.out.println("Titular: " + titular);
        System.out.println("EXTRATO");
        System.out.println("=================================");

        for (Transacao t : lista) {

            String operacao = t.getOperacao();
            BigDecimal valor = t.getValor();

            System.out.println(
                    t.getDataHora() + " | " +
                            operacao + " | R$ " + valor
            );

            if (operacao.equalsIgnoreCase("DEPOSITO")) {

                saldo = saldo.add(valor);

                System.out.println("Depósito realizado.");
                System.out.println("Saldo: R$ " + saldo);

            } else if (operacao.equalsIgnoreCase("SAQUE")) {

                if (valor.compareTo(saldo) <= 0) {

                    // Tem saldo suficiente
                    saldo = saldo.subtract(valor);

                    System.out.println("Saque realizado.");
                    System.out.println("Saldo: R$ " + saldo);

                } else {

                    // Não tem saldo suficiente
                    System.out.println("Saldo insuficiente.");
                    System.out.println("Saldo: R$ " + saldo);
                }
            }

            System.out.println("---------------------------------");
        }

        System.out.println("Saldo final: R$ " + saldo);
        System.out.println("=================================");
        System.out.println();
    }
}

void main() {
}