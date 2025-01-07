<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="../css/style.css">
        <meta charset="UTF-8">
        <title>Aggiungi Prodotto</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        
        <h2>Modifica Prodotto</h2>
        
        <script src="scripts/validate.js"></script>
        
        <form id="addProductForm" action="${pageContext.request.contextPath}/admin/AddProductServlet" method="post" onsubmit="removeSpecial()">
            <h1>Aggiungi Prodotto</h1>
            <label id="addProductLabel" for="nome">Nome:</label>
            <input class="addProductInput" type="text" id="nome" name="nome" required>

            <label for="descrizione">Descrizione:</label>
            <textarea style="resize: none" id="descrizione" name="descrizione" required></textarea>

            <label for="scala">Scala:</label><br/>
            <select name="scala">
                <option value="SCA_1_10">1:10</option>
                <option value="SCA_1_18">1:18</option>
                <option value="SCA_1_24">1:24</option>
                <option value="SCA_1_32">1:32</option>
                <option value="SCA_1_43">1:43</option>
            </select>
            <br><br>
                
            <label for="marca">Marca:</label><br/>
            <select name="marca">
                <option value="BBURAGO">BBURAGO</option>
                <option value="SPARK">SPARK</option>
                <option value="LOOKSMART">LOOKSMART</option>
                <option value="ALTAYA">ALTAYA</option>
                <option value="LEGO">LEGO</option>
            </select>
            <br><br>

            <label for="prezzo">Prezzo:</label>
            <input class="addProductInput" type="number" step="any" id="prezzo" name="prezzo" required  oninput="validatePrice('prezzo' , 'spanPrezzo')">
            <br>
            <span style="color: #bd2130" id = "spanPrezzo"></span>
            <br>

            <label for="quantita">Quantità:</label>
            <input class="addProductInput" type="number" step="1" id="quantita" name="quantita" required oninput="validatePrice('quantita' , 'spanQuantita')">
            <br>
            <span style="color: #bd2130" id = "spanQuantita"></span>
            <br>

            <label for="urls">Immagine:</label>
            <input class="addProductInput"  type="text" id="urls" name="urls" required>

            <button id="addProductButton" type="submit" onmouseover="validateQuantPrice('prezzo' , 'quantita' , 'addProductButton')">Aggiungi Prodotto</button>
        </form>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
