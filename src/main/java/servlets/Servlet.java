package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logicaNegocio.Alumno;
import logicaNegocio.Usuario;
import util.SessionManager;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PG_RESPUESTA_DESCONEXION = "desconectado.jsp";
    private static final String PG_RESPUESTA_INICIO = "validLogin.jsp";
    private static final String PG_RESPUESTA_VALIDARSE = "acceso.jsp";
    private static final String PG_RESPUESTA_LOGIN = "Login";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Creación de la sesión. Si ya existe, la utiliza
        HttpSession sesion = request.getSession(true);

        // Incrementa el contador de accesos por sesión
        SessionManager.incrementarContadorSesion(sesion);

        // Obtiene el tipo de operación solicitada
        String operacion = request.getParameter("operacion");

        // Valida si la operación es "info"
        if ("info".equals(operacion)) {
            request.getRequestDispatcher("infosesion.jsp").forward(request, response);
            return;
        }

        // Valida si la operación es "desconectar"
        if ("desconectar".equals(operacion)) {
            sesion.invalidate();
            response.sendRedirect(PG_RESPUESTA_DESCONEXION);
            return;
        }
        
        // El usuario quiere hacer una Consulta
        else if ("consulta".equals(operacion)) {
            String sentenciaSQL = request.getParameter("sentencia");
            boolean usarJSTL = "true".equals(request.getParameter("jstl"));

            // Aquí iría la lógica para ejecutar la consulta en la base de datos
            List<Alumno> alumnos = null;
			try {
				alumnos = Alumno.load(sentenciaSQL);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (usarJSTL) {
		        // Si usa JSTL, enviar los datos a la JSP
		        request.setAttribute("listaAlumnos", alumnos);
		        request.getRequestDispatcher("consulta.jsp").forward(request, response);
		    } else {
		        // Si NO usa JSTL, generar la respuesta HTML directamente desde el Servlet
		        response.setContentType("text/html; charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        
		        out.println("<html>");
		        out.println("<head><title>Resultados de la Consulta</title></head>");
		        out.println("<body>");
		        out.println("<h2>Resultados de la Consulta</h2>");
		        out.println("<table border='1'>");
		        out.println("<tr><th>ID</th><th>Nombre</th><th>Curso</th></tr>");

		        if (alumnos != null) {
		            for (Alumno alumno : alumnos) {
		                out.println("<tr>");
		                out.println("<td>" + alumno.getId() + "</td>");
		                out.println("<td>" + alumno.getNombre() + "</td>");
		                out.println("<td>" + alumno.getCurso() + "</td>");
		                out.println("</tr>");
		            }
		        } else {
		            out.println("<tr><td colspan='3'>No se encontraron resultados.</td></tr>");
		        }

		        out.println("</table>");
		        out.println("</body>");
		        out.println("</html>");
		    }
        }

        // El usuario quiere dar de alta un alumno
        else if ("alta".equals(operacion)) {
            // Guardamos los valores que permiten dar de alta al alumno
            String idParam = request.getParameter("txtID");
            String curso = request.getParameter("txtCurso");
            String nombre = request.getParameter("txtNombre");
            
            int id = -1; // Inicializamos con un valor inválido para detectar errores

            // Si el ID se recibe desde el formulario, lo convertimos a int
            if (idParam != null && !idParam.isEmpty()) {
                id = Integer.parseInt(idParam);
            } else {
                // Si no se recibe desde el formulario, lo intentamos obtener de la sesión
                Object sesionIdObj = sesion.getAttribute("sesAlumno");

                if (sesionIdObj != null) {
                    if (sesionIdObj instanceof Integer) {
                        id = (Integer) sesionIdObj; // Si ya es Integer, lo usamos directamente
                    } else {
                        id = Integer.parseInt(sesionIdObj.toString()); // Si es String, lo convertimos
                    }
                }
            }
            
            if (curso == null || curso.isEmpty()) {
                curso = (String) sesion.getAttribute("sesCurso"); // Tomar el curso de la sesión
            }
            if (nombre == null || nombre.isEmpty()) {
                nombre = (String) sesion.getAttribute("sesNombre"); // Tomar el nombre de la sesión
            }

         // Verificamos si el usuario ya está validado en la sesión
            Usuario usuario = (Usuario) sesion.getAttribute("usuario");

            if (usuario != null) { // El usuario ya está validado
                try {
                    Alumno.saved(id, nombre, curso);
                    response.sendRedirect(PG_RESPUESTA_INICIO);
                } catch (ClassNotFoundException e) {
                    response.getWriter().println("<script>alert('No se puede registrar el alumno'); window.location.href='index.jsp';</script>");
                }
            } else {
                // Si el usuario aún no está validado, guardamos los datos en la sesión
                sesion.setAttribute("sesAlumno", id);
                sesion.setAttribute("sesCurso", curso);
                sesion.setAttribute("sesNombre", nombre);

                // Redirigimos al usuario al login para validarse
                response.sendRedirect(PG_RESPUESTA_VALIDARSE); // Se enviará al servlet Login
                return;
            }
            
         // Validación del usuario
            if ("validar".equals(operacion)) {
            	response.sendRedirect(PG_RESPUESTA_LOGIN);
            }
      
        }
    }
}
