package service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.bean.Indirizzo;
import model.bean.MetodoPagamento;
import model.bean.Ordine;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;

@Singleton
public class GestioneProfilo {
    
    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private IndirizzoDAO indirizzoDAO;

    // Modifica i dati personali dell'utente
    public void modificaDatiPersonali(Utente utente, String newNome, String newCognome, String newPassword, Date newDataNascita) {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        
        // Aggiorna i dati dell'utente
        utente.setNome(newNome);
        utente.setCognome(newCognome);
        utente.setPassword(encryptPassword(newPassword));
        utente.setDataNascita(newDataNascita);
        
        utenteDAO.save(utente);
        
        System.out.println("Utente (nuovi dati):\t" + utente);
    }

    // Aggiungi un nuovo indirizzo di spedizione
    public void addIndirizzoSpedizione(Utente utente, Indirizzo newIndirizzo) {
        if (utente == null || newIndirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }
        
        indirizzoDAO.save(newIndirizzo);
        
        // Aggiungi l'indirizzo all'elenco degli indirizzi di spedizione
        List<Indirizzo> indirizzi = utente.getIndirizzi();
        indirizzi.add(newIndirizzo);
        
        utenteDAO.save(utente);
        indirizzoDAO.save(newIndirizzo);
        
        System.out.println("Utente (aggiunto indirizzo):\t" + utente.toString());
    }

    // Modifica un indirizzo di spedizione esistente
    public void modificaIndirizzoSpedizione(Utente utente, Indirizzo indirizzo, Indirizzo newIndirizzo) {
        if (utente == null || indirizzo == null || newIndirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }
        
        // Trova l'indirizzo da modificare
        List<Indirizzo> indirizzi = utente.getIndirizzi();
        if (!indirizzi.contains(indirizzo)) {
            throw new IllegalArgumentException("Indirizzo non trovato");
        }
        
        // Sostituisci l'indirizzo
        int index = indirizzi.indexOf(indirizzo);
        indirizzi.set(index, newIndirizzo);
        
        utenteDAO.save(utente);
        System.out.println("Utente (modificato indirizzo):\t" + utente.toString());
    }

    // Rimuovi un indirizzo di spedizione
    public void removeIndirizzoSpedizione(Utente utente, Indirizzo indirizzo) {
        if (utente == null || indirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }
        
        // Rimuovi l'indirizzo
        List<Indirizzo> indirizzi = utente.getIndirizzi();
        if (!indirizzi.contains(indirizzo)) {
            throw new IllegalArgumentException("Indirizzo non trovato");
        }
        
        indirizzi.remove(indirizzo);
        
        utenteDAO.save(utente);
        System.out.println("Utente (rimosso indirizzo):\t" + utente.toString());
    }

    // Aggiungi un nuovo metodo di pagamento
    public void aggiungiMetodoPagamento(Utente utente, MetodoPagamento newMetodoPagamento) {
        if (utente == null || newMetodoPagamento == null) {
            throw new IllegalArgumentException("Utente o metodo di pagamento non valido");
        }
        
        // Aggiungi il metodo di pagamento alla lista
        List<MetodoPagamento> metodi = utente.getMetodiPagamento();
        metodi.add(newMetodoPagamento);
        
        utenteDAO.save(utente);
        System.out.println("Utente (aggiunto metodo pagameto):\t" + utente.toString());
    }

    // Modifica un metodo di pagamento esistente
    public void modificaMetodoPagamento(Utente utente, MetodoPagamento metodoPagamento, MetodoPagamento newMetodoPagamento) {
        if (utente == null || metodoPagamento == null || newMetodoPagamento == null) {
            throw new IllegalArgumentException("Utente o metodo di pagamento non valido");
        }
        
        // Trova e sostituisci il metodo di pagamento
        List<MetodoPagamento> metodi = utente.getMetodiPagamento();
        if (!metodi.contains(metodoPagamento)) {
            throw new IllegalArgumentException("Metodo di pagamento non trovato");
        }
        
        int index = metodi.indexOf(metodoPagamento);
        metodi.set(index, newMetodoPagamento);
        
        utenteDAO.save(utente);
        System.out.println("Utente (modificato metodo pagameto):\t" + utente.toString());
    }

    // Rimuovi un metodo di pagamento
    public void removeMetodoPagamento(Utente utente, MetodoPagamento metodoPagamento) {
        if (utente == null || metodoPagamento == null) {
            throw new IllegalArgumentException("Utente o metodo di pagamento non valido");
        }
        
        // Rimuovi il metodo di pagamento
        List<MetodoPagamento> metodi = utente.getMetodiPagamento();
        if (!metodi.contains(metodoPagamento)) {
            throw new IllegalArgumentException("Metodo di pagamento non trovato");
        }
        
        metodi.remove(metodoPagamento);
        
        utenteDAO.save(utente);
        System.out.println("Utente (rimosso metodo pagameto):\t" + utente.toString());
    }

    // Visualizza lo storico degli ordini di un utente
    public List<Ordine> visualizzaStoricoOrdini(Utente utente) {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        
        // Recupera lo storico degli ordini dell'utente
        List<Ordine> ordini = utente.getOrdini();
        
        return ordini;
    }
    
    private String encryptPassword(String password) {
        StringBuilder hashString = null;

        try
        {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashString = new StringBuilder();

            for (int i = 0; i < hash.length; i++)
                hashString.append(Integer.toHexString((hash[i] & 0xFF) | 0x100), 1, 3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return hashString.toString();
    }
}