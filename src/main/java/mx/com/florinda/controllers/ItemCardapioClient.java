package mx.com.florinda.controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ItemCardapioClient {

    static void main() throws MalformedURLException {

        URI uri = URI.create("http://localhost:8000/itens-cardapio.json");

        try (HttpClient client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(uri).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            IO.println(response.statusCode());
            IO.println(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
