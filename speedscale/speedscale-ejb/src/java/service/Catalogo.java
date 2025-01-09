package service;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import model.bean.Prodotto;
import model.dao.ProdottoDAO;

@Singleton
@Startup
public class Catalogo {
    
    @EJB
    private ProdottoDAO prodottoDAO;

    
    private List<Prodotto> prodotti;

    public Catalogo() {
        prodotti = new ArrayList<>();
    }

    /**
     * Aggiunge un nuovo prodotto al catalogo.
     * @param prodotto Il prodotto da aggiungere.
     * @return true se il prodotto è stato aggiunto con successo, false se già presente.
     */
    public boolean addNewProdotto(Prodotto prodotto) {
        if (prodotti.contains(prodotto)) {
            return false;  // Il prodotto è già presente
        }
        
        System.out.println("Catalogo (aggiunto nuovo prodotto):\t" + prodotto);
        
        return prodotti.add(prodotto);
    }

    /**
     * Modifica un prodotto esistente nel catalogo.
     * @param prodotto Il prodotto da modificare.
     * @param nuovoProdotto Il prodotto con i nuovi dati.
     * @return true se la modifica è avvenuta con successo, false se il prodotto non esiste.
     */
    public boolean modificaProdotto(Prodotto prodotto, Prodotto nuovoProdotto) {
        int index = prodotti.indexOf(prodotto);
        if (index == -1) {
            return false;  // Il prodotto non esiste nel catalogo
        }
        prodotti.set(index, nuovoProdotto);
        return true;
    }

    /**
     * Rimuove un prodotto dal catalogo.
     * @param prodotto Il prodotto da rimuovere.
     * @return true se il prodotto è stato rimosso, false se non esiste nel catalogo.
     */
    public boolean rimuoviProdotto(Prodotto prodotto) {
        return prodotti.remove(prodotto);
    }

    /**
     * Restituisce la lista dei prodotti nel catalogo.
     * @return La lista dei prodotti.
     */
    public List<Prodotto> getProdotti() {
        return prodottoDAO.findAll();
    }
    
    public List<Prodotto> getLatestProducts(int limit) {
        return prodottoDAO.findAll();
    }
    
    public List<Prodotto> getBestSellingProducts(int limit) {
        return prodottoDAO.findAll();
    }
    
    public List<Prodotto> getUpcomingProducts(int limit) {
        return prodottoDAO.findAll();
    }
}
