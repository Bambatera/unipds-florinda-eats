import mx.com.florinda.cli.CardapioServices;
import mx.com.florinda.model.Cardapio;

void main() {

    CardapioServices cardapioServices;

    String nomeArquivo = IO.readln("INFORME O NOME DO ARQUIVO PARA LEITURA: ");
    cardapioServices = new CardapioServices(new Cardapio(nomeArquivo));
    cardapioServices.exibirOpcoes();

}