package service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.bean.*;
import model.dao.CarrelloDAO;
import model.dao.UtenteDAO;
import model.dao.ProdottoDAO;
import model.dao.CarrelloProdottoDAO;

@Singleton
public class CarrelloService {
    
    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;
    
    @EJB
    private CarrelloProdottoDAO carrelloProdottoDAO;
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    /**
     * Aggiunge un prodotto al carrello.
     * @param carrello
     * @param prodotto
     * @param quantita
     */
    public void addProdottoCarrello(Carrello carrello, Prodotto prodotto, int quantita) {
        if (carrello == null || prodotto == null || quantita <= 0) {
            throw new IllegalArgumentException("Parametri non validi per l'aggiunta al carrello.");
        }
        
        // Controllare se il prodotto è già presente nel carrello
        CarrelloProdotto carrelloProdottoEsistente = null;
        
        for (CarrelloProdotto cp : carrello.getProdotti()) {
            if (cp.getProdotto().getId().equals(prodotto.getId())) {
                carrelloProdottoEsistente = cp;
                break;
            }
        }
        
        if (carrelloProdottoEsistente != null) {
            // Il prodotto è già presente nel carrello
            if (quantita < prodotto.getQuantitàDisponibile()) {
                carrelloProdottoEsistente.setQuantità(quantita);
            }
        } else {
            // Il prodotto non è presente nel carrello
            CarrelloProdotto nuovoCarrelloProdotto = new CarrelloProdotto(carrello, prodotto, quantita);
            carrello.getProdotti().add(nuovoCarrelloProdotto);
        }
        
        // Persisti le modifiche nel database
        carrelloDAO.save(carrello);
        
        Utente utente = carrello.getUtente();
        
        if (utente != null) {
            // Salva il carrello dell'utente autenticato
            utenteDAO.save(utente);
        }
        
        System.out.println("Carrello aggiornato:\t" + carrello.getProdotti());
    }

    /**
     * Rimuove un prodotto dal carrello.
     */
    public void removeProdottoCarrello(Carrello carrello, Prodotto prodotto) {
        if (carrello == null || prodotto == null) {
            throw new IllegalArgumentException("Parametri non validi per la rimozione dal carrello.");
        }
        
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
    }

    
    public void updateQuantitaProdotto(Carrello carrello, Prodotto prodotto, int newQuantita) {
        if (carrello == null || prodotto == null || newQuantita < 0) {
            throw new IllegalArgumentException("Parametri non validi per l'aggiornamento della quantità.");
        }

        
    }
    

    /**
     * Crea un ordine dall'attuale carrello.
     */
    public void creaOrdine(Carrello carrello, Indirizzo indirizzo, MetodoPagamento pagamento) {
        if (carrello == null || indirizzo == null || pagamento == null || carrello.getProdotti().isEmpty()) {
            throw new IllegalArgumentException("Parametri non validi per la creazione dell'ordine.");
        }

        
    }
}
