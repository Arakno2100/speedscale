package control.common;

import model.bean.Utente;
import model.dao.MetodoPagamentoDAO;
import service.GestioneProfilo;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Indirizzo;
import model.bean.MetodoPagamento;

@WebServlet("/common/UpdateMetodoPagamentoServlet")
public class UpdateMetodoPagamentoServlet extends HttpServlet {

    @EJB
    private GestioneProfilo gestioneProfilo;
    
    @EJB
    private MetodoPagamentoDAO metodoPagamentoDAO;
    
    @Override
    public void init() throws ServletException {}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        System.out.println("Utente:\t" + utente);
        
        //Prendere id metodo pagamento 
        long metodoId = Long.parseLong(request.getParameter("id"));
        
        System.out.println("Metodo pagamento (id):\t" + metodoId);

        MetodoPagamento oldMetodo = metodoPagamentoDAO.findById(metodoId);
        
        System.out.println("Metodo pagamento vecchio:\t" + oldMetodo);

        if (oldMetodo == null)
        {
            redirectToLogin(response, request);
            return ;
        }
        
        //Creo il nuovo metodo pagamento
        MetodoPagamento newMetodo = createNewMetodoPagamento(request);
        
        System.out.println("Creo nuovo metodo pagamento:\t" + newMetodo);
        
        gestioneProfilo.modificaMetodoPagamento(utente, oldMetodo, newMetodo);

        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountMetodiPagamento");
    }
    
    private MetodoPagamento createNewMetodoPagamento(HttpServletRequest request) {
        String intestatario = request.getParameter("intestatario");
        String numero = request.getParameter("numero");
        String mese = request.getParameter("mese");
        String anno = request.getParameter("anno");
        String cvv = request.getParameter("cvv");
        
        MetodoPagamento metodo = new MetodoPagamento(numero, intestatario, mese, anno, cvv);
        
        return metodo;
    }
    
    private void redirectToLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath() + "/common/login.jsp");
    }
}
