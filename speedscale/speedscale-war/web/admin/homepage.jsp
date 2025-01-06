<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            <c:if test="${isAdmin == 1 || isGestoreOrdini == 1}">
                <a href="<%=request.getContextPath()%>/admin/ManageOrdersServlet" class="aAdminPage">Gestisci Ordini</a>
            </c:if>
            <c:if test="${isAdmin == 1 || isGestoreOrdini == 1}">
                <a href="<%=request.getContextPath()%>/admin/ManageOrdersServlet" class="aAdminPage">Gestisci Ordini</a>
            </c:if>
        </div>
        
        <a href="<%=request.getContextPath()%>/common/Logout" class="btn">Logout</a>
        
        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>

