package util;

import jakarta.servlet.http.HttpSession;

public class SessionManager {
    public static void incrementarContadorSesion(HttpSession sesion) {
        Integer contadorAccesos = 0;
        if (!sesion.isNew()) {
            Integer contadorActual = (Integer) sesion.getAttribute("contadorAccesos");
            if (contadorActual != null) {
                contadorAccesos = contadorActual + 1;
            }
        }
        sesion.setAttribute("contadorAccesos", contadorAccesos);
    }
}
