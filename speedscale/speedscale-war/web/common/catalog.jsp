<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Catalogo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="../jsp/header.jsp"/>
<p><img  style="width: 100%" src="images/banner.png" alt="Banner"></p>

<%-- Codice Java nel corpo JSP --%>
<%
    // Ottenere il parametro "parametro" dalla richiesta
    int value;
    try {
        value =  Integer.parseInt(request.getParameter("maxPrice"));
    }catch (Exception e){ value = 50;}


%>

<h1>PRODOTTI</h1>
<form action="${pageContext.request.contextPath}/common/CatalogServlet" method="GET">
    <div id="price-range">
        <input type="range" min="0" max="100" value= <%= value%> class="slider" id="priceSlider" name="maxPrice">
        <div id="price-output">$0 - $<%=value%></div>
    </div>
    <button id="catologButton" type="submit">Filtra</button>
</form>
<script src="scripts/slider.js"> </script>



<div class="viewer">
    <c:forEach var="product" items="${products}">
        <div class="card">
            <p> <img src="${product.urls[0]}"></p>
            <p>${product.nome}</p>
            <a href="${pageContext.request.contextPath}/common/ProductDetailsServlet?id=${product.id}"> <button>Dettaglio</button></a>
        </div>
    </c:forEach>
</div>

<jsp:include page="../jsp/footer.jsp"/>
</body>
</html>