package service;

import java.nio.charset.StandardCharsets;
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

@Singleton
public class RegistroUtenti {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private CarrelloDAO carrelloDAO;

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
     * Verifica se la password fornita corrisponde all'hash salvato nel database.
     * 
     * @param pwd password da verificare
     * @param storedHash hash salvato nel database
     * @return true se la password corrisponde, false altrimenti
     */
    private boolean checkPassword(String pwd, String storedHash) {
        return pwd.equals(storedHash);
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
        if (utente != null) {
            session.setAttribute("utente", utente);  // Memorizza l'utente nella sessione
        }
        
        if (utente.getRuoli().contains(Ruolo.CLIENTE)) {
            // Controlla se l'utente ha già un carrello nel database
            Carrello carrello = carrelloDAO.findByUtente(utente);

            if (carrello == null) {
                // Se non esiste, crea un nuovo carrello
                carrello = new Carrello(utente, null);
                utente.setCarrello(carrello);
                carrello.setUtente(utente);
            }
            
            System.out.println("Carrello query:\t" + carrello.toString());
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