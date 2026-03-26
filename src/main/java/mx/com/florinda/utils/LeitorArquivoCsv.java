package mx.com.florinda.utils;

import mx.com.florinda.model.CategoriaCardapio;
import mx.com.florinda.model.ItemCardapio;
import mx.com.florinda.model.ItemCardapioIsento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeitorArquivoCsv extends LeitorArquivo {

    public LeitorArquivoCsv(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public List<ItemCardapio> getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private List<ItemCardapio> lerConteudo(String conteudoArquivo) {
        if (conteudoArquivo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new ArrayList<>();
        }

        String[] linhasConteudo = conteudoArquivo.split("\n");
        List<String> conteudos = new ArrayList<>();

        try {
            Long.parseLong(linhasConteudo[0].split(",")[0]);
            conteudos.addAll(Arrays.asList(linhasConteudo));
        } catch (NumberFormatException e) {
            for (int i = 1; i < linhasConteudo.length; i++) {
                conteudos.add(linhasConteudo[i]);
            }
        }

        List<ItemCardapio> itens = new ArrayList<>();

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
