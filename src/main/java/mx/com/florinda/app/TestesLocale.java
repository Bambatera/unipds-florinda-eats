package mx.com.florinda.app;

import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestesLocale {

    static void main() {
        Locale localeUS = Locale.US;
        Locale localePtBR = Locale.of("pt", "BR");

        IO.println("---------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        IO.println(formatter.format(ZonedDateTime.now()));
        IO.println(formatter.withLocale(localeUS).format(ZonedDateTime.now()));
        IO.println(formatter.withLocale(localePtBR).format(ZonedDateTime.now()));

        IO.println("---------------------------");

        formatter = DateTimeFormatter.ofPattern("MMMM/yyyy");
        IO.println(formatter.format(YearMonth.now()));
        IO.println(formatter.withLocale(localeUS).format(YearMonth.now()));
        IO.println(formatter.withLocale(localePtBR).format(YearMonth.now()));

        IO.println("---------------------------");

        IO.println(NumberFormat.getCurrencyInstance().format(2.99));
        IO.println(NumberFormat.getCurrencyInstance(localeUS).format(2.99));
        IO.println(NumberFormat.getCurrencyInstance(localePtBR).format(2.99));

        IO.println("---------------------------");

        ResourceBundle mensagens = ResourceBundle.getBundle("mensagens");
        ResourceBundle mensagensUS = ResourceBundle.getBundle("mensagens", localeUS);
        ResourceBundle mensagensPtBR = ResourceBundle.getBundle("mensagens", localePtBR);
        IO.println(mensagens.getString("categoria.cardapio.pratos_principais"));
        IO.println(mensagensUS.getString("categoria.cardapio.pratos_principais"));
        IO.println(mensagensPtBR.getString("categoria.cardapio.pratos_principais"));

        IO.println("---------------------------");
    }
}
