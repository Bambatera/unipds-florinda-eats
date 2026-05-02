package mx.com.florinda.app.servers;

import com.google.gson.Gson;
import mx.com.florinda.controllers.Cardapio;
import mx.com.florinda.models.ItemCardapio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemCardapioSocketServer {

    private static final Logger logger = Logger.getLogger(ItemCardapioSocketServer.class.getName());
    private static final Cardapio cardapio = new Cardapio("/databases/itens-cardapio.json");
    private static final CopyOnWriteArrayList<ItemCardapio> itensCardapio = cardapio.getItens();

    static void main() throws Exception {

        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                logger.info("Servidor iniciado!");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.submit(() -> trataRequisicao(clientSocket));
                }
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
            logger.finest(request);
            logger.fine("\n\nChegou nova requisição");

            Thread.sleep(250);

            RequestHeader rh = getRequestHeader(request);

            OutputStream clientOS = clientSocket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);

            try {

                if (rh.method().equals("GET") && rh.requestURI().equals("/itens-cardapio")) {
                    getItensCardapio(clientOut);
                } else if (rh.method().equals("GET") && rh.requestURI().equals("/itens-cardapio/total")) {
                    getCardapioSize(clientOut);
                } else if (rh.method().equals("POST") && rh.requestURI().equals("/itens-cardapio")) {
                    String[] requestChuncks = request.split("\r\n\r\n");
                    if (requestChuncks.length == 1) {
                        clientOut.println("HTTP/1.1 400 Bad Request");
                        clientOut.println();
                        return;
                    }

                    String body = requestChuncks[1];
                    addItemCardapio(body);
                    clientOut.println("HTTP/1.1 201 Created");
                    clientOut.println();
                } else {
                    clientOut.println("HTTP/1.1 404 Not Found");
                    clientOut.println();
                    logger.warning(() -> "URI não encontrada: " + rh.requestURI());
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e, () -> "Erro ao tratar " + rh.method() + " " + rh.requestURI());

                clientOut.println("HTTP/1.1 500 Internal Server Error");
                clientOut.println();
                clientOut.println(e.getMessage());

                throw new RuntimeException(e);
            }

        } catch (Exception e) {
//            logger.severe("Erro no servidor.");
            logger.log(Level.SEVERE, "Erro no servidor.", e);
            throw new RuntimeException(e);
        }
    }

    private static void addItemCardapio(String body) {
        long newId = itensCardapio.stream()
                .map(ItemCardapio::getId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
        ItemCardapio itemCardapio = new Gson().fromJson(body, ItemCardapio.class);
        itensCardapio.add(new ItemCardapio(newId, itemCardapio.getNome(), itemCardapio.getDescricao(), itemCardapio.getPreco(), itemCardapio.getCategoria()));
    }

    private static RequestHeader getRequestHeader(String request) {
        String[] requestChuncks = request.split("\r\n\r\n");
        String requestLineAndHeaders = requestChuncks[0];
        String[] requestLineAndHeadersChuncks = requestLineAndHeaders.split("\r\n");
        String requestLine = requestLineAndHeadersChuncks[0];
        String[] requestLineChuncks = requestLine.split(" ");

        String method = requestLineChuncks[0];
        String requestURI = requestLineChuncks[1];
        String httpVersion = requestLineChuncks[2];

        logger.finer(() -> "Method: " + method);
        logger.finer(() -> "Request URI: " + requestURI);
        logger.finer(() -> "HTTP Version: " + httpVersion);

        return new RequestHeader(method, requestURI);
    }

    private record RequestHeader(String method, String requestURI) {
    }

    private static void getCardapioSize(PrintStream clientOut) {
        clientOut.println("HTTP/1.1 200 OK");
        clientOut.println("Content-Type: application/json; charset=UTF-8");
        clientOut.println();
        clientOut.println(itensCardapio.size());
    }

    private static void getItensCardapio(PrintStream clientOut) {
        String json = new Gson().toJson(itensCardapio);

        clientOut.println("HTTP/1.1 200 OK");
        clientOut.println("Content-Type: application/json; charset=UTF-8");
        clientOut.println();
        clientOut.println(json);
    }
}
