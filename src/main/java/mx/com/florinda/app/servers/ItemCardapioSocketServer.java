package mx.com.florinda.app.servers;

import com.google.gson.Gson;
import mx.com.florinda.models.ItemCardapio;
import mx.com.florinda.repositories.SQLDatabase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemCardapioSocketServer {

    private static final Logger logger = Logger.getLogger(ItemCardapioSocketServer.class.getName());
    private static final SQLDatabase cardapio = new SQLDatabase(); //Cardapio cardapio = new Cardapio("/databases/itens-cardapio.json");
    private static final CopyOnWriteArrayList<ItemCardapio> itensCardapio = cardapio.listaItensCardapio();//cardapio.getItens();

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

    private record RequestHeader(String method, String requestURI, String mediaType) {
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

                if (rh.method().equals("GET") && (rh.requestURI().equals("/") || rh.requestURI().equals("/en"))) {

                    Locale locale = (rh.requestURI().equals("/en")) ? Locale.US : Locale.of("pt", "BR");

                    clientOut.print("HTTP/1.1 200 OK\r\n");
                    clientOut.print("Content-Type: text/html; charset=UTF-8\r\n\r\n");
                    clientOut.print("\r\n");
                    clientOut.print(createHtml(locale));
                    clientOut.print("\r\n");

                } else if (rh.method().equals("GET") && rh.requestURI().equals("/itens-cardapio")) {
                    getItensCardapio(clientOS, rh.mediaType);
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

    private static String createHtml(Locale locale) {
        NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(locale);
        ResourceBundle mensagens = ResourceBundle.getBundle("mensagens", locale);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(locale);
        DateTimeFormatter anoMesFormatter = DateTimeFormatter.ofPattern("MMMM/yyyy").withLocale(locale);

        // Percorre os itens e monta o "article" para a exibição.
        StringBuilder itens = new StringBuilder();
        itensCardapio.forEach(item -> {
            String precoItem;
            if (item.isEmPromocao()) {
                precoItem = String.format("<mark>Em promoção</mark> <strong>%s</strong> <s>%s</s>", formatadorMoeda.format(item.getPrecoComDesconto()), formatadorMoeda.format(item.getPreco()));
            } else {
                precoItem = String.format("<strong>%s</strong>", formatadorMoeda.format(item.getPreco()));
            }

            String categoria = mensagens.getString("categoria.cardapio." + item.getCategoria().name().toLowerCase());

            itens.append("""
                    <article>
                        <kbd>%s</kbd>
                        <h3>%s</h3>
                        <p>%s</p>
                        %s
                    </article>
                    """.formatted(categoria, item.getNome(), item.getDescricao(), precoItem));
        });

        // Monta o corpo padrão do HTML
        String html = """
                <!DOCTYPE html>
                <html lang="ptBR">
                <head>
                    <meta charset="UTF-8">
                    <title>Florinda Eats - Cardápio</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.1.1/css/pico.min.css">
                </head>
                <body>
                
                <header class="container">
                    <hgroup>
                        <h1>Florinda Eats</h1>
                        <p>O sabor da Vila direto pra você</p>
                    </hgroup>
                </header>
                
                <main class="container">
                    <h2>Cardápio</h2>
                
                %s
                
                </main>
                
                <footer class="container">
                    <p><small><em>Preços de acordo com %s</em></small></p>
                    <p><strong>Florinda Eats</strong> Todos os direitos reservados - setembro/2025</p>
                </footer>
                </body>
                </html>
                """;
        return html.formatted(itens.toString(), dateTimeFormatter.format(ZonedDateTime.now()), anoMesFormatter.format(YearMonth.now()));
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
        String mediaType = "application/json";

        for (int i = 1; i < requestLineAndHeadersChuncks.length; i++) {
            String header = requestLineAndHeadersChuncks[i];
            logger.finer(() -> "Header: " + header);
            if (header.contains("Accept")) {
                mediaType = header.replace("Accept: ", "");
            }
        }

        logger.finer(() -> "Method: " + method);
        String finalMediaType = mediaType;
        logger.finer(() -> "Accept: " + finalMediaType);
        logger.finer(() -> "Request URI: " + requestURI);
        logger.finer(() -> "HTTP Version: " + httpVersion);

        return new RequestHeader(method, requestURI, mediaType);
    }

    private static void getCardapioSize(PrintStream clientOut) {
        clientOut.println("HTTP/1.1 200 OK");
        clientOut.println("Content-Type: application/json; charset=UTF-8");
        clientOut.println();
        clientOut.println(itensCardapio.size());
    }

    private static void getItensCardapio(OutputStream clientOS, String mediaType) throws IOException {
        byte[] body;

        if ("application/x-java-serialized-object".equals(mediaType)) {
            var bos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(bos);
            oos.writeObject(itensCardapio);
            body = bos.toByteArray();
        } else {
            String json = new Gson().toJson(itensCardapio);
            body = json.getBytes(StandardCharsets.UTF_8);
        }

        clientOS.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        clientOS.write(("Content-Type: "+ mediaType +"; charset=UTF-8\r\n\r\n").getBytes(StandardCharsets.UTF_8));
        clientOS.write(body);
    }

}
