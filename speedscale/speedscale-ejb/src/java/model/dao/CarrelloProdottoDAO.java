package model.dao;

import model.bean.CarrelloProdotto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class CarrelloProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(CarrelloProdotto cp) {
        if (cp.getId() == null) {
            entityManager.persist(cp);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(cp);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    public CarrelloProdotto findById(Long id) {
        return entityManager.find(CarrelloProdotto.class, id);
    }

    public List<CarrelloProdotto> findAll() {
        return entityManager.createQuery("SELECT c FROM CarrelloProdotto c", CarrelloProdotto.class).getResultList();
    }
    
    @Transactional
    public void delete(CarrelloProdotto cp) {
        if (cp != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(cp)) {
                cp = entityManager.merge(cp);  // Merge per associarla
            }
            
            entityManager.remove(cp);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(CarrelloProdotto cp) {
        if (entityManager.contains(cp)) {
            entityManager.refresh(cp);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            cp = entityManager.merge(cp);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(cp);  // Esegui refresh sul merge
        }
    }
}
