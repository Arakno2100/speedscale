package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.CarrelloProdotto;
import model.bean.Prodotto;
import model.bean.Utente;
import service.RegistroUtenti;

@WebServlet("/common/RetrieveAccountCartServlet")
public class RetrieveAccountCartServlet extends HttpServlet {
    
    @EJB
    private RegistroUtenti registroUtenti;
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        
        if (utente == null || carrello == null) {
            registroUtenti.inizializzaSessione(utente, request.getSession());
            carrello = (Carrello) request.getSession().getAttribute("carrello");
        }
        
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