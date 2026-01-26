<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<style>
body { font-family: Arial; text-align: center; margin-top: 100px; }
form { width: 300px; margin: 0 auto; }
input { display: block; width: 100%; margin: 10px 0; padding: 8px; }
button { padding: 10px 20px; background: #4CAF50; color: white; border: none; cursor: pointer; }
.error { color: red; }
.message { color: green; }
</style>
</head>
<body>
<h1>Connexion</h1>
<% if (request.getAttribute("error") != null) { %>
<p class="error"><%= request.getAttribute("error") %></p>
<% } %>
<% if (request.getAttribute("message") != null) { %>
<p class="message"><%= request.getAttribute("message") %></p>
<% } %>
<form method="POST" action="<%= request.getContextPath() %>/login">
<input type="text" name="username" placeholder="Nom d'utilisateur" required value="admin">
<input type="password" name="password" placeholder="Mot de passe" required value="1234">
<button type="submit">Se connecter</button>
</form>

<hr>
<h3>Test manuel des paramètres</h3>
<p>Essayez cette URL directement :</p>
<a href="<%= request.getContextPath() %>/login?username=admin&password=1234">
    /login?username=admin&password=1234 (GET - pour test)
</a>
</body>
</html>