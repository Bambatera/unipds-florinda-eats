package mx.florinda.utils;

import com.google.gson.Gson;
import mx.florinda.model.ItemCardapio;

public class LeitorArquivoJson extends LeitorArquivo {

    public LeitorArquivoJson(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public ItemCardapio[] getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private ItemCardapio[] lerConteudo(String conteudo) {
        if (conteudo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new ItemCardapio[0];
        }

        return new Gson().fromJson(conteudo, ItemCardapio[].class);
    }

}
