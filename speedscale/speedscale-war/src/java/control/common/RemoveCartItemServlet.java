package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.Prodotto;
import model.dao.ProdottoDAO;
import service.CarrelloService;

@WebServlet("/common/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private CarrelloService carrelloService;
    
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        long prodottoId = Long.parseLong(request.getParameter("productId"));
        
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        Prodotto prodotto = (Prodotto) prodottoDAO.findById(prodottoId);
        
        carrelloService.removeProdottoCarrello(carrello, prodotto);

        // Reindirizza alla pagina del carrello dopo la rimozione
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountCartServlet");
    }
}