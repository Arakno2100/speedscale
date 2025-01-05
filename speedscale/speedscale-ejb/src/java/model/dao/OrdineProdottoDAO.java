package model.dao;

import model.bean.OrdineProdotto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class OrdineProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(OrdineProdotto op) {
        if (op.getId() == null) {
            entityManager.persist(op);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(op);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    public OrdineProdotto findById(Long id) {
        return entityManager.find(OrdineProdotto.class, id);
    }

    public List<OrdineProdotto> findAll() {
        return entityManager.createQuery("SELECT o FROM OrdineProdotto o", OrdineProdotto.class).getResultList();
    }

    @Transactional
    public void delete(OrdineProdotto op) {
        if (op != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(op)) {
                op = entityManager.merge(op);  // Merge per associarla
            }
            
            entityManager.remove(op);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(OrdineProdotto op) {
        if (entityManager.contains(op)) {
            entityManager.refresh(op);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            op = entityManager.merge(op);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(op);  // Esegui refresh sul merge
        }
    }
}
