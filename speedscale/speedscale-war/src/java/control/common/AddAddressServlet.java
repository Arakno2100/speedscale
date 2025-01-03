package control.common;

import model.bean.Indirizzo;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;

@WebServlet("/common/AddAddressServlet")
public class AddAddressServlet extends HttpServlet {
    
    @EJB
    private IndirizzoDAO indirizzoDAO;
    
    @EJB
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        Indirizzo newIndirizzo = extractAddressFromRequest(request);
        
        saveAddressAndUserLink(newIndirizzo, utente);

        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountAddresses");       
    }

    private Indirizzo extractAddressFromRequest(HttpServletRequest request) {
        String via = request.getParameter("via");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        String nazione = request.getParameter("nazione");

        Indirizzo indirizzo = new Indirizzo();
        
        indirizzo.setVia(via);
        indirizzo.setCitta(citta);
        indirizzo.setProvincia(provincia);
        indirizzo.setCap(cap);
        indirizzo.setNazione(nazione);

        return indirizzo;
    }

    private void saveAddressAndUserLink(Indirizzo indirizzo, Utente utente) {
        
        indirizzoDAO.save(indirizzo);
        
        List<Indirizzo> indirizzi = utente.getIndirizzi();
        
        indirizzi.add(indirizzo);
        
        utente.setIndirizzi(indirizzi);
        
        indirizzoDAO.save(indirizzo);
        utenteDAO.save(utente);
    }
}