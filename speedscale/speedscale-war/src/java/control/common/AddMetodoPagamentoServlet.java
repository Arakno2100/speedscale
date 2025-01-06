package control.common;

import model.bean.MetodoPagamento;
import model.bean.Utente;
import service.GestioneProfilo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/common/AddMetodoPagamentoServlet")
public class AddMetodoPagamentoServlet extends HttpServlet {

    @EJB
    private GestioneProfilo gestioneProfilo;

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        MetodoPagamento metodo = extractMetodoPagamentoFromRequest(request);
        
        gestioneProfilo.addMetodoPagamento(utente, metodo);
        
        // Verifica se la richiesta proviene dal carrello
        String fromCart = request.getParameter("fromCart");

        if (fromCart != null && fromCart.equals("true")) {
            String total = request.getParameter("total");

            if (total == null || total.isEmpty()) {
                throw new IllegalArgumentException("Il totale non può essere nullo o vuoto");
            }

            request.setAttribute("total", total);
            request.setAttribute("addressId", request.getParameter("addressId"));
            request.getRequestDispatcher("/common/selectMetodoPagamento.jsp").forward(request, response);
            return ;
        }
        
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountMetodiPagamento");       
    }

    private MetodoPagamento extractMetodoPagamentoFromRequest(HttpServletRequest request) {        
        
        String intestatario = request.getParameter("intestatario");
        String numero = request.getParameter("numero");
        String mese = request.getParameter("mese");
        String anno = request.getParameter("anno");
        String cvv = request.getParameter("cvv");
        
        MetodoPagamento metodo = new MetodoPagamento(numero, intestatario, mese, anno, cvv);
        
        return metodo;
    }
}
