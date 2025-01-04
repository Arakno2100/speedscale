package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.CarrelloProdotto;
import model.bean.Prodotto;
import model.dao.CarrelloDAO;

@WebServlet("/common/RetrieveAccountCartServlet")
public class RetrieveAccountCartServlet extends HttpServlet {
    
    @EJB
    private CarrelloDAO carrelloDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Carrello carrello = (Carrello) request.getSession().getAttribute("cart");
        
        List<CarrelloProdotto> voci = carrello.getProdotti();
        List<Prodotto> prodotti = new ArrayList<>();
        
        float totale = 0;
        
        for (CarrelloProdotto voce : voci) {
            totale += voce.getProdotto().getPrezzo();
            prodotti.add(voce.getProdotto());
        }
        
        request.setAttribute("prodottiCarrello", voci);
        request.setAttribute("items", prodotti);
        request.setAttribute("total", totale);
        
        request.getRequestDispatcher("/common/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}