package control.common;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.MetodoPagamento;
import model.bean.Utente;
import model.dao.MetodoPagamentoDAO;
import service.GestioneProfilo;

public class DeleteMetodoPagamento extends HttpServlet {
    
    @EJB
    private MetodoPagamentoDAO metodoPagamentoDAO;
    
    @EJB
    private GestioneProfilo gestioneProfilo;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        //Recuperare ID indirizzo
        long metodoId = Long.parseLong(request.getParameter("id"));
        
        //Controllo recupero indirizzo
        MetodoPagamento metodo = metodoPagamentoDAO.findById(metodoId);
        
        if (metodo == null)
        {
            redirectToErrorPage(response, "ID metodo di pagamento non valido");
            return ;
        }
        
        //Cancellazione indirizzo
        gestioneProfilo.removeMetodoPagamento(utente, metodo);
                
        //Redirect
        response.sendRedirect(getServletContext().getContextPath() + "/common/RetrieveAccountAddresses");
        
    }

    private void redirectToErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.sendRedirect(getServletContext().getContextPath() + "/common/manageMetodiPagamento.jsp?error=" + errorMessage);
    }
}
