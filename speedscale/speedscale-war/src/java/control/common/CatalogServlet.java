package control.common;

import service.Catalogo;
import model.bean.Prodotto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;

@WebServlet("/common/CatalogServlet")
public class CatalogServlet extends HttpServlet {
    
    @EJB
    private Catalogo catalogo;
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Prodotto> prodotti = catalogo.getProdotti();
        
        // Ottieni il parametro di ricerca (se presente)
        String searchTerm = request.getParameter("searchQuery");
        
        if (searchTerm != null && !searchTerm.isEmpty()) {
            // Filtro per il termine di ricerca (in questo caso sul nome del prodotto)
            prodotti = prodotti.stream()
                    .filter(product -> product.getNome().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        request.setAttribute("products", prodotti);
        
        request.getRequestDispatcher("/common/catalog.jsp").forward(request, response);
    }
}