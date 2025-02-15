<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <title>CheckOut - Selezione indirizzo</title>
    </head>
    
    <body>
        <jsp:include page="../jsp/header.jsp"/>
        
        <div class="container">
            <div class="addressSelection">
                <c:if test="${not empty addresses}">
                    <h1>Seleziona un indirizzo di spedizione</h1>
                    <form action="${pageContext.request.contextPath}/common/RetrieveAccountMetodiPagamento" method="post">
                        <c:forEach var="address" items="${addresses}">
                            <div class="addressItem">
                                <input type="radio" name="addressId" value="${address.id}" required>
                                <label for="addressId">${address.via}, ${address.citta}, ${address.provincia}, ${address.cap}, ${address.nazione}</label>
                            </div>
                        </c:forEach>
                        <h3>Totale: ${total}&euro;</h3>
                        <input type="hidden" name="total" value="${total}">
                        <input type="hidden" name="fromCart" value="true">
                        <button type="submit" class="btn" style="width: 40%">Continua</button>
                    </form>
                </c:if>

                <c:if test="${empty addresses}">
                    <h1>Nessun indirizzo di spedizione trovato</h1>
                    <form action="${pageContext.request.contextPath}/common/addAddress.jsp" method="post">
                        <h3>Totale: ${total}&euro;</h3>
                        <input type="hidden" name="total" value="${total}">
                        <input type="hidden" name="fromCart" value="true">
                        <button type="submit" class="btn" style="width: 40%">Registra nuovo indirizzo</button>
                    </form>
                </c:if>
            </div>
        </div>

        <jsp:include page="../jsp/footer.jsp"/>
    </body>
</html>
