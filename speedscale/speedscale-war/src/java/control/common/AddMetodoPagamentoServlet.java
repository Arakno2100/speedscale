package control.common;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.Indirizzo;
import model.bean.MetodoPagamento;
import model.bean.Utente;
import service.GestioneProfilo;

@WebServlet("/common/AddMetodoPagamentoServlet")
public class AddMetodoPagamentoServlet extends HttpServlet {

    @EJB
    private GestioneProfilo gestioneProfilo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        MetodoPagamento metodo = extractMetodoPagamentoFromRequest(request);
        
        gestioneProfilo.addMetodoPagamento(utente, metodo);
        
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountMetodiPagamento");       
    }

    private MetodoPagamento extractMetodoPagamentoFromRequest(HttpServletRequest request) {        
        
        String intestatario = request.getParameter("intestatario");
        String numero = request.getParameter("numero");
        String mese = request.getParameter("mese");
        String anno = request.getParameter("anno");
        String cvv = request.getParameter("cvv");
        
        MetodoPagamento metodo = new MetodoPagamento(intestatario, numero, mese, anno, cvv);
        
        return metodo;
    }
}
