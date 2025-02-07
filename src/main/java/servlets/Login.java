package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logicaNegocio.Usuario;

public class Login extends HttpServlet {

	private static final long serialVersionUID = -7432700501449176526L;
	private static final String PG_RESPUESTA_ERROR = "/errorLogin.jsp";
	private static final String PG_RESPUESTA_ALTA = "/Servlet?operacion=alta";
	private String siguientePag = "";

	@Override
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {

		doPost(arg0, arg1);
	}

	@Override
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {

		String usuarioIntro = arg0.getParameter("txtUsuario");
		String passwIntro = arg0.getParameter("txtContrasenya");

		// Intentamos obtener el nombre del usuario si es que está
		// ya validado
		String sessionUser = (String) arg0.getSession().getAttribute("nombre");

		// Variable que tomará como valor el nombre del usuario
		// confirmado contra la "Base de datos"
		String user = null;

		// Si el usuario no está validado hay que validarlo
		if (sessionUser == null)
			user = validarUsuario(usuarioIntro, passwIntro);

		// Si ni está validado y tampoco se reconoce su usuario-password
		if ((sessionUser == null) && (user == null)) {
			siguientePag = PG_RESPUESTA_ERROR;
		} else {
			// Si no está validado y el usuario introducido es correcto
			if ((sessionUser == null) && (user != null)) {
				Usuario usuari = new Usuario(user);
				arg0.getSession().setAttribute("usuario", usuari);
				arg0.getSession().setAttribute("nombre", usuari.getNombre());
			}
			siguientePag = PG_RESPUESTA_ALTA;
		}

		getServletContext().getRequestDispatcher(siguientePag)
				.forward(arg0, arg1);
	}

	// Nos devuelve el nombre del usuario si la pareja
	// nombre-passw es correcta
	// Tendriamos que comparar con valores de la BD!
	private String validarUsuario(String usuarioIntro, String passwIntro) {
		String retorno = null;

		if (usuarioIntro.equals("fer") &&
				passwIntro.equals("fer")) {
			retorno = usuarioIntro;
		}
		return retorno;
	}

}
