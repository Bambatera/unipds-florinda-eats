package mx.florinda.utils;

import mx.florinda.model.CategoriaCardapio;
import mx.florinda.model.ItemCardapio;
import mx.florinda.model.ItemCardapioIsento;

public class LeitorArquivoCsv extends LeitorArquivo {

    public LeitorArquivoCsv(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public ItemCardapio[] getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private ItemCardapio[] lerConteudo(String conteudoArquivo) {
        if (conteudoArquivo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new ItemCardapio[0];
        }

        String[] linhasConteudo = conteudoArquivo.split("\r\n");
        String[] conteudos;

        try {
            Long.parseLong(linhasConteudo[0].split(",")[0]);
            conteudos = linhasConteudo;
        } catch (NumberFormatException e) {
            conteudos = new String[(linhasConteudo.length - 1)];
            System.arraycopy(linhasConteudo, 1, conteudos, 0, linhasConteudo.length - 1);
        }

        ItemCardapio[] itens = new ItemCardapio[conteudos.length];

        for (int i = 0; i < conteudos.length; i++) {
            String[] atributos = conteudos[i].split(";");

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

            itens[i] = item;
        }

        return itens;
    }
}
