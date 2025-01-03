<%@ page import="model.bean.Utente" %>
<%@ page session="true" %>
<%
    Utente utente = (Utente) session.getAttribute("utente");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Modifica Dati Personali</title>
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<script src="scripts/validate.js"></script>
<h1>Modifica Dati Personali</h1>
<form id="updateProduct" action="${pageContext.request.contextPath}/common/UpdateAccountServlet" method="post">
    <label for="nome">Nome</label>
    <input type="text" id="nome" name="nome" value="<%= utente.getNome() %>" oninput="validateNome('updateProduct')">
    <br><br>
    <span id="errorName"></span>
    <br><br>
    <label for="cognome">Cognome</label>
    <input type="text" id="cognome" name="cognome" value="<%= utente.getCognome() %>" oninput="validateCognome('updateProduct')">
    <br><br>
    <span id="errorLastname"></span>
    <br><br>
    <label for="dataNascita">Data di nascita</label>
    <%
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(utente.getDataNascita());
    %>
    <input type="date" id="dataNascita" name="dataNascita" value="<%= formattedDate %>">
    <br><br>
    <br><br>
    <label for="email">Telefono</label>
    <input type="text" id="phone" name="phone" value="<%= utente.getTelefono()%>" oninput="validatePhone('updateProduct')">
    <br><br>
    <br><br>
    <input  id="updateAccountButton" type="submit" value="Aggiorna" onmouseover="validateAllName('updateProduct' , 'updateAccountButton')">
</form>

<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>
