package model.dao;

import model.bean.Ordine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class OrdineDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    // Salva o aggiorna un indirizzo
    @Transactional
    public void save(Ordine ordine) {
        if (ordine.getId() == null) {
            entityManager.persist(ordine);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(ordine);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    public Ordine findById(Long id) {
        return entityManager.find(Ordine.class, id);
    }

    public List<Ordine> findAll() {
        return entityManager.createQuery("SELECT o FROM Ordine o", Ordine.class).getResultList();
    }

    // Rimuove un metodo di pagamento
    @Transactional
    public void delete(Ordine ordine) {
        if (ordine != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(ordine)) {
                ordine = entityManager.merge(ordine);  // Merge per associarla
            }
            
            entityManager.remove(ordine);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(Ordine ordine) {
        if (entityManager.contains(ordine)) {
            entityManager.refresh(ordine);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            ordine = entityManager.merge(ordine);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(ordine);  // Esegui refresh sul merge
        }
    }
}
