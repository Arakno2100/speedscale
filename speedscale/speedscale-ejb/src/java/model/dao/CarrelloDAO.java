package model.dao;

import model.bean.Carrello;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import model.bean.Utente;

@Stateless
public class CarrelloDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Carrello carrello) {
        if (carrello.getId() == null) {
            entityManager.persist(carrello);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(carrello);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    public Carrello findById(Long id) {
        return entityManager.find(Carrello.class, id);
    }
    
    public Carrello findByUtente(Utente utente) {
        try
        {
            return entityManager.createQuery("SELECT c FROM Carrello c WHERE c.utente = :utente", Carrello.class)
                            .setParameter("utente", utente)
                            .getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
          
    }

    public List<Carrello> findAll() {
        return entityManager.createQuery("SELECT c FROM Carrello c", Carrello.class).getResultList();
    }

    @Transactional
    public void delete(Carrello carrello) {
        if (carrello != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(carrello)) {
                carrello = entityManager.merge(carrello);  // Merge per associarla
            }
            
            entityManager.remove(carrello);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(Carrello carrello) {
        if (entityManager.contains(carrello)) {
            entityManager.refresh(carrello);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            carrello = entityManager.merge(carrello);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(carrello);  // Esegui refresh sul merge
        }
    }
}
