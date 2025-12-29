import mx.florinda.cli.CardapioServices;
import mx.florinda.model.Cardapio;

void main() {

    String nomeArquivo = IO.readln("INFORME O NOME DO ARQUIVO PARA LEITURA: ");
    CardapioServices.exibirOpcoes(new Cardapio(nomeArquivo));

}