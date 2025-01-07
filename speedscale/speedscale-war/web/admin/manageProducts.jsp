<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <link rel="stylesheet" href="../css/style.css">
        <title>Gestione Prodotti</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <h1>Gestione dei Prodotti</h1>

        <button class="btn" style="width: 30%; font-size: 80%" onclick="location.href='${pageContext.request.contextPath}/admin/addProduct.jsp'">Nuovo Prodotto</button>


        <div style="overflow-x: auto">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                    <th>Descrizione</th>
                    <th>Scala</th>
                    <th>Marca</th>
                    <th>Quantità</th>
                    <th>Immagine</th>
                    <th colspan="3">Azione</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="prodotto" items="${products}">
                    <tr>
                        <td>${prodotto.id}</td>
                        <td>${prodotto.nome}</td>
                        <td><fmt:formatNumber value="${prodotto.prezzo}" type="number" maxFractionDigits="2" minFractionDigits="2" />€</td>
                        <td>${prodotto.descrizione}</td>
                        <td>${fn:replace(prodotto.scala, '_', ':').substring(4)}</td>
                        <td>${prodotto.marca}</td>
                        <td>${prodotto.quantitàDisponibile}</td>
                        <td><img src="${prodotto.urls[0]}" width="50" height="50"></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/updateProduct.jsp" method="post">
                                <input type="hidden" name="id" value="${prodotto.id}" />
                                <input type="hidden" name="nome" value="${prodotto.nome}" />
                                <input type="hidden" name="descrizione" value="${prodotto.descrizione}" />
                                <input type="hidden" name="scala" value="${prodotto.scala}" />
                                <input type="hidden" name="marca" value="${prodotto.marca}" />
                                <input type="hidden" name="prezzo" value="${prodotto.prezzo}" />
                                <input type="hidden" name="quantita" value="${prodotto.quantitàDisponibile}" />
                                <input type="hidden" name="urls" value="${prodotto.urls}" />
                                <button class="btn" type="submit">Modifica</button>
                            </form>
                        </td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/DeleteProductServlet" method="post" onsubmit="return confirm('Sei sicuro di voler cancellare questo prodotto?');">
                                <input type="hidden" name="id" value="${prodotto.id}" />
                                <button class="btn" type="submit">Cancella</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <br>
        
        <a href="${pageContext.request.contextPath}/admin/homepage.jsp" class="btn">Home</a>
        
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>

