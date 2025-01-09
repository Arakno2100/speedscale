package control.common;

import model.bean.Indirizzo;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import service.GestioneProfilo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/common/UpdateAddressServlet")
public class UpdateAddressServlet extends HttpServlet {

    @EJB
    private GestioneProfilo gestioneProfilo;
    
    @EJB
    private IndirizzoDAO indirizzoDAO;
    
    @Override
    public void init() throws ServletException {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        System.out.println("Utente:\t" + utente);
        
        //Prendere id indirizzo 
        long addressId = Long.parseLong(request.getParameter("id"));
        
        System.out.println("Indirizzo (id):\t" + addressId);

        Indirizzo oldIndirizzo = indirizzoDAO.findById(addressId);
        
        System.out.println("Indirizzo vecchio:\t" + oldIndirizzo);

        if (oldIndirizzo == null)
        {
            redirectToLogin(response, request);
            return ;
        }
        
        //Creo il nuovo indirizzo
        Indirizzo newIndirizzo = createNewAddress(request);
        
        System.out.println("Creo nuovo indirizzo:\t" + newIndirizzo);
        
        gestioneProfilo.modificaIndirizzoSpedizione(utente, oldIndirizzo, newIndirizzo);

        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountAddresses");
    }
    
    private Indirizzo createNewAddress(HttpServletRequest request) {
        String via = request.getParameter("via");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        String nazione = request.getParameter("nazione");

        Indirizzo indirizzo = new Indirizzo(via, citta, provincia, cap, nazione);
        
        return indirizzo;
    }

    private void redirectToLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath() + "/common/login.jsp");
    }
}