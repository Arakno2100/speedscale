package control.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Carrello;
import model.bean.Prodotto;
import model.bean.CarrelloProdotto;
import model.dao.CarrelloDAO;
import model.dao.ProdottoDAO;
import model.dao.CarrelloProdottoDAO;

@WebServlet("/common/RemoveCartItemServlet")
public class RemoveCartItemServlet extends HttpServlet {
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;
    
    @EJB
    private CarrelloProdottoDAO carrelloProdottoDAO;
    
    
    @Override
    public void init() throws ServletException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        long prodottoId = Long.parseLong(request.getParameter("productId"));
        
        Prodotto prodotto = (Prodotto) prodottoDAO.findById(prodottoId);
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        
        List<CarrelloProdotto> voci = carrello.getProdotti();
        
        System.out.println("Prodotto da rimuovere:\t" + prodotto);
        
        int index = -1;
        
        for (CarrelloProdotto voce : voci) {
            if (voce.getProdotto().getId() == prodotto.getId()) {
                index = voci.indexOf(voce);
            }
        }
        
        if (index != -1) {
            System.out.println("Corrispondenza trovata");
            
            CarrelloProdotto voce = voci.get(index);
                
            //Ripristinare quantità in magazzino
            prodotto.setQuantitàDisponibile(prodotto.getQuantitàDisponibile() + voce.getQuantità());
            voci.remove(voce);
            carrello.setProdotti(voci);
            prodottoDAO.save(prodotto);
            carrelloProdottoDAO.delete(voce);
            carrelloDAO.save(carrello);                

            System.out.println("Nuovo carrello:\t" + carrello);
        }

        // Reindirizza alla pagina del carrello dopo la rimozione
        response.sendRedirect(request.getContextPath() + "/common/RetrieveAccountCartServlet");
    }
}