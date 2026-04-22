package mx.com.florinda.services;

import mx.com.florinda.models.CategoriaCardapio;
import mx.com.florinda.models.ItemCardapio;
import mx.com.florinda.repositories.ItemCardapioInMemoryRepository;

import java.util.List;

public class CardapioInMemoryService {

    private final ItemCardapioInMemoryRepository itemCardapioRepo = new ItemCardapioInMemoryRepository();

    public CardapioInMemoryService() {
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
        sb.append("7. REMOVER ITEM DO CARDÁPIO").append("\n");
        sb.append("8. ATUALIZAR VALOR DO ITEM").append("\n");
        sb.append("9. INCLUIR ITEM AO CARDÁPIO").append("\n");
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
                    imprimirListagem(this.itemCardapioRepo.listaItensCardapio(), false);
                    break;
                case 2:
                    ImpressoraService.limparTela();
                    String opcao2 = IO.readln("INFORME O NÚMERO DO ITEM: ");
                    if (opcao2 == null || !isNumeric(opcao2)
                            || this.itemCardapioRepo.totalItensCardapio() < Integer.parseInt(opcao2)) {
                        IO.println("\n\nOPÇÃO INVÁLIDA!\n\n");
                        break;
                    }
                    imprimirItem(Integer.parseInt(opcao2));
                    break;
                case 3:
                    imprimirListagem(itemCardapioRepo.listaItensCardapio(), true);
                    break;
                case 4:
                    IO.println("-".repeat(50));
                    ImpressoraService.imprimirCorpo(String.format("EXISTEM %d ITENS NO CARDÁPIO", this.itemCardapioRepo.totalItensCardapio()));
                    break;
                case 5:
                    mostrarQtdeItensPromocao(itemCardapioRepo.listaItensCardapio());
                    break;
                case 6:
                    valorTotalCardapio(itemCardapioRepo.listaItensCardapio());
                    break;
                case 7:
                    removerItem();
                    break;
                case 8:
                    atualizarValorItem();
                    break;
                case 9:
                    incluirItem();
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

    private void incluirItem() {
        ImpressoraService.limparTela();
        ItemCardapio itemCardapio = new ItemCardapio(
            0,
            IO.readln("INFORME O NOME DO ITEM: "),
            IO.readln("INFORME A DESCRIÇÃO DO ITEM: "),
            Double.parseDouble(IO.readln("INFORME O PREÇO DO ITEM: ")),
            CategoriaCardapio.valueOf(IO.readln("INFORME A CATEGORIA: "))
        );
        this.itemCardapioRepo.adicionaItemCardapio(itemCardapio);
        IO.println("\n\nITEM INCLUÍDO COM SUCESSO!\n\n");
        ImpressoraService.pressEnter();
        ImpressoraService.limparTela();
    }

    private void atualizarValorItem() {
        ImpressoraService.limparTela();
        String opcao = IO.readln("INFORME O NÚMERO DO ITEM: ");
        if (opcao == null || !isNumeric(opcao)) {
            IO.println("\n\n*** OPÇÃO INVÁLIDA! ***\n\n");
            ImpressoraService.limparTela();
            ImpressoraService.pressEnter();
            return;
        }

        String valor = IO.readln("INFORME O NOVO VALOR DO ITEM: ");
        if (valor == null || !isNumeric(valor)) {
            IO.println("\n\n*** OPÇÃO INVÁLIDA! ***\n\n");
            ImpressoraService.limparTela();
            ImpressoraService.pressEnter();
            return;
        }

        boolean updated = this.itemCardapioRepo.alteraPrecoItemCardapio(Long.parseLong(opcao), Double.parseDouble(valor));
        if (updated) {
            IO.println("\n\nVALOR ATUALIZADO COM SUCESSO!\n\n");
        } else {
            IO.println("\n\nITEM NÃO ENCONTRADO!\n\n");
        }

        ImpressoraService.limparTela();
        ImpressoraService.pressEnter();
    }

    private void removerItem() {
        ImpressoraService.limparTela();
        String opcao = IO.readln("INFORME O NÚMERO DO ITEM: ");
        if (opcao == null || !isNumeric(opcao)) {
            IO.println("\n\n*** OPÇÃO INVÁLIDA! ***\n\n");
            ImpressoraService.limparTela();
            ImpressoraService.pressEnter();
            return;
        }

        String confirma = IO.readln("CONFIRMAR REMOÇÃO DO ITEM " + opcao + "? (S/N): ");
        if (confirma != null && confirma.equalsIgnoreCase("S")) {
            boolean removed = this.itemCardapioRepo.removeItemCardapio(Long.parseLong(opcao));
            if (removed) {
                IO.println("\n\nITEM REMOVIDO COM SUCESSO!\n\n");
            } else {
                IO.println("\n\nITEM NÃO ENCONTRADO!\n\n");
            }
        } else {
            IO.println("\n\nOPERAÇÃO CANCELADA!\n\n");
        }

        ImpressoraService.limparTela();
        ImpressoraService.pressEnter();
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

    private void imprimirItem(Integer idItem) {
        ImpressoraService.limparTela();

        ImpressoraService.imprimirTitulo("MOSTRANDO ITENS DO CARDÁPIO");
        itemCardapioRepo.itemCardapioPorId(idItem.longValue()).ifPresentOrElse(
                item -> ImpressoraService.imprimirCorpo(item.toString()),
                () -> ImpressoraService.imprimirCorpo("ITEM NÃO ENCONTRADO!"));
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
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
