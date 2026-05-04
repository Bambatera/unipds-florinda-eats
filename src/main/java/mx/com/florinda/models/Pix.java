package mx.com.florinda.models;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Pix implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private Long id;
    private BigDecimal valor;
    private String chaveDestino;
    private Instant dataHora;
    private String mensagem;

    public Pix(Long id, BigDecimal valor, String chaveDestino) {
        this.id = id;
        this.valor = valor;
        this.chaveDestino = chaveDestino;
    }

    public Pix(Long id, BigDecimal valor, String chaveDestino, Instant dataHora, String mensagem) {
        this.id = id;
        this.valor = valor;
        this.chaveDestino = chaveDestino;
        this.dataHora = dataHora;
        this.mensagem = mensagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getChaveDestino() {
        return chaveDestino;
    }

    public void setChaveDestino(String chaveDestino) {
        this.chaveDestino = chaveDestino;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pix pix = (Pix) o;
        return Objects.equals(id, pix.id) && Objects.equals(valor, pix.valor) && Objects.equals(chaveDestino, pix.chaveDestino) && Objects.equals(dataHora, pix.dataHora) && Objects.equals(mensagem, pix.mensagem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, chaveDestino, dataHora, mensagem);
    }

    @Override
    public String toString() {
        return "{\n\r\t\"id\": " + id + ",\n\r\t" +
                "\"valor\": " + valor + ",\n\r\t" +
                "\"chaveDestino\": \"" + chaveDestino + "\",\n\r\t" +
                "\"dataHora\": \"" + dataHora + "\",\n\r\t" +
                "\"mensagem\": \"" + mensagem + "\"" +
                "\n\r}";
    }
}
