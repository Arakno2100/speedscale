package control.admin;

import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/admin/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long prodottoId = Long.parseLong(request.getParameter("id"));
        
        
        Prodotto prodotto = prodottoDAO.findById(prodottoId);
        
        prodotto.setQuantitàDisponibile(-1);
        
        prodottoDAO.save(prodotto);
        
        response.sendRedirect(request.getContextPath() + "/admin/RetrieveProductsServlet");
    }
}