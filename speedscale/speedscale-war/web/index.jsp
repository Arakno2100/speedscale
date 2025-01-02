<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="errors/404.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Index</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<jsp:include page="jsp/header.jsp"/>
<p><img src="images/banner.png" alt="Banner"></p>

<h1>Novità</h1>
<div class="viewer">
  <c:forEach var="product" items="${latestProducts}">
    <div class="card">
      <p> <img src="${pageContext.request.contextPath}/images/products/${product.image_path}"></p>
      <p>${product.nome}</p>
      <a href="${pageContext.request.contextPath}/common/ProductDetailsServlet?id=${product.id}"> <button>Dettaglio</button></a>
    </div>
  </c:forEach>
</div>

<h1>Più venduti </h1>
<div class="viewer">
  <c:forEach var="product" items="${bestSellingProducts}">
    <div class="card">
      <p> <img src="${pageContext.request.contextPath}/images/products/${product.image_path}"></p>
      <p>${product.nome}</p>
      <a href="${pageContext.request.contextPath}/common/ProductDetailsServlet?id=${product.id}"> <button>Dettaglio</button></a>
    </div>
  </c:forEach>
</div>

<h1> Prossime Uscite </h1>
<div class="viewer">
  <c:forEach var="product" items="${upcomingProducts}">
    <div class="card">
      <p> <img src="${pageContext.request.contextPath}/images/products/${product.image_path}"></p>
      <p>${product.nome}</p>
      <a href="${pageContext.request.contextPath}/common/ProductDetailsServlet?id=${product.id}"> <button>Dettaglio</button></a>
    </div>
  </c:forEach>
</div>


<div class="card">
  <button onclick="location.href='${pageContext.request.contextPath}/common/CatalogServlet'">Tutti i prodotti</button>
</div>



<jsp:include page="jsp/footer.jsp"/>
</body>
</html>