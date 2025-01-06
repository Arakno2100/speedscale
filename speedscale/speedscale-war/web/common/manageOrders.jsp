<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="../css/style.css">
        <meta charset="UTF-8">
        <title>Lista Ordini</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    
    <body>
        <jsp:include page="../jsp/header.jsp"/>

        <h2>Lista Ordini</h2>
        <div style="overflow-x: auto">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Stato</th>
                        <th>Data</th>
                        <th>Totale</th>
                        <th>Dettagli</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${orders}" var="ordine">
                        <tr>
                            <td>${ordine.id}</td>
                            <td>${ordine.stato}</td>
                            <td><fmt:formatDate value="${ordine.data}" pattern="dd-MM-yyyy"/></td>
                            <td>${ordine.totale}€</td>
                            <td><button class="btn" onclick="mostraDettagli(${ordine.id});">Dettaglio</button></td>
                        </tr>

                        <tr id="dettagli-${ordine.id}" style="display: none;">
                            <td colspan="5">
                                <h3>Dettagli Ordine ${ordine.id}</h3>
                                <p><strong>Nome Utente:</strong> ${ordine.utente.nome} ${ordine.utente.cognome}</p>
                                <p><strong>Email Utente:</strong> ${ordine.utente.email}</p>
                                <p><strong>Indirizzo:</strong> ${ordine.indirizzo.via}, ${ordine.indirizzo.citta} (${ordine.indirizzo.provincia}, ${ordine.indirizzo.nazione})</p>
                                <p><strong>Data: </strong><fmt:formatDate value="${ordine.data}" pattern="dd-MM-yyyy" /></p>
                                <p><strong>Totale:</strong> ${ordine.getTotale()}€</p>

                                <table border="1">
                                    <thead>
                                        <tr>
                                            <th>ID Prodotto</th>
                                            <th>Nome Prodotto</th>
                                            <th>Prezzo Unitario</th>
                                            <th>Quantità</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${ordine.prodotti}" var="voceOrdine">
                                            <tr>
                                                <td>${voceOrdine.prodotto.id}</td>
                                                <td>${voceOrdine.prodotto.nome}</td>
                                                <td>${voceOrdine.prezzoUnitario}€</td>
                                                <td>${voceOrdine.quantità}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <hr>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <br>

        <a href="${pageContext.request.contextPath}/common/homepage.jsp" class="btn">Home</a>
        <script src="scripts/manageOrderScript.js"></script>

        <jsp:include page="../jsp/footer.jsp"/>
    </body>
</html>
