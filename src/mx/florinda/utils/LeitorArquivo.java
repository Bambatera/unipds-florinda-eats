package mx.florinda.utils;

import mx.florinda.model.ItemCardapio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class LeitorArquivo {

    private final String conteudo;

    public LeitorArquivo(String nomeArquivo) throws RuntimeException {
        try {
            if (nomeArquivo == null || nomeArquivo.isEmpty()) {
                throw new IllegalArgumentException("O nome do arquivo deve ser informado.");
            } else {
                if (!nomeArquivo.endsWith(".csv") && !nomeArquivo.endsWith(".json")) {
                    throw new IllegalArgumentException("Tipo de arquivo inválido " + nomeArquivo);
                }
            }
            Path arquivo = Path.of(nomeArquivo);
            this.conteudo = Files.readString(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o conteúdo do arquivo!");
        }
    }

    protected String getConteudo() {
        return conteudo;
    }

    public abstract ItemCardapio[] getItensCardapio();
}
