package mx.com.florinda.app;

import mx.com.florinda.models.Pix;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.Instant;

public class SerializadorPix {

    static void main() throws Exception {
//        Pix pix = new Pix(1L, new BigDecimal("100.00"), "lmsilv@gmail.com");
        Pix pix2 = new Pix(2L, new BigDecimal("1000.00"), "lmsilv@hotmail.com", Instant.now(), "dinlelo!!!");

        try (var fos = new FileOutputStream("pix.ser");
        var oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(pix);
            oos.writeObject(pix2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
