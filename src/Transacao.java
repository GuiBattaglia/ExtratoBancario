import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transacao {

    private String agencia;
    private String conta;
    private String banco;
    private String titular;
    private String operacao;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public Transacao(
            String agencia,
            String conta,
            String banco,
            String titular,
            String operacao,
            BigDecimal valor,
            LocalDateTime dataHora) {

        this.agencia = agencia;
        this.conta = conta;
        this.banco = banco;
        this.titular = titular;
        this.operacao = operacao;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public String getBanco() {
        return banco;
    }

    public String getTitular() {
        return titular;
    }

    public String getOperacao() {
        return operacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Transacao)) {
            return false;
        }

        Transacao that = (Transacao) o;

        return Objects.equals(agencia, that.agencia)
                && Objects.equals(conta, that.conta)
                && Objects.equals(banco, that.banco)
                && Objects.equals(titular, that.titular)
                && Objects.equals(operacao, that.operacao)
                && Objects.equals(valor, that.valor)
                && Objects.equals(dataHora, that.dataHora);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                agencia,
                conta,
                banco,
                titular,
                operacao,
                valor,
                dataHora
        );
    }
}