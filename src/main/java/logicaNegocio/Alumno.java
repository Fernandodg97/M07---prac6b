package logicaNegocio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Alumno {
	// Atributos
	private int id;
	private String nombre;
	private String curso;

	// Constructor
	public Alumno(int id, String nombre, String curso) {
		this.id = id;
		this.nombre = nombre;
		this.curso = curso;
	}

	// Métodos getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	// Método para cargar todos los alumnos
	public static List<Alumno> load(String sentencia) throws ClassNotFoundException {
		List<Alumno> alumnosList = new ArrayList<>();

		// Establece la conexión y hace la consulta
		try (Connection conn = Conexion.getConexion();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(sentencia)) {

			// Procesa los resultados
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				String curso = resultSet.getString("curso");
				Alumno alumno = new Alumno(id, nombre, curso);
				alumnosList.add(alumno);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return alumnosList;
	}

	// Método para añadir un nuevo alumno
	public static int saved(int id, String nombre, String curso) throws ClassNotFoundException {
		String sentencia = "INSERT INTO alumnos (id, nombre, curso) VALUES (" + id + ", '" + nombre + "', '" + curso
				+ "')";

		// Establece la conexión y lanza el INSERT
		try (Connection conn = Conexion.getConexion(); Statement stmt = conn.createStatement()) {
			return stmt.executeUpdate(sentencia);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
