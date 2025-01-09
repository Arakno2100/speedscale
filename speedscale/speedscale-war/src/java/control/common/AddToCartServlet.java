package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.Prodotto;
import model.bean.Utente;
import model.dao.CarrelloDAO;
import model.dao.ProdottoDAO;
import service.CarrelloService;

@WebServlet("/common/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;
    
    @EJB
    private CarrelloService carrelloService;
    

    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        
        Carrello carrello;
        
        if (utente != null) {
            // L'utente è autenticato, recupera il suo carrello
            carrello = utente.getCarrello();
        } else {
            // Utente non autenticato -> Prendere carrello temporaneo dalla sessione
            carrello = (Carrello) request.getSession().getAttribute("carrello");
            
            if (carrello == null) {
                //Carrello temporaneo non esistente -> Creazione di un nuovo carrello temporaneo e inserimento nella sessione
                carrello = new Carrello();
                request.getSession().setAttribute("carrello", carrello);
            }
        }
        
        //Recuperare il prodotto da aggiungere al carrello
        long prodottoId = Long.parseLong(request.getParameter("productId"));
        Prodotto prodotto = prodottoDAO.findById(prodottoId);
        
        //Controllare la quantità da inserire nel carrello
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        if (quantity <= 0 || quantity > prodotto.getQuantitàDisponibile()) {
                request.setAttribute("errors", "La quantità indicata per il prodotto \"" + prodotto.getNome() + "\" non è valida");
                request.getRequestDispatcher("/common/RetrieveAccountCartServlet").forward(request, response);
                return ;
        }
        
        carrelloService.addProdottoCarrello(carrello, prodotto, quantity);
        
        System.out.println("Carrello aggiornato:\t" + carrello.getProdotti());
        
        // Salva il carrello nella sessione
        request.getSession().setAttribute("carrello", carrello);
        request.getSession().setAttribute("cartId", carrello.getId());
        
        // Reindirizza alla pagina di conferma aggiunta al carrello
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountCartServlet");
    }
}