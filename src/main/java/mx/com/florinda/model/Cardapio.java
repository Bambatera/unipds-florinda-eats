package mx.com.florinda.model;

import mx.com.florinda.utils.LeitorArquivoCsv;
import mx.com.florinda.utils.LeitorArquivoJson;

public class Cardapio {

    ItemCardapio[] itens;

    public Cardapio(String nomeArquivo) {
        if (nomeArquivo.endsWith("csv")) {
            this.itens = new LeitorArquivoCsv(nomeArquivo).getItensCardapio();
        } else if (nomeArquivo.endsWith("json")) {
            this.itens = new LeitorArquivoJson(nomeArquivo).getItensCardapio();
        } else {
            throw  new IllegalArgumentException("Tipo de arquivo inválido " + nomeArquivo + "!");
        }
    }

    public ItemCardapio[] getItensCardapio() {
        return this.itens;
    }
}
