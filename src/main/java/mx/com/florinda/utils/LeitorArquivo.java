package mx.com.florinda.utils;

import mx.com.florinda.models.ItemCardapio;

import java.io.InputStream;
import java.util.List;

public abstract class LeitorArquivo {

    private final String conteudo;

    public LeitorArquivo(String nomeArquivo) {
        try {
            if (nomeArquivo == null || nomeArquivo.isEmpty()) {
                throw new IllegalArgumentException("O nome do arquivo deve ser informado.");
            } else {
                if (!nomeArquivo.endsWith(".csv") && !nomeArquivo.endsWith(".json")) {
                    throw new IllegalArgumentException("Tipo de arquivo inválido " + nomeArquivo);
                }
            }
            //Path arquivo = Path.of(nomeArquivo);
            try (InputStream is = this.getClass().getResourceAsStream(nomeArquivo)) {
                if (is != null) {
                    this.conteudo = new String(is.readAllBytes()); //Files.readString(arquivo);
                } else {
                    throw new RuntimeException("Arquivo " + nomeArquivo + " não encontrado!");
                }
            } catch (Exception e) {
                throw new RuntimeException("Não foi possível ler o conteúdo do arquivo " + nomeArquivo + "!");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível ler o conteúdo do arquivo " + nomeArquivo + "!");
        }
    }

    protected String getConteudo() {
        return conteudo;
    }

    public abstract List<ItemCardapio> getItensCardapio();
}
