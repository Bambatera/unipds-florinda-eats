package mx.com.florinda.services;

import com.google.gson.Gson;
import mx.com.florinda.controllers.Cardapio;
import mx.com.florinda.models.ItemCardapio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ItemCardapioService {

    private static final Cardapio cardapio = new Cardapio("/databases/itens-cardapio.csv");

    static void main() throws IOException {
        List<ItemCardapio> itensCardapio = cardapio.getItens();
        String itensJson = new Gson().toJson(itensCardapio);

        Path path = Path.of("itens-cardapio.json");
        Files.writeString(path, itensJson);
    }
}
