<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Date"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Validación de usuarios</title>
</head>
<body>
    <h1>Validación de usuarios</h1>
    <h2>La operación solicitada requiere validación. Por favor, introduzca sus credenciales.</h2>
    
    <form action="Login" method="POST">
    	<input type="hidden" name="operacion" value="validar">	
        <label for="txtUsuario">Usuario:</label>
        <input type="text" id="txtUsuario" name="txtUsuario" required><br><br>

        <label for="txtContrasenya">Contraseña:</label>
        <input type="password" id="txtContrasenya" name="txtContrasenya" required><br><br>

        <button type="submit">Aceptar</button>
        <button type="reset">Borrar</button>
    </form>
</body>
</html>



