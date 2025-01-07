package service;

import java.util.ArrayList;
import java.util.Date;
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
import model.dao.OrdineDAO;
import model.dao.OrdineProdottoDAO;

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
    private OrdineProdottoDAO ordineProdottoDAO;
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private OrdineDAO ordineDAO;
    
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
            
            carrelloProdottoDAO.save(carrelloProdottoEsistente);
        } else {
            // Il prodotto non è presente nel carrello
            CarrelloProdotto nuovoCarrelloProdotto = new CarrelloProdotto(carrello, prodotto, quantita);
            carrelloProdottoDAO.save(nuovoCarrelloProdotto);
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

    /**
     * Crea un ordine dall'attuale carrello.
     */
    
    
    public void creaOrdine(Carrello carrello, Indirizzo indirizzo, MetodoPagamento pagamento) {
        // Controllo dei parametri
        if (carrello == null || indirizzo == null || pagamento == null || carrello.getProdotti().isEmpty()) {
            throw new IllegalArgumentException("Parametri non validi per la creazione dell'ordine.");
        }

        // Creazione dell'ordine
        Ordine ordine = new Ordine();
        ordine.setUtente(carrello.getUtente());
        ordine.setIndirizzo(indirizzo);
        ordine.setMetodoPagamento(pagamento);
        ordine.setData(new Date());
        ordine.setStato(StatoOrdine.RICEVUTO);

        // Verifica che l'utente non sia null
        if (ordine.getUtente() == null) {
            throw new IllegalArgumentException("L'utente associato all'ordine è null.");
        }

        // Persisti l'ordine nel database
        ordineDAO.save(ordine);

        // Creazione delle voci dell'ordine
        List<OrdineProdotto> vociOrdine = new ArrayList<>();
        for (CarrelloProdotto voce : carrello.getProdotti()) {
            // Verifica che ogni voce del carrello non sia nulla
            if (voce.getProdotto() == null || voce.getQuantità() == 0) {
                throw new IllegalArgumentException("Prodotto nel carrello non valido.");
            }

            OrdineProdotto voceOrdine = new OrdineProdotto();
            voceOrdine.setProdotto(voce.getProdotto());
            voceOrdine.setPrezzoUnitario(voce.getProdotto().getPrezzo());
            voceOrdine.setQuantità(voce.getQuantità());
            voceOrdine.setOrdine(ordine);

            // Salva ogni voce dell'ordine nel database
            ordineProdottoDAO.save(voceOrdine);

            // Aggiungi la voce alla lista dell'ordine
            vociOrdine.add(voceOrdine);
        }

        // Imposta le voci dell'ordine e aggiorna l'ordine
        ordine.setProdotti(vociOrdine);

        // Salva l'ordine aggiornato con le voci
        ordineDAO.save(ordine);

        // Aggiungi l'ordine all'utente
        List<Ordine> ordiniAggiornati = carrello.getUtente().getOrdini();
        ordiniAggiornati.add(ordine);
        carrello.getUtente().setOrdini(ordiniAggiornati);

        // Salva l'utente con il nuovo ordine
        utenteDAO.save(carrello.getUtente());

        // Svuotare il carrello (opzionale)
        carrello.getProdotti().clear();
        carrelloDAO.save(carrello);
    }


}
