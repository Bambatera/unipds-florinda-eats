package mx.com.florinda.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ItemCardapioSocketClient {

    static void main() throws Exception {

        try (Socket socket = new Socket("localhost", 8000)) {
            OutputStream clientOS = socket.getOutputStream();
            PrintStream clientOut = new PrintStream(clientOS);
            clientOut.println("GET /itens-cardapio HTTP/1.1");
            clientOut.println();

            InputStream clientIS = socket.getInputStream();
            Scanner scanner = new Scanner(clientIS);
            while (scanner.hasNextLine()) {
                IO.println(scanner.nextLine());
            }
        }

    }
}
