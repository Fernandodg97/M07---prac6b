package webses;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class WebSessionListener implements HttpSessionListener {
	
    public void sessionCreated(HttpSessionEvent arg0) {
        // Este método se ejecuta cuando se crea una nueva sesión HTTP

        System.out.println("Session creada"); 
        // Imprime en la consola un mensaje indicando que se ha creado una sesión

        ServletContext contexto = arg0.getSession().getServletContext(); 
        // Obtiene el contexto del servlet a partir del evento de sesión

        synchronized (contexto) { 
            // Sincroniza el acceso al contexto para evitar problemas de concurrencia

            Integer usuariosConectados = (Integer) contexto.getAttribute("usuariosConectados");
            // Obtiene el número de usuarios conectados almacenado en el contexto
            if (usuariosConectados == null) {
                usuariosConectados = 0;
            }
            // Si no hay usuarios conectados, se inicializa el contador a 0

            usuariosConectados += 1; 
            // Incrementa en uno el contador de usuarios conectados

            contexto.setAttribute("usuariosConectados", usuariosConectados);
            // Actualiza el atributo "usuariosConectados" en el contexto con el nuevo valor
        } // Fin del bloque synchronized
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
        // Este método se ejecuta cuando una sesión HTTP es destruida

        System.out.println("Session destruida"); 
        // Imprime en la consola un mensaje indicando que una sesión ha sido eliminada

        ServletContext contexto = arg0.getSession().getServletContext();
        // Obtiene el contexto del servlet desde el evento de sesión

        synchronized (contexto) {
            // Bloque sincronizado para evitar problemas de concurrencia en el acceso al contexto

            Integer usuariosConectados = (Integer) contexto.getAttribute("usuariosConectados");
            // Obtiene el número actual de usuarios conectados almacenado en el contexto

            if (usuariosConectados == null) {
                usuariosConectados = 0;
            }
            // Si el atributo no existe, se inicializa a 0 (aunque esto no debería ocurrir normalmente)

            usuariosConectados -= 1;
            // Disminuye en 1 el contador de usuarios conectados

            contexto.setAttribute("usuariosConectados", usuariosConectados);
            // Actualiza el atributo "usuariosConectados" en el contexto con el nuevo valor
        } // Fin del bloque synchronized
    }


}
