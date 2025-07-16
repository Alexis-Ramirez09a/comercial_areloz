package comercial_areloz.rzl.web.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        // Cambia aquí tu contraseña en texto claro
        String rawPassword = "0906";

        // Creamos el encoder de Spring Security
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        // Generamos la contraseña encriptada
        String encoded = encoder.encode(rawPassword);

        // La mostramos por consola
        System.out.println("Raw password  : " + rawPassword);
        System.out.println("Encoded password: " + encoded);
    }
}
