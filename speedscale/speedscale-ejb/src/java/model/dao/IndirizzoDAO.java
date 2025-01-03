package model.dao;

import model.bean.Indirizzo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class IndirizzoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    // Salva o aggiorna un indirizzo
    @Transactional
    public void save(Indirizzo indirizzo) {
        if (indirizzo.getId() == null) {
            entityManager.persist(indirizzo);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(indirizzo);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    // Trova un indirizzo per ID
    public Indirizzo findById(Long id) {
        return entityManager.find(Indirizzo.class, id);
    }

    // Trova tutti gli indirizzi
    public List<Indirizzo> findAll() {
        return entityManager.createQuery("SELECT i FROM Indirizzo i", Indirizzo.class).getResultList();
    }

    // Rimuove un indirizzo
    @Transactional
    public void delete(Indirizzo indirizzo) {
        if (indirizzo != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(indirizzo)) {
                indirizzo = entityManager.merge(indirizzo);  // Merge per associarla
            }
            
            entityManager.remove(indirizzo);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(Indirizzo indirizzo) {
        if (entityManager.contains(indirizzo)) {
            entityManager.refresh(indirizzo);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            indirizzo = entityManager.merge(indirizzo);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(indirizzo);  // Esegui refresh sul merge
        }
    }
}
