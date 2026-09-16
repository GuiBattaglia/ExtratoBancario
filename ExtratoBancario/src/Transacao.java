import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transacao {

    private String titular;
    private String operacao;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public Transacao(String titular,
                     String operacao,
                     BigDecimal valor,
                     LocalDateTime dataHora) {

        this.titular = titular;
        this.operacao = operacao;
        this.valor = valor;
        this.dataHora = dataHora;
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

        return Objects.equals(valor, that.valor)
                && Objects.equals(operacao, that.operacao)
                && Objects.equals(dataHora, that.dataHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, operacao, dataHora);
    }
}