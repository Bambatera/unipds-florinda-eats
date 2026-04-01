package mx.com.florinda.utils;

import mx.com.florinda.models.CategoriaCardapio;
import mx.com.florinda.models.ItemCardapio;
import mx.com.florinda.models.ItemCardapioIsento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LeitorArquivoCsv extends LeitorArquivo {

    public LeitorArquivoCsv(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public CopyOnWriteArrayList<ItemCardapio> getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private CopyOnWriteArrayList<ItemCardapio> lerConteudo(String conteudoArquivo) {
        if (conteudoArquivo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new CopyOnWriteArrayList<>();
        }

        String[] linhasConteudo = conteudoArquivo.split("\n");
        List<String> conteudos = new ArrayList<>();

        try {
            Long.parseLong(linhasConteudo[0].split(",")[0]);
            conteudos.addAll(Arrays.asList(linhasConteudo));
        } catch (NumberFormatException e) {
            conteudos.addAll(Arrays.asList(linhasConteudo).subList(1, linhasConteudo.length));
        }

        CopyOnWriteArrayList<ItemCardapio> itens = new CopyOnWriteArrayList<>();

        for (String conteudo : conteudos) {
            String[] atributos = conteudo.split(";");

            long id = Long.parseLong(atributos[0]);
            String nome = atributos[1];
            String descricao = atributos[2];
            double preco = Double.parseDouble(atributos[3]);
            CategoriaCardapio categoria = CategoriaCardapio.valueOf(atributos[4]);
            boolean emPromocao = Boolean.parseBoolean(atributos[5]);

            ItemCardapio item;
            if (Boolean.parseBoolean(atributos[7])) {
                item = new ItemCardapioIsento(id, nome, descricao, preco, categoria);
            } else {
                item = new ItemCardapio(id, nome, descricao, preco, categoria);
            }

            if (emPromocao) {
                double precoDesconto = Double.parseDouble(atributos[6]);
                item.setPromocao(precoDesconto);
            }

            itens.add(item);
        }

        return itens;
    }
}
