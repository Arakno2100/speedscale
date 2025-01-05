package service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import model.bean.Utente;
import model.dao.UtenteDAO;
import model.bean.Carrello;
import model.dao.CarrelloDAO;
import model.bean.Ruolo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import model.bean.CarrelloProdotto;

@Singleton
public class RegistroUtenti {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;
    
    @EJB
    private CarrelloService carrelloService;

    public RegistroUtenti() {}

    /**
     * Autentica un utente con la sua email e password.
     * Confronta la password fornita con l'hash salvato nel database.
     * 
     * @param email email dell'utente
     * @param pwd password dell'utente
     * @return Utente se l'autenticazione è riuscita, null altrimenti
     */
    public Utente authenticate(String email, String pwd) {
        Utente utenteBean = utenteDAO.findByEmail(email);

        if (utenteBean != null && utenteBean.getPassword().equals(pwd))
            return utenteBean;
        
        return null;
    }

    /**
     * Crea un nuovo utente, criptando la password prima di salvarlo nel database.
     * 
     * @param email email dell'utente
     * @param pwd password dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param dataNascita data di nascita dell'utente
     * @return Utente creato
     */
    @Transactional
    public Utente createUtente(String email, String pwd, String nome, String cognome, Date dataNascita) {
        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email già utilizzata");
        }

        Utente nuovoUtente = new Utente();
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(encryptPassword(pwd));  // Cripta la password prima di salvarla
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setDataNascita(dataNascita);
        
        List<Ruolo> ruoli = new ArrayList<>();
        ruoli.add(Ruolo.CLIENTE);
        
        nuovoUtente.setRuoli(ruoli);

        utenteDAO.save(nuovoUtente);

        return nuovoUtente;
    }

    /**
     * Controlla se l'email è già associata a un altro utente.
     * 
     * @param email email da verificare
     * @return true se l'email è già in uso, false altrimenti
     */
    private boolean isEmailTaken(String email) {
        List<Utente> utenti = utenteDAO.findAll();
        return utenti.stream().anyMatch(utente -> utente.getEmail().equals(email));
    }

    /**
     * Inizializza la sessione per un utente autenticato.
     * 
     * @param utente Utente da memorizzare nella sessione
     * @param session La sessione HTTP da aggiornare
     */
    public void inizializzaSessione(Utente utente, HttpSession session) {
        
        if (utente == null) {
            //Utente non loggato
            //Controllare se carrello temporaneo già presente
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            
            System.out.println("Utente non loggato");
            System.out.println("Carrello in sessione:\t" + carrello);
            
            if (carrello == null) {
                //Creazione di un carrello temporaneo
                carrello = new Carrello();
                carrelloDAO.save(carrello);
                session.setAttribute("carrello", carrello);
                System.out.println("Carrello temp:\t" + carrello.toString());
            }   
        } else {
            setAttributiSessione(utente, session);
        }            
    }
    
    private void setAttributiSessione(Utente utente, HttpSession session) {
        session.setAttribute("utente", utente);
        session.setAttribute("isCliente", 1);
        session.setAttribute("isResponsabileMagazzino", 0);
        session.setAttribute("isGestoreOrdini", 0);
        session.setAttribute("isAdmin", 0);
        
    
        if (utente.getRuoli().contains(Ruolo.CLIENTE)) {
            // Controlla se l'utente ha già un carrello nel database
            Carrello carrelloUtente = carrelloDAO.findByUtente(utente);

            //Controlla se esiste già un carrello in sessione
            Carrello tempCarrello = (Carrello) session.getAttribute("carrello");
            
            if(carrelloUtente == null && tempCarrello == null) {
                //Creare carrello da associare all'utente
                tempCarrello = new Carrello(utente, null);
                utente.setCarrello(tempCarrello);
                tempCarrello.setUtente(utente);
                carrelloDAO.save(tempCarrello);                
                utenteDAO.save(utente);
                session.setAttribute("carrello", tempCarrello);
            } else if (carrelloUtente == null && tempCarrello != null) {
                //Associare il carrello temporaneo all'utente
                utente.setCarrello(tempCarrello);
                tempCarrello.setUtente(utente);
                carrelloDAO.save(tempCarrello);
                utenteDAO.save(utente);
                session.setAttribute("carrello", tempCarrello);
            } else if (carrelloUtente != null && tempCarrello == null) {
                //L'utente ha già un carrello + caricare il carrello in sessione
                session.setAttribute("carrello", carrelloUtente);
            } else if (carrelloUtente != null && tempCarrello != null) {
                //Aggiungere i prodotti del carrello temporaneo in quello persistente
                List<CarrelloProdotto> voci = tempCarrello.getProdotti();
                
                for (CarrelloProdotto voce : voci)
                    carrelloService.addProdottoCarrello(carrelloUtente, voce.getProdotto(), voce.getQuantità());
                
                carrelloDAO.save(carrelloUtente);                
                utenteDAO.save(utente);
                session.setAttribute("carrello", carrelloUtente);
            }
            
            session.setAttribute("isCliente", 1);
            
            Carrello carrello = (Carrello) session.getAttribute("carrello");
            
            System.out.println("Carrello query:\t" + carrello.toString());
        } else if (utente.getRuoli().contains(Ruolo.RESPONSABILE_MAGAZZINO)) {
            session.setAttribute("isResponsabileMagazzino", 1);
        } else if (utente.getRuoli().contains(Ruolo.GESTORE_ORDINI)) {
            session.setAttribute("isGestoreOrdini", 1);
        } else if (utente.getRuoli().contains(Ruolo.AMMINISTRATORE)) {
            session.setAttribute("isAdmin", 1);
        }
    
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