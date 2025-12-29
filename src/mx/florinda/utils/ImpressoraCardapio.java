package mx.florinda.utils;

import mx.florinda.model.ItemCardapio;

public class ImpressoraCardapio {

    public static String criarImpressaoItem(ItemCardapio item) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(item.getId()).append("\n");
        sb.append("NOME: ").append(item.getNome()).append("\n");
        sb.append("DESCRIÇÃO: ").append(item.getDescricao()).append("\n");

        if (item.isEmPromocao()) {
            sb.append("ITEM EM PROMOÇÃO").append("\n");
            sb.append("DE: ").append(item.getPreco()).append(" POR: ").append(item.getPrecoComDesconto()).append("\n");
            sb.append((item.getPorcentagemDesconto() * 100)).append("% DE DESCONTO").append("\n");
        } else {
            sb.append("PREÇO: ").append(item.getPreco()).append("\n");
        }

        if (item.getImposto() > 0.00) {
            sb.append("VALOR DO IMPOSTO: ").append(item.getImposto()).append("\n");
        } else {
            sb.append("ITEM ISENTO DE TRIBUTAÇÃO").append("\n");
        }

        sb.append("CATEGORIA: ").append(item.getCategoria()).append("\n");
        return sb.toString();
    }
}
