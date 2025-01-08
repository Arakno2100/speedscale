<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registrazione</title>
        <script src="../scripts/validate.js"></script>
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        
        <div class="container">
            <h2>Registrazione</h2>
            <img style="width: 30%; margin: 0 15%;" src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
            <form id="regForm" action="${pageContext.request.contextPath}/common/RegistrationServlet" method="post">
                <div class="form-group">
                    <div class="form-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" required  onblur="validateNome('regForm')">
                    </div>
                    <span id="errorName"></span>
                    <div class="form-group">
                        <label for="cognome">Cognome</label>
                        <input type="text" id="cognome" name="cognome" required onblur="validateCognome('regForm')">
                    </div>
                    <span id="errorLastname"></span>            
                    <div class="form-group">
                        <label for="dataNascita">Data di Nascita</label>
                        <input type="date" id="dataNascita" name="dataNascita" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" required onblur="validateMail('regForm')">
                    </div>
                    <span id="errorEmail"></span>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required onfocus="validate()">
                </div>
                <div class="form-group">
                    <button id="buttonReg" type="submit" onmouseover="validateReg('regForm' ,'buttonReg' )">Continua</button>
                </div>
                <a  href="index.jsp">Home</a>
                <a  style="margin-right: 0" href="common/login.jsp">Login</a>
            </form>

            <div id="errorMessages">
                <%
                    List<String> errors = (List<String>) request.getAttribute("errors");
                    if (errors != null && !errors.isEmpty()) {
                        for (String error : errors) {
                %>
                <p class="error-message"><%= error %></p>
                <%
                        }
                    }
                %>
            </div>
        </div>
            
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
