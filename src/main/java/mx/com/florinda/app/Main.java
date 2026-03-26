import mx.com.florinda.controller.Cardapio;
import mx.com.florinda.services.CardapioService;
import mx.com.florinda.services.ImpressoraService;

void main() {

    try {
        ImpressoraService.limparTela();
        ImpressoraService.imprimirTitulo("RESTAURANTE DONA FLORINDA");
        String nomeArquivo = "";
        do {
            nomeArquivo = IO.readln("INFORME O NOME DO ARQUIVO PARA LEITURA: ");
            IO.println("O nome do arquivo deve ser informado.");
            ImpressoraService.limparTela();
        } while (nomeArquivo == null || nomeArquivo.trim().isEmpty());

        CardapioService cardapioServices = new CardapioService(new Cardapio(nomeArquivo));
        cardapioServices.exibirOpcoes();
    } catch (IllegalArgumentException e) {
        throw new RuntimeException(e);
    }

}