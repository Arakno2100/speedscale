<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
    <head>
        <meta charset="UTF-8">
        <base href="<%=request.getContextPath()%>/">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <title>Navbar con Logo</title>
        <script src="scripts/ajaxScript.js">
        </script>
    </head>
    
    <body>

        <div class="topnav">
            <a href="${pageContext.request.contextPath}/common/IndexServlet">
                <img src="images/logo.png" alt="Logo">
            </a>

            <a href="${pageContext.request.contextPath}/common/IndexServlet">SpeedScale</a>

            <div class="search-container">
                <form action="${pageContext.request.contextPath}/common/CatalogServlet" method="GET">
                    <label>
                        <input id="search" type="text" placeholder="Cerca.." name="searchQuery" onsubmit="this.form.submit()">
                    </label>
                </form>
                <span class="fa fa-search" style="color: white; margin-right: 10px" id="num"></span>
            </div>

            <div class="links">
                <c:choose>
                    <c:when test="${isAdmin == 1 || isResponsabileMagazzino == 1 || isGestoreOrdini == 1}">
                        <a href="${pageContext.request.contextPath}/admin/homepage.jsp">Profilo</a>
                    </c:when>
                    <c:when test="${isAdmin == 0}"> <!-- Accesso effettuato come user -->
                        <a href="${pageContext.request.contextPath}/common/homepage.jsp">Profilo</a>
                        <a href="${pageContext.request.contextPath}/common/RetrieveAccountCartServlet">Carrello</a>
                    </c:when>
                    <c:otherwise> <!-- Non ancora effettuato l'accesso -->
                        <a href="${pageContext.request.contextPath}/common/login.jsp">Accedi</a>
                        <a href="${pageContext.request.contextPath}/common/RetrieveAccountCartServlet">Carrello</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </body>
</html>