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

<h1>Metodi Pagamento</h1>

<!--Collegamento per aggiungere un nuovo indirizzo -->

<button  class="btn" style="width: 30%; font-size: 80%" onclick="location.href='${pageContext.request.contextPath}/common/addMetodoPagamento.jsp'">Nuovo Metodo Pagamento</button>

<div class="viewer">
    <c:forEach var="metodo" items="${metodiPagamento}">
        <div class="card">
            <p>Intestatario: ${metodo.getIntestatario()}</p>
            <p>Numero ${metodo.getNumero()}</p>
            <p>Mese Scadenza: ${metodo.getMeseScadenza()}</p>
            <p>Anno Scadenza: ${metodo.getAnnoScadenza()}</p>

            <div>
                <form action="${pageContext.request.contextPath}/common/updateMetodoPagamento.jsp" method="post">
                    <input type="hidden" name="id" value="${metodo.getId()}" />
                    <input type="hidden" name="intestatario" value="${metodo.getIntestatario()}" />
                    <input type="hidden" name="numero" value="${metodo.getNumero()}" />
                    <input type="hidden" name="mese" value="${metodo.getMeseScadenza()}" />
                    <input type="hidden" name="anno" value="${metodo.getAnnoScadenza()}" />
                    <button type="submit">Modifica</button>
                </form>
            </div>

            <div>
                <form action="${pageContext.request.contextPath}/common/DeleteMetodoPagamento" method="post" onsubmit="return confirm('Sei sicuro di voler cancellare questo metodo di pagamento?');">
                    <input type="hidden" name="id" value="${metodo.getId()}" />
                    <button type="submit">Cancella</button>
                </form>
            </div>

        </div>
    </c:forEach>
</div>

<jsp:include page="../jsp/footer.jsp"/>

</body>
</html>