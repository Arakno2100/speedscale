package model.dao;

import model.bean.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class ProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @Transactional
    public void save(Prodotto prodotto) {
        if (prodotto.getId() == null) {
            entityManager.persist(prodotto);
            entityManager.flush();
        } else {
            entityManager.merge(prodotto);
        }
    }
    
    public Prodotto findById(Long id) {
        return entityManager.find(Prodotto.class, id);
    }
    
    public List<Prodotto> findAll() {
        return entityManager.createQuery("SELECT p FROM Prodotto p", Prodotto.class).getResultList();
    }
    /*
    // Trova i prodotti in base alla marca e alla scala
    public List<Prodotto> findByMarcaAndScala(String marca, String scala) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Prodotto p WHERE p.marca = :marca AND p.scala = :scala", Prodotto.class)
                    .setParameter("marca", marca)
                    .setParameter("scala", scala)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Trova i prodotti in base alla marca
    public List<Prodotto> findByMarca(String marca) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Prodotto p WHERE p.marca = :marca", Prodotto.class)
                    .setParameter("marca", marca)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Trova i prodotti in base alla scala
    public List<Prodotto> findByScala(String scala) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Prodotto p WHERE p.scala = :scala", Prodotto.class)
                    .setParameter("scala", scala)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    // Trova i prodotti più recenti (per esempio)
    public List<Prodotto> doRetrieveLatest(int limit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Prodotto p ORDER BY p.id DESC", Prodotto.class)
                    .setMaxResults(limit)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
    */
    
    // Rimuove un indirizzo
    @Transactional
    public void delete(Prodotto prodotto) {
        if (prodotto != null) {
            // Se l'entità non è gestita (staccata), la uniamo al contesto di persistenza
            if (!entityManager.contains(prodotto)) {
                prodotto = entityManager.merge(prodotto);  // Merge per associarla
            }
            
            entityManager.remove(prodotto);  // Rimuove l'entità
            entityManager.flush();  // Forza la sincronizzazione con il database
        }
    }

    // Forza il refresh dell'indirizzo solo se è gestito
    public void refresh(Prodotto prodotto) {
        if (entityManager.contains(prodotto)) {
            entityManager.refresh(prodotto);  // Solo se l'indirizzo è gestito
        } else {
            // Altrimenti ricarica dal database
            prodotto = entityManager.merge(prodotto);  // Unisce l'entità al contesto di persistenza
            entityManager.refresh(prodotto);  // Esegui refresh sul merge
        }
    }
}
