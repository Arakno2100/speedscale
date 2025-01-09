package control.admin;

import model.bean.Ordine;
import model.bean.StatoOrdine;
import model.dao.OrdineDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;

@WebServlet("/admin/UpdateOrderStatusServlet")
public class UpdateOrderStatusServlet extends HttpServlet {
    
    @EJB
    private OrdineDAO ordineDAO;

    @Override
    public void init() throws ServletException {}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long orderId = Long.parseLong(request.getParameter("orderId"));
        String newStatusStr = request.getParameter("newStatus");
        StatoOrdine newStatus = StatoOrdine.valueOf(newStatusStr);

        if (newStatus != null) {
            try {
                // Recupera l'ordine dal servizio
                Ordine ordine = ordineDAO.findById(orderId);

                if (ordine != null) {
                    // Imposta il nuovo stato all'ordine
                    ordine.setStato(newStatus);

                    // Aggiorna l'ordine nel database
                    ordineDAO.save(ordine);

                    // Imposta un messaggio di successo
                    request.setAttribute("message", "Stato ordine aggiornato correttamente.");

                } else {
                    // Imposta un messaggio di errore se l'ordine non esiste
                    request.setAttribute("error", "Ordine non trovato.");
                }

            } catch (Exception e) {
                // Gestisce eventuali errori (es. numero di ordine non valido)
                request.setAttribute("error", "Errore durante l'aggiornamento dello stato.");
            }
        } else {
            request.setAttribute("error", "Dati insufficienti.");
        }

        // Ricarica la pagina degli ordini per visualizzare l'aggiornamento
        response.sendRedirect(getServletContext().getContextPath() + "/admin/ManageOrdersServlet");
    }
}
