<!-- Se crea jsp como JSTL para la consulta -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Alumnos</title>
</head>
<body>
    <h2>Resultados de la consulta JSTL:</h2>

    <!-- Mostrar errores si existen -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <!-- Mostrar resultados si existen -->
    <c:if test="${not empty listaAlumnos}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Curso</th>
                </tr>
            </thead>
            <tbody>
                <!-- Recorrer la lista de alumnos -->
                <c:forEach var="alumno" items="${listaAlumnos}">
                    <tr>
                        <td>${alumno.id}</td>
                        <td>${alumno.nombre}</td>
                        <td>${alumno.curso}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
