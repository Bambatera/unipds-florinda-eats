package mx.com.florinda.controller;

import mx.com.florinda.model.ItemCardapio;
import mx.com.florinda.utils.LeitorArquivoCsv;
import mx.com.florinda.utils.LeitorArquivoJson;

import java.util.List;

public class Cardapio {

    List<ItemCardapio> itens;

    public Cardapio(String nomeArquivo) {
        if (nomeArquivo.endsWith("csv")) {
            this.itens = new LeitorArquivoCsv(nomeArquivo).getItensCardapio();
        } else if (nomeArquivo.endsWith("json")) {
            this.itens = new LeitorArquivoJson(nomeArquivo).getItensCardapio();
        } else {
            throw new IllegalArgumentException("Tipo de arquivo inválido " + nomeArquivo + "!");
        }
    }

    public List<ItemCardapio> getItensCardapio() {
        return this.itens;
    }
}
