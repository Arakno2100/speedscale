<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="../css/style.css">
    <meta charset="UTF-8">
    <title>Nuovo Indirizzo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>
<script src = "scripts/validate.js"></script>
<div id="updateProduct">
<form id="updatePaymentMethodForm" action="${pageContext.request.contextPath}/common/UpdateMetodoPagamentoServlet" method="post">
    <h1 style="color: #0d0d0d">Modifica Metodo Pagamento</h1>

    <input type="hidden" name="id" value="${param.id}" />

    <label for="intestatario">Intestatario</label>
    <input type="text" id="intestatario" name="intestatario" value="${fn:escapeXml(param.intestatario)}"/><br/>
    <span id="errorIntestatario"></span>
    <br>

    <label for="numero">Numero Carta</label>
    <input type="text" id="numero" name="numero" value="${fn:escapeXml(param.numero)}"/><br/>
    <span id="errorNumero"></span>
    <br>

    <!-- Mese di scadenza -->
    <label for="mese">Mese di Scadenza:</label>
    <select class="addPaymentInput" id="mese" name="mese" required>
        <option value="" disabled selected>Seleziona Mese</option>
        <option value="01">01 - Gennaio</option>
        <option value="02">02 - Febbraio</option>
        <option value="03">03 - Marzo</option>
        <option value="04">04 - Aprile</option>
        <option value="05">05 - Maggio</option>
        <option value="06">06 - Giugno</option>
        <option value="07">07 - Luglio</option>
        <option value="08">08 - Agosto</option>
        <option value="09">09 - Settembre</option>
        <option value="10">10 - Ottobre</option>
        <option value="11">11 - Novembre</option>
        <option value="12">12 - Dicembre</option>
    </select>
    <br/>
    <span id="errorMese"></span>
    <br>

    <!-- Anno di scadenza -->
    <label for="anno">Anno di Scadenza:</label>
    <input class="addPaymentInput" type="number" id="anno" name="anno" required min="2025" max="2100" oninput="validateAnno('addPaymentMethodForm')">
    <br/>
    <span id="errorAnno"></span>
    <br>
    
    <input type="hidden" name="cvv" value="${param.cvv}" />

    <input id="updatePaymentMethodButton" type="submit" value="Aggiorna Metodo di Pagamento" onmouseover="validateAllPaymentMethod('updatePaymentMethodForm', 'updatePaymentMethodButton')" />
</form>
</div>

<jsp:include page="/jsp/footer.jsp"/>
</body>
</html>