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

        <button  class="btn" style="width: 30%; font-size: 80%" onclick="location.href='${pageContext.request.contextPath}/common/addMetodoPagamento.jsp'">Nuovo Metodo Pagamento</button>

        <div class="viewer">
            <c:forEach var="metodo" items="${metodiPagamento}">
                <div class="card">
                    <p>Intestatario: ${metodo.intestatario}</p>
                    <p>Numero: ${metodo.numero}</p>
                    <p>Mese Scadenza: ${metodo.meseScadenza}</p>
                    <p>Anno Scadenza: ${metodo.annoScadenza}</p>

                    <div>
                        <form action="${pageContext.request.contextPath}/common/updateMetodoPagamento.jsp" method="post">
                            <input type="hidden" name="id" value="${metodo.id}" />
                            <input type="hidden" name="intestatario" value="${metodo.intestatario}" />
                            <input type="hidden" name="numero" value="${metodo.numero}" />
                            <input type="hidden" name="mese" value="${metodo.meseScadenza}" />
                            <input type="hidden" name="anno" value="${metodo.annoScadenza}" />
                            <input type="hidden" name="cvv" value="${metodo.cvv}" />
                            <button type="submit">Modifica</button>
                        </form>
                    </div>

                    <div>
                        <form action="${pageContext.request.contextPath}/common/DeleteMetodoPagamento" method="post" onsubmit="return confirm('Sei sicuro di voler cancellare questo metodo di pagamento?');">
                            <input type="hidden" name="id" value="${metodo.id}" />
                            <button type="submit">Cancella</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <br>
        <a href="${pageContext.request.contextPath}/common/homepage.jsp" class="btn">Home</a>
        
        <jsp:include page="../jsp/footer.jsp"/>

    </body>
</html>