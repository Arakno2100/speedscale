<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Modifica Prodotto</title>
    </head>
    
    <body>
        <jsp:include page="/jsp/header.jsp"/>
        
        <h2>Modifica Prodotto</h2>
        
        <script src="scripts/validate.js"></script>
        
        <div id="updateProduct">
            <form action="${pageContext.request.contextPath}/admin/UpdateProductServlet" method="post" onsubmit="removeSpecial()">
                <input type="hidden" name="id" value="${param.id}" />

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="${fn:escapeXml(param.nome)}" /><br/>

                <label for="descrizione">Descrizione:</label><br/>
                <textarea style="resize: none" id="descrizione" name="descrizione" rows="4" cols="50" >${fn:escapeXml(param.descrizione)}</textarea><br/>
                
                <label for="scala">Scala:</label><br/>
                <select name="scala">
                    <option value="SCA_1_10" ${param.scala == 'SCA_1_10' ? 'selected' : ''}>1:10</option>
                    <option value="SCA_1_18" ${param.scala == 'SCA_1_18' ? 'selected' : ''}>1:18</option>
                    <option value="SCA_1_24" ${param.scala == 'SCA_1_24' ? 'selected' : ''}>1:24</option>
                    <option value="SCA_1_32" ${param.scala == 'SCA_1_32' ? 'selected' : ''}>1:32</option>
                    <option value="SCA_1_43" ${param.scala == 'SCA_1_43' ? 'selected' : ''}>1:43</option>
                </select>
                <br><br>
                
                
                <label for="marca">Marca:</label><br/>
                <select name="marca">
                    <option value="BBURAGO" ${param.marca == 'BBURAGO' ? 'selected' : ''}>BBURAGO</option>
                    <option value="SPARK" ${param.marca == 'SPARK' ? 'selected' : ''}>SPARK</option>
                    <option value="LOOKSMART" ${param.marca == 'LOOKSMART' ? 'selected' : ''}>LOOKSMART</option>
                    <option value="ALTAYA" ${param.marca == 'ALTAYA' ? 'selected' : ''}>ALTAYA</option>
                    <option value="LEGO" ${param.marca == 'LEGO' ? 'selected' : ''}>LEGO</option>
                </select>
                <br><br>
                
                <label for="prezzo">Prezzo:</label>
                <input type="number" id="prezzo" name="prezzo" step="any" value="<fmt:formatNumber value="${param.prezzo}" type="number" maxFractionDigits="2" minFractionDigits="2" />" oninput="validatePrice('prezzo' , 'spanPrezzo' )" /><br/>
                <span style="color: #bd2130" id = "spanPrezzo"></span>
                <br>

                <label for="quantita">Quantità:</label>
                <input type="number" id="quantita" name="quantita" step="1" value="${param.quantita}"  oninput="validatePrice('quantita' , 'spanQuantita' )"/><br/>
                <span  style="color: #bd2130" id = "spanQuantita"></span>
                <br>
                
                <%-- Assicurati che param.urls sia una stringa --%>
                <c:set var="urlsString" value="${param.urls}" />
                <%-- Rimuovi parentesi quadre --%>
                <c:set var="cleanedUrls" value="${fn:replace(fn:replace(fn:replace(fn:replace(urlsString, '[', ''), ']', ''), '{', ''), '}', '')}" />
                <%-- Estrai il primo elemento --%>
                <c:set var="firstUrl" value="${fn:split(cleanedUrls, ',')[0]}" />
                <label for="urls">Immagine:</label>
                <input type="text" id="urls" name="urls" value="${firstUrl}">

                <input id="buttonUpdate" type="submit" value="Aggiorna Prodotto" onmouseover="validateQuantPrice('prezzo' , 'quantita' , 'buttonUpdate' )" />
            </form>
        </div>

        <jsp:include page="/jsp/footer.jsp"/>
    </body>
</html>
