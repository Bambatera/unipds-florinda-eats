package mx.com.florinda.utils;

import com.google.gson.Gson;
import mx.com.florinda.model.ItemCardapio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeitorArquivoJson extends LeitorArquivo {

    public LeitorArquivoJson(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public List<ItemCardapio> getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    @SuppressWarnings("unchecked")
    private List<ItemCardapio> lerConteudo(String conteudo) {
        if (conteudo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new ArrayList<>();
        }

        var itens = new Gson().fromJson(conteudo, ItemCardapio[].class);
        List<ItemCardapio> itensCardapio = Arrays.stream(itens).collect(Collectors.toList());
        return itensCardapio;
    }

}
