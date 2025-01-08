<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styleError.css">
        <title>Osps, problema?</title>
    </head>

    <body>
        <jsp:include page="/jsp/header.jsp"/>

        <div id="container">
            <h1> Purtroppo la pagina che cerchi non è disponibile</h1>
            <p>Nel frattempo, controlla quali sono le ultime novità nel nostro sito.</p>
            <a href="${pageContext.request.contextPath}/common/CatalogServlet"> <button id="button"> Home </button></a>
        </div>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
