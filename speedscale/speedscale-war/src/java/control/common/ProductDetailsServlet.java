package control.common;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/common/ProductDetailsServlet")
public class ProductDetailsServlet extends HttpServlet {

    @EJB
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prodottoId = Long.parseLong(request.getParameter("id"));
        
        //Ottenere prodotto
        Prodotto prodotto = prodottoDAO.findById(prodottoId);
        
        if (prodotto == null) {
            redirectToPageWithError(request, response,"Prodotto non trovato");
            return ;
        }
        
        request.setAttribute("product", prodotto);
        
        request.getRequestDispatcher("/common/productDetails.jsp").forward(request, response);
    }

    private void redirectToPageWithError(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, error);
    }
}