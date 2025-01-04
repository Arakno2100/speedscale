<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>${product.nome}</title>
</head>

<body>

<jsp:include page="../jsp/header.jsp"/>

<div class="productView" >
    <img style="width:45%; display: inline; margin:0 2.5% " src="${product.urls[0]}">

    <div style="width: 45%; display: inline;">
        <c:if test="${not empty product}">
            <h1>${product.nome}</h1>
            <h3>${product.prezzo}&euro;</h3>
            <p>Descrizione: ${product.descrizione}</p>
            <p>Scala: ${product.scala}</p>
            <p>Marca: ${product.marca}</p>

            <c:choose>
                <c:when test="${product.quantitàDisponibile <= 0}">
                    <p style="color: red;">Esaurito</p>
                </c:when>

                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/common/AddToCartServlet" method="post">
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="hidden" name="quantity" value="1">
                        <button class="btn" type="submit">Aggiungi al carrello</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
</div>

<jsp:include page="../jsp/footer.jsp"/>

</body>
</html>