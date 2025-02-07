package logicaNegocio;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class Usuario implements HttpSessionBindingListener {

	private String nombre = "";

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	@SuppressWarnings("removal")
	public void valueBound(HttpSessionBindingEvent arg0) {

		System.out.println("User añadido a la session. ID: " +
				arg0.getSession().getId());

		ServletContext contexto = arg0.getSession().getServletContext();

		synchronized (contexto) {

			Integer usuariosValidados = (Integer) contexto
					.getAttribute("usuariosValidados");
			if (usuariosValidados == null) {
				usuariosValidados = new Integer(0);
			}
			usuariosValidados += 1;
			contexto.setAttribute("usuariosValidados", usuariosValidados);

		} // synchronized
	}

	@SuppressWarnings("removal")
	public void valueUnbound(HttpSessionBindingEvent arg0) {

		System.out.println("User eliminado de session. ID: " +
				arg0.getSession().getId());

		ServletContext contexto = arg0.getSession().getServletContext();

		synchronized (contexto) {
			Integer usuariosValidados = (Integer) contexto
					.getAttribute("usuariosValidados");

			if (usuariosValidados == null) {
				usuariosValidados = new Integer(0);
			}
			usuariosValidados -= 1;
			contexto.setAttribute("usuariosValidados", usuariosValidados);

		} // synchronized
	}
}
