package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Prodotto;
import model.bean.Utente;
import service.Catalogo;
import service.RegistroUtenti;


@WebServlet(value = "/common/IndexServlet", loadOnStartup = 1)
public class IndexServlet extends HttpServlet {
    
    @EJB
    private RegistroUtenti registroUtenti;

    @EJB
    private Catalogo catalogo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        if (utente == null)
            registroUtenti.inizializzaSessione(null, request.getSession());
        
        // Recupera i dati per le sezioni della pagina
        List<Prodotto> latestProducts = catalogo.getLatestProducts(3);
        List<Prodotto> bestSellingProducts = catalogo.getBestSellingProducts(3);
        List<Prodotto> upcomingProducts = catalogo.getUpcomingProducts(3);

        // Passa i dati recuperati come attributi alla richiesta
        request.setAttribute("latestProducts", latestProducts);
        request.setAttribute("bestSellingProducts", bestSellingProducts);
        request.setAttribute("upcomingProducts", upcomingProducts);
        
        request.getSession().setAttribute("isCliente", 1);

        // Inoltra la richiesta alla JSP per la visualizzazione dei dati
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
    }
}