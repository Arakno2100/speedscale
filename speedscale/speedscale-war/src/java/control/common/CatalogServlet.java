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
        
        int maxPrice = extractMaxPrice(request);
        
        prodotti = prodotti.stream()
                .filter(product -> product.getPrezzo() <= maxPrice)
                .collect(Collectors.toList());
        
        request.setAttribute("products", prodotti);
        
        request.getRequestDispatcher("/common/catalog.jsp").forward(request, response);
    }

    private int extractMaxPrice(HttpServletRequest request) {
        int maxPrice;

        try
        {
            maxPrice = Integer.parseInt(request.getParameter("maxPrice"));
        }
        catch (NumberFormatException e)
        {
            maxPrice = Integer.MAX_VALUE;
        }

        return maxPrice > 0 ? maxPrice : Integer.MAX_VALUE;
    }
}