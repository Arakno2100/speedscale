<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="../css/style.css">
        <meta charset="UTF-8">
        <title>Nuovo Metodo Pagamento</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        
        <script src = "scripts/validate.js"></script>

        <div id="updateProduct">
            <form id="addPaymentMethodForm" action="${pageContext.request.contextPath}/common/AddMetodoPagamentoServlet" method="post">
                <h1 style="color: #0d0d0d">Aggiungi Metodo di Pagamento</h1>
                
                <label id="addPaymentMethodLabel" for="intestatario">Intestatario:</label>
                <input class="addPaymentInput" type="text" id="intestatario" name="intestatario" required oninput="validateIntestatario('addPaymentMethodForm')">
                <br/>
                <span id="errorIntestatario"></span>
                <br>                
                <label for="numero">Numero della Carta:</label>
                <input class="addPaymentInput" type="text" id="numero" name="numero" required maxlength="16" oninput="validateNumeroCarta('addPaymentMethodForm')">
                <br/>
                <span id="errorNumero"></span>
                <br>
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
                <label for="anno">Anno di Scadenza:</label>
                <input class="addPaymentInput" type="number" id="anno" name="anno" required min="2025" max="2100" oninput="validateAnno('addPaymentMethodForm')">
                <br/>
                <span id="errorAnno"></span>
                <br>
                <label for="cvv">CVV:</label>
                <input class="addPaymentInput" type="text" id="cvv" name="cvv" required maxlength="3" oninput="validateCVV('addPaymentMethodForm')">
                <br/>
                <span id="errorCVV"></span>
                <br>
                
                <c:if test="${param.fromCart == 'true'}">
                    <input type="hidden" name="fromCart" value="true">
                    <input type="hidden" name="total" value="${param.total}">
                </c:if>
                
                <button id="addPaymentButton" type="submit" onmouseover="validateAllPayment('addPaymentMethodForm', 'addPaymentButton')">Aggiungi Metodo di Pagamento</button>
            </form>
        </div>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>