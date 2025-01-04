package service;

import model.bean.Indirizzo;
import model.bean.MetodoPagamento;
import model.bean.Ordine;
import model.bean.Utente;
import model.dao.IndirizzoDAO;
import model.dao.UtenteDAO;
import model.dao.MetodoPagamentoDAO;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class GestioneProfilo {
    
    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private IndirizzoDAO indirizzoDAO;
    
    @EJB
    private MetodoPagamentoDAO metodoPagamentoDAO;
    
    public GestioneProfilo() {}

    // Modifica i dati personali dell'utente
    public void modificaDatiPersonali(Utente utente, String newNome, String newCognome, Date newDataNascita, String newTelefono) {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        
        // Aggiorna i dati dell'utente
        utente.setNome(newNome);
        utente.setCognome(newCognome);
        utente.setDataNascita(newDataNascita);
        utente.setTelefono(newTelefono);
        
        utenteDAO.save(utente);
        
        System.out.println("Utente (nuovi dati):\t" + utente);
    }
    
    // Aggiungi un nuovo indirizzo di spedizione
    public void addIndirizzoSpedizione(Utente utente, Indirizzo newIndirizzo) {
        if (utente == null || newIndirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }

        // Associa l'indirizzo all'utente
        newIndirizzo.setUtente(utente);

        // Salva l'indirizzo nel database (la prima volta)
        indirizzoDAO.save(newIndirizzo);

        // Aggiungi l'indirizzo all'elenco degli indirizzi di spedizione dell'utente
        List<Indirizzo> indirizzi = utente.getIndirizzi();
        indirizzi.add(newIndirizzo);

        // Associa la lista aggiornata all'utente e salva l'utente nel database
        utente.setIndirizzi(indirizzi);
        utenteDAO.save(utente);

        System.out.println("Utente (aggiunto indirizzo):\t" + newIndirizzo);
    }

    // Modificare un indirizzo
    public void modificaIndirizzoSpedizione(Utente utente, Indirizzo indirizzo, Indirizzo newIndirizzo) {
        if (utente == null || indirizzo == null || newIndirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }

        // Modifica delle proprietà dell'indirizzo esistente con quelle del nuovo indirizzo
        indirizzo.setVia(newIndirizzo.getVia());
        indirizzo.setCitta(newIndirizzo.getCitta());
        indirizzo.setProvincia(newIndirizzo.getProvincia());
        indirizzo.setCap(newIndirizzo.getCap());
        indirizzo.setNazione(newIndirizzo.getNazione());

        // Salva le modifiche
        indirizzoDAO.save(indirizzo);  // Salva l'indirizzo aggiornato

        // Forza il refresh dell'indirizzo, se necessario
        indirizzoDAO.refresh(indirizzo);

        // Ricarica l'utente e gli indirizzi aggiornati
        Utente utenteRicaricato = utenteDAO.findById(utente.getId());  // Ricarica l'utente dal database
        List<Indirizzo> indirizziAggiornati = utenteRicaricato.getIndirizzi();  // Ottieni la lista aggiornata di indirizzi
        utente.setIndirizzi(indirizziAggiornati);  // Imposta gli indirizzi aggiornati nell'utente

        utenteDAO.save(utente);  // Salva l'utente con gli indirizzi aggiornati

        System.out.println("Indirizzo modificato: " + indirizzo);
    }

    // Rimuovi un indirizzo di spedizione
    public void removeIndirizzoSpedizione(Utente utente, Indirizzo indirizzo) {
        if (utente == null || indirizzo == null) {
            throw new IllegalArgumentException("Utente o indirizzo non valido");
        }

        // Ricarica l'utente dal database per avere l'elenco più aggiornato degli indirizzi
        Utente utenteAggiornato = utenteDAO.findById(utente.getId());
        List<Indirizzo> indirizzi = utenteAggiornato.getIndirizzi();

        int index = 0;
        
        for (Indirizzo ind : indirizzi) {
            if (ind.getId() == indirizzo.getId())
                index = indirizzi.indexOf(ind);
        }

        // Rimuovi l'indirizzo dalla lista
        indirizzi.remove(index);

        // Aggiorna l'utente e l'indirizzo nel database
        indirizzoDAO.delete(indirizzo);
        utenteDAO.save(utenteAggiornato);
        
        // Ricarica l'utente e gli indirizzi aggiornati
        Utente utenteRicaricato = utenteDAO.findById(utente.getId());  // Ricarica l'utente dal database
        List<Indirizzo> indirizziAggiornati = utenteRicaricato.getIndirizzi();  // Ottieni la lista aggiornata di indirizzi
        utente.setIndirizzi(indirizziAggiornati);  // Imposta gli indirizzi aggiornati nell'utente

        utenteDAO.save(utente);  // Salva l'utente con gli indirizzi aggiornati

        System.out.println("Utente (rimosso indirizzo):\t" + utenteAggiornato.toString());
    }


    // Aggiungi un nuovo metodo di pagamento
    public void addMetodoPagamento(Utente utente, MetodoPagamento newMetodoPagamento) {
        if (utente == null || newMetodoPagamento == null) {
            throw new IllegalArgumentException("Utente o metodo di pagamento non valido");
        }

        // Associa il metodo di pagamento all'utente
        newMetodoPagamento.setUtente(utente);
        
        //Salva il metodo di pagamento nel database (la prima volta)
        metodoPagamentoDAO.save(newMetodoPagamento);

        //Aggiungere il nuovo metodi di pagamento all'elenco dei metodi di pagamento dell'utente
        List<MetodoPagamento> metodi = utente.getMetodiPagamento();
        metodi.add(newMetodoPagamento);

        //Associare la lista aggiornata all'utente ed aggiornare il database
        utente.setMetodiPagamento(metodi);
        utenteDAO.save(utente);

        System.out.println("Utente (aggiunto metodo di pagamento):\t" + newMetodoPagamento);
    }

    // Modifica un metodo di pagamento esistente
    public void modificaMetodoPagamento(Utente utente, MetodoPagamento metodoPagamento, MetodoPagamento newMetodoPagamento) {
        if (utente == null || metodoPagamento == null || newMetodoPagamento == null) {
            throw new IllegalArgumentException("Utente o metodo pagamento non valido");
        }

        // Modifica delle proprietà del metodo di pagamento esistente con quelle del nuovo metodo di pagamento
        metodoPagamento.setIntestatario(newMetodoPagamento.getIntestatario());
        metodoPagamento.setNumero(newMetodoPagamento.getNumero());
        metodoPagamento.setMeseScadenza(newMetodoPagamento.getMeseScadenza());
        metodoPagamento.setAnnoScadenza(newMetodoPagamento.getAnnoScadenza());

        // Salva le modifiche
        metodoPagamentoDAO.save(metodoPagamento);

        // Forza il refresh dell'indirizzo, se necessar
        metodoPagamentoDAO.refresh(metodoPagamento);
        
        // Ricarica l'utente e gli indirizzi aggiornati
        Utente utenteRicaricato = utenteDAO.findById(utente.getId());  // Ricarica l'utente dal database
        List<MetodoPagamento> metodiAggiornati = utenteRicaricato.getMetodiPagamento();
        utente.setMetodiPagamento(metodiAggiornati);

        utenteDAO.save(utente);  // Salva l'utente con gli indirizzi aggiornati

        System.out.println("Metodo di pagamento modificato: " + metodoPagamento);
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