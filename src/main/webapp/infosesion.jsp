<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
    <title>Información de Sesión</title>
    <%
	ServletContext contexto = getServletContext();
	Integer usuariosConectados = null;
	Integer usuariosValidados = null;

	synchronized (contexto) {
		usuariosConectados = (Integer) contexto
				.getAttribute("usuariosConectados");

		usuariosValidados = (Integer) contexto
				.getAttribute("usuariosValidados");
	}
%>
</head>
<body>
    <h2>Información de la Sesión</h2>
    <table border="1">
     	<tr>
            <th>Atributo</th>
            <th>Valor</th>
        </tr>
        <tr>
            <td>Identificador</td>
            <td><%= session.getId() %></td>
        </tr>
        <tr>
            <td>Fecha/hora creación</td>
            <td><%= new Date(session.getCreationTime()) %></td>
        </tr>
        <tr>
            <td>Hora último acceso</td>
            <td><%= new Date(session.getLastAccessedTime()) %></td>
        </tr>
        <tr>
            <td>Número previo de accesos</td>
            <td><%= session.getAttribute("contadorAccesos") %></td>
        </tr>
        <tr>
            <td>Usuario</td>
            <td><%= session.getAttribute("nombre") %></td>
        </tr>
        <tr>
            <td>Número de usuarios conectados</td>
            <td><%= usuariosConectados%></td>
        </tr>
        <tr>
            <td>Número de usuarios validados</td>
            <td><%= usuariosValidados %></td>
        </tr>
    </table>
</body>
</html>
