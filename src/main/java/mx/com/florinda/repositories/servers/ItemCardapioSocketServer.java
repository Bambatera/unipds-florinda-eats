package mx.com.florinda.repositories.servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ItemCardapioSocketServer {

    static void main() throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            IO.println("Servidor iniciado!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> trataRequisicao(clientSocket));
                thread.start();
            }

        }

    }

    private static void trataRequisicao(Socket clientSocket) {
        try (clientSocket) {
            InputStream clientIS = clientSocket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();
            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);
            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            IO.println("Requisição recebida: " + request);

            Thread.sleep(250);

            String json;
            try (InputStream dataIS = ItemCardapioSocketServer.class.getResourceAsStream("/databases/itens-cardapio.json")) {
                byte[] bytes = dataIS.readAllBytes();
                json = new String(bytes, StandardCharsets.UTF_8);
            }

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);

            clientOut.println("HTTP/1.1 200 OK");
            clientOut.println("Content-Type: application/json; charset=UTF-8");
            clientOut.println();
            clientOut.println(json);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
