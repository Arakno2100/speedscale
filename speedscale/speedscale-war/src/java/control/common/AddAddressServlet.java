package control.common;

import model.bean.Indirizzo;
import model.bean.Utente;
import service.GestioneProfilo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/common/AddAddressServlet")
public class AddAddressServlet extends HttpServlet {
    
    @EJB
    private GestioneProfilo gestioneProfilo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        Indirizzo newIndirizzo = extractAddressFromRequest(request);
        
        gestioneProfilo.addIndirizzoSpedizione(utente, newIndirizzo);
        
        // Verifica se la richiesta proviene dal carrello
        String fromCart = request.getParameter("fromCart");

        if (fromCart != null && fromCart.equals("true")) {
            String total = request.getParameter("total");

            if (total == null || total.isEmpty()) {
                throw new IllegalArgumentException("Il totale non può essere nullo o vuoto");
            }

            request.setAttribute("total", total);
            request.getRequestDispatcher("/common/selectAddress.jsp").forward(request, response);
            return ;
        }
        
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountAddresses");       
    }

    private Indirizzo extractAddressFromRequest(HttpServletRequest request) {
        String via = request.getParameter("via");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        String nazione = request.getParameter("nazione");

        Indirizzo indirizzo = new Indirizzo(via, citta, provincia, cap, nazione);
        
        return indirizzo;
    }
}