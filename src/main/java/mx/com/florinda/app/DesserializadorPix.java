package mx.com.florinda.app;

import mx.com.florinda.models.Pix;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DesserializadorPix {

    static void main() {
        try (var fis = new FileInputStream("pix.ser");
             var ois = new ObjectInputStream(fis)) {
            var pix = (Pix) ois.readObject();
            IO.println(pix);
//            IO.println("ID: " + pix.getId());
//            IO.println("Valor: " + pix.getValor());
//            IO.println("Chave Destino: " + pix.getChaveDestino());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
