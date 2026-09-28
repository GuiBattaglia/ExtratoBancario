import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aplicacao {

    public static void main(String[] args) {

        String arquivo = "operacoes.csv";

        Map<String, List<Transacao>> mapa = new HashMap<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(arquivo))) {

            // Ignora o cabeçalho
            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(",");

                String agencia = dados[0];
                String conta = dados[1];
                String banco = dados[2];
                String titular = dados[3];
                String operacao = dados[4];

                LocalDateTime dataHora =
                        LocalDateTime.parse(dados[5]);

                BigDecimal valor =
                        new BigDecimal(dados[6]);

                Transacao transacao = new Transacao(
                        agencia,
                        conta,
                        banco,
                        titular,
                        operacao,
                        valor,
                        dataHora
                );

                // Agrupa pelo banco + agência + conta
                String chave = banco + "-" + agencia + "-" + conta;

                mapa
                        .computeIfAbsent(chave, k -> new ArrayList<>())
                        .add(transacao);
            }

        } catch (IOException e) {

            System.out.println(
                    "Erro ao ler o arquivo: " + e.getMessage()
            );

            return;
        }

        processar(mapa);
    }

    public static void processar(
            Map<String, List<Transacao>> mapa) {

        for (List<Transacao> lista : mapa.values()) {

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

            // Calcula o saldo final
            for (Transacao t : lista) {

                if (t.getOperacao()
                        .equalsIgnoreCase("DEPOSITO")) {

                    saldo = saldo.add(t.getValor());

                } else if (t.getOperacao()
                        .equalsIgnoreCase("SAQUE")) {

                    // Verifica se existe saldo suficiente
                    if (t.getValor().compareTo(saldo) <= 0) {

                        saldo = saldo.subtract(t.getValor());
                    }
                }
            }

            // Dados da conta
            Transacao primeira = lista.get(0);

            System.out.println();
            System.out.println("======================================");
            System.out.println("CLIENTE: " + primeira.getTitular());
            System.out.println("BANCO: " + primeira.getBanco());
            System.out.println("AGÊNCIA: " + primeira.getAgencia());
            System.out.println("CONTA: " + primeira.getConta());
            System.out.println("--------------------------------------");
            System.out.println("SALDO FINAL: R$ " + saldo);
            System.out.println("======================================");
        }
    }
}