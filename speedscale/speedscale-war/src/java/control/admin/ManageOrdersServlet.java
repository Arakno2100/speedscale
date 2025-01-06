package control.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Ordine;
import model.bean.Utente;
import model.dao.UtenteDAO;

@WebServlet("/admin/ManageOrdersServlet")
public class ManageOrdersServlet extends HttpServlet {
    
    @EJB
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Utente> utenti = utenteDAO.findAll();
        
        List<Ordine> ordini = new ArrayList<>();
        
        if (utenti != null) {
            for (Utente utente : utenti) {
                List<Ordine> ordiniUtente = utente.getOrdini();
                
                if (ordiniUtente != null) {
                    
                    for (Ordine ordine : ordiniUtente)
                        ordini.add(ordine);
                    
                }
            }
        }
        
        request.setAttribute("orders", ordini);
        
        request.getRequestDispatcher("/admin/manageOrders.jsp").forward(request, response);
    }
}