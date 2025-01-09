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
            <form id="updateAddressForm" action="${pageContext.request.contextPath}/common/UpdateAddressServlet" method="post">
                <h1 style="color: #0d0d0d">Modifica Indirizzo</h1>

                <input type="hidden" name="id" value="${param.id}" />

                <label id="updateAddressLabel" for="via">Via:</label>
                <input type="text" id="via" name="via" value="${fn:escapeXml(param.via)}" /><br/>
                <span id = "errorAddress"></span>
                <br>
                <label for="citta">Citta:</label>
                <input type="text" id="citta" name="citta" value="${fn:escapeXml(param.citta)}" oninput="validateCitta('updateAddressForm')" /><br/>
                <span id = "errorCitta"></span>
                <br>
                <label for="provincia">Provincia:</label>
                <input type="text" id="provincia" name="provincia" value="${fn:escapeXml(param.provincia)}" oninput="validateProv('updateAddressForm')"/><br/>
                <span id = "errorProv"></span>
                <br>
                <label for="cap">Cap:</label>
                <input type="text" id="cap" name="cap" value="${fn:escapeXml(param.cap)}" oninput="validateCap('updateAddressForm')"/>
                <br/>
                <span id = "errorCap"></span>
                <br>
                <label for="nazione">Nazione:</label>
                <input type="text" id="nazione" name="nazione" value="${fn:escapeXml(param.nazione)}" oninput="validateNaz('updateAddressForm')"><br/>
                <span id = "errorNaz"></span>
                <br>
                <input id="updateAddressButton" type="submit" value="Aggiorna Indirizzo" onmouseover="validateAllAddress('updateAddressForm' , 'updateAddressButton')">
            </form>
        </div>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>