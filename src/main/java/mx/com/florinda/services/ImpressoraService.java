package mx.com.florinda.services;

public class ImpressoraService {

    public static void imprimirTitulo(String titulo) {
        IO.println("=".repeat(50));
        IO.println((titulo == null || titulo.isEmpty()) ? "" : titulo);
        IO.println("=".repeat(50));
    }

    static void imprimirCorpo(String texto) {
        IO.println();
        IO.println(texto);
        IO.println("-".repeat(50));
    }

    static String imprimirMenu(String titulo, StringBuilder sb) {
        ImpressoraService.imprimirTitulo(titulo);
        return sb.toString();
    }

    public static void limparTela() {
//        try {
//            // executa o comando específico do sistema operacional
//            if (System.getProperty("os.name").contains("Windows")) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } else {
//                new ProcessBuilder("clear").inheritIO().start().waitFor();
//            }
//        } catch (Exception e) {
        // utiliza código ANSI para limpar a tela
        IO.println("\033[H\033[2J");
        System.out.flush();
//        }
    }

    static void pressEnter() {
        IO.print("Pressione ENTER para continuar...");
        IO.readln();
    }

}
