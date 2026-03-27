package mx.com.florinda.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import mx.com.florinda.models.ItemCardapio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeitorArquivoJson extends LeitorArquivo {

    public LeitorArquivoJson(String nomeArquivo) throws RuntimeException {
        super(nomeArquivo);
    }

    @Override
    public List<ItemCardapio> getItensCardapio() {
        return this.lerConteudo(super.getConteudo());
    }

    private List<ItemCardapio> lerConteudo(String conteudo) {
        if (conteudo.isEmpty()) {
            IO.println("Arquivo vazio!");
            return new ArrayList<>();
        }

        try {
            return Arrays
                    .stream(new Gson().fromJson(conteudo, ItemCardapio[].class))
                    .collect(Collectors.toList());
        } catch (JsonSyntaxException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            throw new RuntimeException("Não foi possível interpretar o conteúdo do arquivo!");
        }
    }

}
