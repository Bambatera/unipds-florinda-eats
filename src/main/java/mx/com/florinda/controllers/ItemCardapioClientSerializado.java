package mx.com.florinda.controllers;

import mx.com.florinda.models.ItemCardapio;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ItemCardapioClientSerializado {

    static void main() throws MalformedURLException {

        URI uri = URI.create("http://localhost:8000/itens-cardapio");

        try (HttpClient client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(uri)
                    .header("Accept", "application/x-java-serialized-object")
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            IO.println(response.statusCode());
            IO.println(response.body());

            var bis = new ByteArrayInputStream(response.body());
            var ois = new ObjectInputStream(bis);
            var itensCardapio = (List<ItemCardapio>) ois.readObject();
            itensCardapio.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
