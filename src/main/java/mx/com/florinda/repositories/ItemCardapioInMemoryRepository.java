package mx.com.florinda.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ItemCardapioInMemoryRepository {

    private String conteudo = "";
    private final String nomeArquivo;

    public ItemCardapioInMemoryRepository(String nomeArquivo) {
        if (nomeArquivo == null || nomeArquivo.isEmpty()) {
            throw new RuntimeException("A origem de dados deve ser informada!");
        }
        this.nomeArquivo = nomeArquivo;
    }

    public String getConteudo() {
        if (!conteudo.isEmpty()) {
            return conteudo;
        }
        try (InputStream is = this.getClass().getResourceAsStream(nomeArquivo)) {
            if (is == null) {
                throw new RuntimeException("ATENÇÃO: O recurso solicitação não possui dados!!!");
            }
            this.conteudo = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("O recurso solicitado não foi encontrado!");
        }
        return conteudo;
    }
}
