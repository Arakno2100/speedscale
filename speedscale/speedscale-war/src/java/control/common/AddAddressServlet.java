package control.common;

import java.io.IOException;
import model.bean.Indirizzo;
import model.bean.Utente;
import service.GestioneProfilo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/common/AddAddressServlet")
public class AddAddressServlet extends HttpServlet {
    
    @EJB
    private GestioneProfilo gestioneProfilo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        Indirizzo newIndirizzo = extractAddressFromRequest(request);
        
        gestioneProfilo.addIndirizzoSpedizione(utente, newIndirizzo);
        
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