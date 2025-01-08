<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>Catalogo</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            /* Contenitore dei filtri */
            #filters {
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
                padding: 20px;
                background-color: #f4f4f4;
                margin: 20px 0;
                border-radius: 10px;
            }

            /* Ogni filtro ha uno spazio */
            #filters div {
                margin: 10px;
                flex: 1 1 200px; /* Crea flessibilità per i filtri */
            }

            /* Styling dei filtri */
            label {
                font-size: 1rem;
                margin-bottom: 5px;
                display: block;
            }

            input[type="number"], select {
                width: 100%;
                padding: 10px;
                font-size: 1rem;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
            }

            /* Bottone per applicare i filtri */
            #apply-filters {
                padding: 10px 20px;
                font-size: 1rem;
                background-color: #ED1C24;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            #apply-filters:hover {
                background-color: #0056b3;
            }

            /* Card dei prodotti */
            .viewer {
                display: flex;
                flex-wrap: wrap;
                justify-content: space-evenly;
                padding: 20px;
            }

            .product-card {
                border: 1px solid #ddd;
                border-radius: 10px;
                margin: 10px;
                padding: 15px;
                width: 200px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .product-card img {
                max-width: 100%;
                height: auto;
                margin-bottom: 15px;
            }

            .product-card p {
                font-size: 1rem;
                color: #333;
            }

            .product-card button {
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
            }

            .product-card button:hover {
                background-color: #218838;
            }
        </style>
    </head>

    <body>
        <jsp:include page="../jsp/header.jsp"/>

        <p><img style="width: 100%" src="images/banner.png" alt="Banner"></p>

        <h1>PRODOTTI</h1>

        <div id="filters">
            <!-- Filtro per il prezzo minimo -->
            <div id="min-price-filter">
                <label for="minPrice">Prezzo Minimo:</label>
                <input type="number" id="minPrice" name="minPrice" value="0" min="0">
            </div>

            <!-- Filtro per il prezzo massimo -->
            <div id="max-price-filter">
                <label for="maxPrice">Prezzo Massimo:</label>
                <input type="number" id="maxPrice" name="maxPrice" value="100" min="0">
            </div>

            <!-- Filtro per la scala -->
            <div id="scale-filter">
                <label for="scale">Seleziona scala:</label>
                <select id="scale" name="scale">
                    <option value="">Tutte</option>
                    <option value="SCA_1_10">1:10</option>
                    <option value="SCA_1_18">1:18</option>
                    <option value="SCA_1_24">1:24</option>
                    <option value="SCA_1_32">1:32</option>
                    <option value="SCA_1_43">1:43</option>
                </select>
            </div>

            <!-- Filtro per la marca -->
            <div id="brand-filter">
                <label for="brand">Seleziona marca:</label>
                <select id="brand" name="brand">
                    <option value="">Tutte</option>
                    <option value="BBURAGO">BBURAGO</option>
                    <option value="SPARK">SPARK</option>
                    <option value="LOOKSMART">LOOKSMART</option>
                    <option value="ALTAYA">ALTAYA</option>
                    <option value="LEGO">LEGO</option>
                </select>
            </div>

            <!-- Bottone per applicare il filtro -->
            <button id="apply-filters" onclick="applyFilters()">Filtra</button>
        </div>

        <div class="viewer">
            <c:forEach var="product" items="${products}">
                <div class="card product-card" data-price="${product.prezzo}" data-scale="${product.scala}" data-brand="${product.marca}">
                    <p><img src="${product.urls[0]}" alt="Immagine prodotto"></p>
                    <p>${product.nome}</p>
                    <a href="${pageContext.request.contextPath}/common/ProductDetailsServlet?id=${product.id}">
                        <button>Dettaglio</button>
                    </a>
                </div>
            </c:forEach>
        </div>

        <jsp:include page="../jsp/footer.jsp"/>

        <script>
            // Funzione per applicare i filtri
            function applyFilters() {
                // Prendi i valori dei filtri
                const minPrice = document.getElementById('minPrice').value;
                const maxPrice = document.getElementById('maxPrice').value;
                const selectedScale = document.getElementById('scale').value;
                const selectedBrand = document.getElementById('brand').value;

                // Ottieni tutti i prodotti
                const products = document.querySelectorAll('.product-card');

                // Filtra i prodotti
                products.forEach(product => {
                    const productPrice = parseFloat(product.getAttribute('data-price'));
                    const productScale = product.getAttribute('data-scale');
                    const productBrand = product.getAttribute('data-brand');

                    // Controlla se il prodotto soddisfa tutti i filtri
                    const isPriceInRange = (productPrice >= minPrice && productPrice <= maxPrice);
                    const isScaleMatch = (selectedScale === "" || selectedScale === productScale);
                    const isBrandMatch = (selectedBrand === "" || selectedBrand === productBrand);

                    // Mostra o nascondi il prodotto in base ai filtri
                    if (isPriceInRange && isScaleMatch && isBrandMatch) {
                        product.style.display = 'block';
                    } else {
                        product.style.display = 'none';
                    }
                });
            }
        </script>
    </body>
</html>
