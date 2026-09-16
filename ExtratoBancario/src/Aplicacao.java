import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Aplicacao {

    public static void main(String[] args) {

        String arquivo = "operacoes.csv";

        Map<String, List<Transacao>> mapa = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            // Ignora o cabeçalho
            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(",");

                String titular = dados[3];
                String operacao = dados[4];

                LocalDateTime dataHora =
                        LocalDateTime.parse(dados[5]);

                BigDecimal valor =
                        new BigDecimal(dados[6]);

                Transacao transacao = new Transacao(
                        titular,
                        operacao,
                        valor,
                        dataHora
                );

                mapa
                        .computeIfAbsent(titular, k -> new ArrayList<>())
                        .add(transacao);
            }

        } catch (IOException e) {

            System.out.println("Erro ao ler o arquivo: "
                    + e.getMessage());

            return;
        }

        processar(mapa);
    }

    public static void processar(Map<String, List<Transacao>> mapa) {

        for (String titular : mapa.keySet()) {

            List<Transacao> lista = mapa.get(titular);

            // Remove transações duplicadas
            Set<Transacao> conjunto =
                    new HashSet<>(lista);

            lista = new ArrayList<>(conjunto);

            // Ordena pela data/hora
            lista.sort(
                    Comparator.comparing(
                            Transacao::getDataHora
                    )
            );

            BigDecimal saldo = BigDecimal.ZERO;

            System.out.println();
            System.out.println("======================================");
            System.out.println("TITULAR: " + titular);
            System.out.println("======================================");

            for (Transacao t : lista) {

                String operacao = t.getOperacao();
                BigDecimal valor = t.getValor();

                System.out.println(
                        t.getDataHora()
                                + " | "
                                + operacao
                                + " | R$ "
                                + valor
                );

                if (operacao.equalsIgnoreCase("DEPOSITO")) {

                    saldo = saldo.add(valor);

                    System.out.println(
                            "Depósito realizado."
                    );

                    System.out.println(
                            "Saldo: R$ " + saldo
                    );

                } else if (operacao.equalsIgnoreCase("SAQUE")) {

                    if (valor.compareTo(saldo) <= 0) {

                        saldo = saldo.subtract(valor);

                        System.out.println(
                                "Saque realizado."
                        );

                        System.out.println(
                                "Saldo: R$ " + saldo
                        );

                    } else {

                        System.out.println(
                                "Saldo insuficiente."
                        );

                        System.out.println(
                                "Saldo: R$ " + saldo
                        );
                    }
                }

                System.out.println("--------------------------------------");
            }

            System.out.println(
                    "SALDO FINAL: R$ " + saldo
            );

            System.out.println("======================================");
        }
    }
}