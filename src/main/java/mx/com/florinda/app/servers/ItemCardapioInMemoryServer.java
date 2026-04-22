package mx.com.florinda.app.servers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import mx.com.florinda.controllers.Cardapio;
import mx.com.florinda.models.ItemCardapio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemCardapioInMemoryServer {

    void main() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);

        server.createContext("/itens-cardapio", exchange -> {
            Cardapio cardapio = new Cardapio("/databases/itens-cardapio.json");
            CopyOnWriteArrayList<ItemCardapio> itensCardapio = cardapio.getItens();
            String json = new Gson().toJson(itensCardapio);
            byte[] bytes = json.getBytes();

            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(bytes);
        });

        IO.println("Servidor iniciado!");
        server.start();
    }
}
