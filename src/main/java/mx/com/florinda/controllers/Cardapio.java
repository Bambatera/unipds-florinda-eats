package mx.com.florinda.controllers;

import mx.com.florinda.models.ItemCardapio;
import mx.com.florinda.utils.LeitorArquivoCsv;
import mx.com.florinda.utils.LeitorArquivoJson;

import java.util.concurrent.CopyOnWriteArrayList;

public class Cardapio {

    CopyOnWriteArrayList<ItemCardapio> itens;

    public Cardapio(String nomeArquivo) {
        if (nomeArquivo.endsWith("csv")) {
            this.itens = new LeitorArquivoCsv(nomeArquivo).getItensCardapio();
        } else if (nomeArquivo.endsWith("json")) {
            LeitorArquivoJson leitor = new LeitorArquivoJson(nomeArquivo);
            this.itens = leitor.getItensCardapio();
        } else {
            throw new IllegalArgumentException("Tipo de arquivo inválido " + nomeArquivo + "!");
        }
    }

    public CopyOnWriteArrayList<ItemCardapio> getItens() {
        return itens;
    }
}
