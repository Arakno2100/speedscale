<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="../css/style.css">
        <meta charset="UTF-8">
        <title>Lista Ordini</title>
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <h2>Lista Ordini</h2>
        
        <div style="overflow-x: auto">
            <div>
                <label for="email-filter">Email:</label>
                <input type="text" id="email-filter" onkeyup="filterTable()">

                <label for="start-date-filter">Data Inizio:</label>
                <input type="date" id="start-date-filter" onchange="filterTable()">

                <label for="end-date-filter">Data Fine:</label>
                <input type="date" id="end-date-filter" onchange="filterTable()">
            </div>

            <table id="orders-table" border="1">
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
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/UpdateOrderStatusServlet" method="POST">
                                    <input type="hidden" name="orderId" value="${ordine.id}"/>
                                    <select name="newStatus" onchange="this.form.submit()">
                                        <option value="RICEVUTO" ${ordine.stato == 'RICEVUTO' ? 'selected' : ''}>RICEVUTO</option>
                                        <option value="IN_ELABORAZIONE" ${ordine.stato == 'IN_ELABORAZIONE' ? 'selected' : ''}>IN ELABORAZIONE</option>
                                        <option value="SPEDITO" ${ordine.stato == 'SPEDITO' ? 'selected' : ''}>SPEDITO</option>
                                        <option value="CONSEGNATO" ${ordine.stato == 'CONSEGNATO' ? 'selected' : ''}>CONSEGNATO</option>
                                        <option value="ANNULLATO" ${ordine.stato == 'ANNULLATO' ? 'selected' : ''}>ANNULLATO</option>
                                    </select>
                                </form>
                            </td>
                            <td><fmt:formatDate value="${ordine.data}" pattern="dd-MM-yyyy"/></td>
                            <td>${ordine.totale}€</td>
                            <td><button class="btn" onclick="mostraDettagli(${ordine.id});">Dettaglio</button></td>
                        </tr>
                        <tr id="dettagli-${ordine.id}" style="display: none;">
                            <td colspan="1">
                                <h3>Dettagli Ordine ${ordine.id}</h3>
                                <p><strong>Nome Utente:</strong> ${ordine.utente.nome} ${ordine.utente.cognome}</p>
                                <p><strong>Email Utente:</strong> ${ordine.utente.email}</p>
                                <p><strong>Indirizzo:</strong> ${ordine.indirizzo.via}, ${ordine.indirizzo.citta} (${ordine.indirizzo.provincia}), ${ordine.indirizzo.nazione}</p>
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

            <br>
        </div>
        
        <a href="${pageContext.request.contextPath}/admin/homepage.jsp" class="btn">Home</a>

        <script src="scripts/manageOrderScript.js"></script>
        
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
