package logicaNegocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	//private static String dbURL = "jdbc:mysql://10.162.201.153/dbalumnos";
	private static String dbURL = "jdbc:mysql://localhost:3306/dbalumnos";
	private static String user = "usuario";
	private static String pass = "usuario";
	private static Connection connection;

	// Método para configurar dinámicamente la URL de la base de datos
	public static void setURL(String url) {
		dbURL = url;
	}

	// Método para obtener una conexión a la base de datos
	public static Connection getConexion() {
		try {
			if (dbURL == null || dbURL.isEmpty()) {
				throw new IllegalStateException(
						"La URL de la base de datos no está configurada. Usa setURL() primero.");
			}
			// Si no existe una conexión activa o está cerrada, se crea una nueva
			if (connection == null || connection.isClosed()) {
				// Cargar el driver de MySQL
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Establecer la conexión
				connection = DriverManager.getConnection(dbURL, user, pass);
			}
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			// Envolver la excepción en una RuntimeException
			throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
		}
	}

}
