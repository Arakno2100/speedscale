package control.common;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import model.bean.Indirizzo;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import service.GestioneProfilo;

@WebServlet("/common/DeleteAddressServlet")
public class DeleteAddressServlet extends HttpServlet {
    
    @EJB
    private GestioneProfilo gestioneProfilo;
    
    @EJB
    private IndirizzoDAO indirizzoDAO;
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        //Recuperare ID indirizzo
        long addressId = Long.parseLong(request.getParameter("id"));
        
        //Controllo recupero indirizzo
        Indirizzo indirizzo = indirizzoDAO.findById(addressId);
        if (indirizzo == null)
        {
            redirectToErrorPage(response, "ID indirizzo non valido");
            return ;
        }
        
        //Cancellazione indirizzo
        gestioneProfilo.removeIndirizzoSpedizione(utente, indirizzo);
        
        //Redirect
        response.sendRedirect(getServletContext().getContextPath() + "/common/RetrieveAccountAddresses");
        
    }

    private void redirectToErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.sendRedirect(getServletContext().getContextPath() + "/common/manageAddresses.jsp?error=" + errorMessage);
    }
}