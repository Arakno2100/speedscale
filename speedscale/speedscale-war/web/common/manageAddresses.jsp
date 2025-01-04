<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Indirizzi</title>
</head>

<body>

<jsp:include page="../jsp/header.jsp"/>

<h1>Indirizzi</h1>

<!--Collegamento per aggiungere un nuovo indirizzo -->

<button  class="btn" style="width: 30%; font-size: 80%" onclick="location.href='${pageContext.request.contextPath}/common/addAddress.jsp'">Nuovo Indirizzo</button>

<div class="viewer">
    <c:forEach var="address" items="${addresses}">
        <div class="card">
            <p>Via: ${address.via}</p>
            <p>Città: ${address.citta}</p>
            <p>Provincia: ${address.provincia}</p>
            <p>Cap: ${address.cap}</p>
            <p>Nazione: ${address.nazione}</p>

            <div>
                <form action="${pageContext.request.contextPath}/common/updateAddress.jsp" method="post">
                    <input type="hidden" name="id" value="${address.id}" />
                    <input type="hidden" name="via" value="${address.via}" />
                    <input type="hidden" name="citta" value="${address.citta}" />
                    <input type="hidden" name="provincia" value="${address.provincia}" />
                    <input type="hidden" name="cap" value="${address.cap}" />
                    <input type="hidden" name="nazione" value="${address.nazione}" />
                    <button type="submit">Modifica</button>
                </form>
            </div>

            <div>
                <form action="${pageContext.request.contextPath}/common/DeleteAddressServlet" method="post" onsubmit="return confirm('Sei sicuro di voler cancellare questo prodotto?');">
                    <input type="hidden" name="id" value="${address.id}" />
                    <button type="submit">Cancella</button>
                </form>
            </div>

        </div>
    </c:forEach>
</div>

<jsp:include page="../jsp/footer.jsp"/>

</body>
</html>