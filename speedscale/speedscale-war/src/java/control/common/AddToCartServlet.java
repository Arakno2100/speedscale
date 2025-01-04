package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.CarrelloProdotto;
import model.bean.Prodotto;
import model.bean.Utente;
import model.dao.CarrelloDAO;
import model.dao.ProdottoDAO;

@WebServlet("/common/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;
    

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prodottoId = Long.parseLong(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        Carrello carrello;
        if (utente != null) {
            // L'utente è autenticato, recupera il suo carrello
            carrello = utente.getCarrello();
        } else {
            // Creare un nuovo carrello se non è autenticato (o gestire un carrello temporaneo)
            carrello = (Carrello) request.getSession().getAttribute("carrello");
            if (carrello == null) {
                carrello = new Carrello();
                request.getSession().setAttribute("carrello", carrello);
            }
        }
        
        // Cerca se il prodotto è già presente nel carrello
        CarrelloProdotto carrelloProdottoEsistente = null;
        
        for (CarrelloProdotto cp : carrello.getProdotti()) {
            if (cp.getProdotto().getId().equals(prodottoId)) {
                carrelloProdottoEsistente = cp;
                break;
            }
        }
        
        Prodotto prodotto = prodottoDAO.findById(prodottoId);
        
        if (carrelloProdottoEsistente != null) {
            // Se il prodotto esiste, aggiorna la quantità
                        
            if (quantity <= 0 || quantity > prodotto.getQuantitàDisponibile()) {
                request.setAttribute("errors", "La quantità indicata per il prodotto \"" + prodotto.getNome() + "\" non è valida");
                request.getRequestDispatcher("/common/RetrieveAccountCartServlet").forward(request, response);
                return ;
            }
            
            carrelloProdottoEsistente.setQuantità(quantity);
        } else {
            // Se il prodotto non esiste, aggiungilo al carrello
            CarrelloProdotto nuovoCarrelloProdotto = new CarrelloProdotto(carrello, prodotto, quantity);
            carrello.getProdotti().add(nuovoCarrelloProdotto);
        }
        
        // Persisti le modifiche nel database (se necessario)
        if (utente != null) {
            // Salva il carrello dell'utente autenticato
            carrelloDAO.save(carrello);
        }
        
        System.out.println("Carrello aggiornato:\t" + carrello.getProdotti());
        
        // Salva il carrello nella sessione
        request.getSession().setAttribute("cart", carrello);
        request.getSession().setAttribute("cartId", carrello.getId());
        
        // Reindirizza alla pagina di conferma aggiunta al carrello
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountCartServlet");
    }
}