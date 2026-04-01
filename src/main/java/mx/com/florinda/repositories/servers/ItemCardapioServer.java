package mx.com.florinda.repositories.servers;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemCardapioServer {

    void main() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);

        server.createContext("/itens-cardapio.json", exchange -> {;
            String json = Files.readString(Path.of("itens-cardapio.json"));
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
