package control.common;

import model.bean.Indirizzo;
import model.bean.Utente;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.MetodoPagamento;

@WebServlet("/common/RetrieveAccountMetodiPagamento")
public class RetrieveAccountMetodiPagamento extends HttpServlet {

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        if (utente == null) {
            // Se l'utente non è autenticato, reindirizza alla pagina di login
            redirectToLogin(response, request);
            return ;
        }
        
        //Ottenere l'elenco degli indirizzi
        List<MetodoPagamento> metodi = utente.getMetodiPagamento();
        
        System.out.println("Metodi di pagamento:\t" + metodi);
        
        // Imposta i metodi nella sessione dell'utente
        request.getSession().setAttribute("metodiPagamento", metodi);
        
        // Verifica se la richiesta proviene dal carrello
        String fromCart = request.getParameter("fromCart");

        if (fromCart != null && fromCart.equals("true")) {
            String total = request.getParameter("total");

            if (total == null || total.isEmpty()) {
                throw new IllegalArgumentException("Il totale non può essere nullo o vuoto");
            }

            request.setAttribute("total", total);
            /*DA MODIFICARE SICURAMENTE*/
            request.getRequestDispatcher("/common/selectAddress.jsp").forward(request, response);
            return ;
        }

        // Reindirizza alla pagina di gestione dei metodi di pagamento
        response.sendRedirect(request.getContextPath() + "/common/manageMetodiPagamento.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // Metodo per reindirizzare alla pagina di login
    private void redirectToLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath() + "/common/login.jsp");
    }
}
