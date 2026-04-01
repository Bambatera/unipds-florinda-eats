package mx.com.florinda.utils.externals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteViaCep {

    static void main() throws MalformedURLException {

//        URL url = new URL("https://viacep.com.br/ws/72316123/json/");

        // Opção 1: URLConnection
//        URLConnection connection = url.openConnection();
//        try (InputStream is = connection.getInputStream()) {
//            try (Scanner scanner = new Scanner(is)) {
//                while (scanner.hasNext()) {
//                    IO.println(scanner.nextLine());
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (IOException | RuntimeException e) {
//            throw new RuntimeException(e);
//        }

        // Opção 2: Scanner diretamente no URL
//        try (Scanner scanner = new Scanner(url.openStream())) {
//            while (scanner.hasNext()) {
//                IO.println(scanner.nextLine());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // Opção 3: Java 11 - URI + HttpClient
        URI uri = URI.create("https://viacep.com.br/ws/72316123/json/");

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
