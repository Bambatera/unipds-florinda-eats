import mx.com.florinda.services.CardapioServices;
import mx.com.florinda.controller.Cardapio;

void main() {

    try {
        String nomeArquivo = IO.readln("INFORME O NOME DO ARQUIVO PARA LEITURA: ");
        if (nomeArquivo == null || nomeArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do arquivo deve ser informado.");
        }
        CardapioServices cardapioServices = new CardapioServices(new Cardapio(nomeArquivo));
        cardapioServices.exibirOpcoes();
    } catch (IllegalArgumentException e) {
        throw new RuntimeException(e);
    }

}