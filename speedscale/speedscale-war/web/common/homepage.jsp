<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <title>Homepage</title>
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        
        <h1>Benvenuto, ${utente.nome}!</h1>

        <div id="buttonHomepage">
            <a href="<%=request.getContextPath()%>/common/updateAccountDetails.jsp" class="aAdminPage">Dati Personali</a>
            <a href="<%=request.getContextPath()%>/common/RetrieveAccountAddresses" class="aAdminPage">Gestione Indirizzi</a>
            <a href="<%=request.getContextPath()%>/common/RetrieveAccountMetodiPagamento" class="aAdminPage">Gestione Metodi Pagamento</a>
            <a href="<%=request.getContextPath()%>/common/RetrieveAccountOrdersServlet" class="aAdminPage">Cronologia Ordini</a>
        </div>
        
        <a href="<%=request.getContextPath()%>/common/Logout" class="btn">Logout</a>
        
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>

