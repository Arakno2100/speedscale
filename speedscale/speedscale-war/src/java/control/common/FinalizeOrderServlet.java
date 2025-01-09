package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.Indirizzo;
import model.bean.Utente;
import model.bean.MetodoPagamento;
import model.dao.IndirizzoDAO;
import model.dao.MetodoPagamentoDAO;
import service.CarrelloService;

@WebServlet("/common/FinalizeOrderServlet")
public class FinalizeOrderServlet extends HttpServlet {
    
    @EJB
    private IndirizzoDAO indirizzoDAO;
    
    @EJB
    private MetodoPagamentoDAO metodoPagamentoDAO;
    
    @EJB
    private CarrelloService carrelloService;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //Ottenere carrello e utente
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        Carrello carrello = utente.getCarrello();
        
        //Ottenere indirizzo
        long addressId = Long.parseLong(request.getParameter("addressId"));
        Indirizzo indirizzo = indirizzoDAO.findById(addressId);
        
        //Ottenere metodo di pagamento
        long metodoId = Long.parseLong(request.getParameter("metodoId"));
        MetodoPagamento metodoPagamento = metodoPagamentoDAO.findById(metodoId);
        
        carrelloService.creaOrdine(carrello, indirizzo, metodoPagamento);
        
        response.sendRedirect(request.getContextPath() + "/common/homepage.jsp");
    }
}