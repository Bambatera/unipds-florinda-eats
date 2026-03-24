package mx.florinda.cli;

import mx.florinda.model.Cardapio;
import mx.florinda.model.ItemCardapio;
import mx.florinda.utils.ImpressoraCardapio;

public class CardapioServices extends Cardapio {

    public CardapioServices(String nomeArquivo) {
        super(nomeArquivo);
    }

    public static void exibirOpcoes(Cardapio cardapio) {
        int opcao;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECIONE UMA OPÇÃO:").append("\n");
        sb.append("--------------------").append("\n");
        sb.append("1. LISTAR CARDÁPIO").append("\n");
        sb.append("2. LISTAR ITEM CARDÁPIO").append("\n");
        sb.append("3. LISTAR ITENS EM PROMOÇÃO").append("\n");
        sb.append("4. IMPRIMIR TAMANHO DO CARDÁPIO").append("\n");
        sb.append("5. IMPRIMIR QTDE DE ITENS EM PROMOÇÃO").append("\n");
        sb.append("6. VALOR TOTAL DOS PREÇOS").append("\n");
        sb.append("0. FINALIZAR PROGRAMA").append("\n");
        sb.append("-------------------").append("\n");
        sb.append("> ");

        do {
            try {
                String selected = IO.readln(sb.toString());
                opcao = Integer.parseInt(selected);
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    imprimirListagem(cardapio.getItensCardapio(), false);
                    break;
                case 2:
                    String opcao2 = IO.readln("INFORME O NÚMERO DO ITEM: ");
                    imprimirItem(cardapio.getItensCardapio(), Integer.parseInt(opcao2));
                    break;
                case 3:
                    imprimirListagem(cardapio.getItensCardapio(), true);
                    break;
                case 4:
                    IO.println("---------------------------");
                    IO.println("EXISTEM " + cardapio.getItensCardapio().length + " ITENS NO CARDÁPIO");
                    IO.println("---------------------------");
                    break;
                case 5:
                    mostrarQtdeItensPromocao(cardapio.getItensCardapio());
                    break;
                case 6:
                    valorTotalCardapio(cardapio.getItensCardapio());
                    break;
                default:
                    if (opcao != 0) {
                        IO.println("\n\n*** OPÇÃO INVÁLIDA! ***\n\n");
                    }
                    break;
            }
        } while (opcao != 0);
    }

    static void imprimirListagem(ItemCardapio[] itens, boolean somentePromocoes) {
        IO.println("---------------------------");
        IO.println("MOSTRANDO ITENS DO CARDÁPIO");
        IO.println("---------------------------");

        for (ItemCardapio item : itens) {
            if (somentePromocoes && item.isEmPromocao()) {
                IO.println(ImpressoraCardapio.criarImpressaoItem(item));
            } else if (!somentePromocoes){
                IO.println(ImpressoraCardapio.criarImpressaoItem(item));
            }
        }

        IO.println("---------------------------");
    }

    static void imprimirItem(ItemCardapio[] itens, Integer idItem) {
        IO.println("---------------------------");
        IO.println("MOSTRANDO ITENS DO CARDÁPIO");
        IO.println("---------------------------");
        IO.println(ImpressoraCardapio.criarImpressaoItem(itens[idItem - 1]));
        IO.println("---------------------------");
    }

    static void mostrarQtdeItensPromocao(ItemCardapio[] itens) {
        int count = 0;
        IO.println("---------------------------");
        for (ItemCardapio item : itens) {
            if (item.isEmPromocao()) {
                count++;
            }
        }
        if (count > 0) {
            IO.println("EXISTEM " + count + " ITENS EM PROMOÇÃO");
        } else {
            IO.println("NÃO HÁ ITENS EM PROMOÇÃO");
        }
        IO.println("---------------------------");
    }

    static void valorTotalCardapio(ItemCardapio[] itens) {
        double valorTotal = 0;
        IO.println("---------------------------");
        for (ItemCardapio item : itens) {
            if (item.isEmPromocao()) {
                valorTotal += item.getPrecoComDesconto();
            } else {
                valorTotal += item.getPreco();
            }
        }
        IO.println("CARDÁPIO AVALIADO EM: " + valorTotal);
        IO.println("---------------------------");
    }
}
