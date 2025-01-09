package model.dao;

import model.bean.MetodoPagamento;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class MetodoPagamentoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    // Salva o aggiorna un indirizzo
    @Transactional
    public void save(MetodoPagamento metodo) {
        if (metodo.getId() == null) {
            entityManager.persist(metodo);  // Inserisce un nuovo indirizzo
        } else {
            entityManager.merge(metodo);  // Aggiorna un indirizzo esistente
        }
        entityManager.flush();  // Forza la sincronizzazione con il database
    }

    public MetodoPagamento findById(Long id) {
        return entityManager.find(MetodoPagamento.class, id);
    }

    public List<MetodoPagamento> findAll() {
        return entityManager.createQuery("SELECT m FROM MetodoPagamento m", MetodoPagamento.class).getResultList();
    }

    
    // Rimuove un metodo di pagamento
    @Transactional
    public void delete(MetodoPagamento metodo) {
        if (metodo != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(metodo)) {
                metodo = entityManager.merge(metodo);  // Merge per associarla
            }
            
            entityManager.remove(metodo);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(MetodoPagamento metodo) {
        if (entityManager.contains(metodo)) {
            entityManager.refresh(metodo);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            metodo = entityManager.merge(metodo);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(metodo);  // Esegui refresh sul merge
        }
    }
}
