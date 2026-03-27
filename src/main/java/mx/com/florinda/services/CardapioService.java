package mx.com.florinda.services;

import mx.com.florinda.controllers.Cardapio;
import mx.com.florinda.models.ItemCardapio;

import java.util.List;

public class CardapioService {

    private final Cardapio cardapio;

    public CardapioService(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public void exibirOpcoes() {
        ImpressoraService.limparTela();
        int opcao;
        StringBuilder sb = new StringBuilder();
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
            String selected = IO.readln(ImpressoraService.imprimirMenu("SELECIONE UMA OPÇÃO:", sb));
            if (selected != null && this.isNumeric(selected)) {
                opcao = Integer.parseInt(selected);
            } else {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    imprimirListagem(cardapio.getItensCardapio(), false);
                    break;
                case 2:
                    ImpressoraService.limparTela();
                    String opcao2 = IO.readln("INFORME O NÚMERO DO ITEM: ");
                    if (opcao2 == null || !isNumeric(opcao2)
                            || this.cardapio.getItensCardapio().size() < Integer.parseInt(opcao2)) {
                        IO.println("\n\nOPÇÃO INVÁLIDA!\n\n");
                        break;
                    }
                    imprimirItem(cardapio.getItensCardapio(), Integer.parseInt(opcao2));
                    break;
                case 3:
                    imprimirListagem(cardapio.getItensCardapio(), true);
                    break;
                case 4:
                    IO.println("-".repeat(50));
                    ImpressoraService.imprimirCorpo(String.format("EXISTEM %d ITENS NO CARDÁPIO", this.cardapio.getItensCardapio().size()));
                    break;
                case 5:
                    mostrarQtdeItensPromocao(cardapio.getItensCardapio());
                    break;
                case 6:
                    valorTotalCardapio(cardapio.getItensCardapio());
                    break;
                default:
                    ImpressoraService.limparTela();
                    if (opcao != 0) {
                        IO.println("\n\n*** OPÇÃO INVÁLIDA! ***\n\n");
                    } else {
                        ImpressoraService.imprimirCorpo("Encerrando programa...");
                    }
                    ImpressoraService.pressEnter();
                    break;
            }
        } while (opcao != 0);
    }

    private void imprimirListagem(List<ItemCardapio> itens, boolean somentePromocoes) {
        ImpressoraService.limparTela();
        ImpressoraService.imprimirTitulo("MOSTRANDO ITENS DO CARDÁPIO");

        for (ItemCardapio item : itens) {
            if (somentePromocoes && item.isEmPromocao()) {
                ImpressoraService.imprimirCorpo(item.toString());
            } else if (!somentePromocoes) {
                ImpressoraService.imprimirCorpo(item.toString());
            }
        }

        ImpressoraService.pressEnter();
    }

    private void imprimirItem(List<ItemCardapio> itens, Integer idItem) {
        ImpressoraService.limparTela();

        ImpressoraService.imprimirTitulo("MOSTRANDO ITENS DO CARDÁPIO");
        itens.stream().filter(item -> item.getId() == idItem)
                .findFirst()
                .ifPresent(item -> ImpressoraService.imprimirCorpo(item.toString()));

        ImpressoraService.pressEnter();
    }

    private void mostrarQtdeItensPromocao(List<ItemCardapio> itens) {
        long count = itens.stream().filter(ItemCardapio::isEmPromocao).count();
        ImpressoraService.limparTela();

        IO.println("-".repeat(50));
        if (count > 0) {
            ImpressoraService.imprimirCorpo(String.format("EXISTEM %d ITENS EM PROMOÇÃO", count));
        } else {
            ImpressoraService.imprimirCorpo("NÃO HÁ ITENS EM PROMOÇÃO");
        }

        ImpressoraService.pressEnter();
    }

    private void valorTotalCardapio(List<ItemCardapio> itens) {
        ImpressoraService.limparTela();

        double valorTotal = 0d;
        IO.println("-".repeat(50));
        for (ItemCardapio item : itens) {
            if (item.isEmPromocao()) {
                valorTotal += item.getPrecoComDesconto();
            } else {
                valorTotal += item.getPreco();
            }
        }
        ImpressoraService.imprimirCorpo("CARDÁPIO AVALIADO EM: " + valorTotal);

        ImpressoraService.pressEnter();
    }


    private boolean isNumeric(String texto) {
        try {
            Integer.parseInt(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
