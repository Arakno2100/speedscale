package control.admin;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;
import java.io.IOException;
import java.util.List;



@WebServlet("/admin/RetrieveProductsServlet")
public class RetrieveProductsServlet extends HttpServlet {

    @EJB
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Prodotto> prodotti = prodottoDAO.findAll();
        
        request.setAttribute("products", prodotti);
        
        request.getRequestDispatcher("/admin/manageProducts.jsp").forward(request, response);
    }
}