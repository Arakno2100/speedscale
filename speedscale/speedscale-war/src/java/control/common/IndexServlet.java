package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Prodotto;
import service.Catalogo;


@WebServlet(value = "/common/IndexServlet", loadOnStartup = 1)
public class IndexServlet extends HttpServlet {

    @EJB
    private Catalogo catalogo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Recupera i dati per le sezioni della pagina
        List<Prodotto> latestProducts = catalogo.getLatestProducts(3);
        List<Prodotto> bestSellingProducts = catalogo.getBestSellingProducts(3);
        List<Prodotto> upcomingProducts = catalogo.getUpcomingProducts(3);

        // Passa i dati recuperati come attributi alla richiesta
        request.setAttribute("latestProducts", latestProducts);
        request.setAttribute("bestSellingProducts", bestSellingProducts);
        request.setAttribute("upcomingProducts", upcomingProducts);

        // Inoltra la richiesta alla JSP per la visualizzazione dei dati
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
    }
}