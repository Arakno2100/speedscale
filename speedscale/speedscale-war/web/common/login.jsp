<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <div class="container">
            <h2>Login</h2>
            <img style="width: 30%; margin: 0 15%;" src="${pageContext.request.contextPath}/images/logo.png" alt="Logo">
            <form action="${pageContext.request.contextPath}/common/Login" method="post">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <button type="submit">Login</button>
                </div>
                <a href="index.jsp">Home</a>
                <a  style="margin-right: 0" href="common/registration.jsp">Nuovo cliente?</a>
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
