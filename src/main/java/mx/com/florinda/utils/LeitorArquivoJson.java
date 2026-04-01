package mx.com.florinda.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import mx.com.florinda.models.ItemCardapio;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class LeitorArquivoJson extends LeitorArquivo {

    public LeitorArquivoJson(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public CopyOnWriteArrayList<ItemCardapio> getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private CopyOnWriteArrayList<ItemCardapio> lerConteudo(String conteudo) {
        if (conteudo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new CopyOnWriteArrayList<>();
        }

        try {
            return Arrays
                    .stream(new Gson().fromJson(conteudo, ItemCardapio[].class))
                    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        } catch (JsonSyntaxException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            throw new RuntimeException("Não foi possível interpretar o conteúdo do arquivo!");
        }
    }

}
