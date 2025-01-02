package model.dao;

import model.bean.Ordine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class OrdineDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Ordine ordine) {
        if (ordine.getId() == null) {
            entityManager.persist(ordine);
        } else {
            entityManager.merge(ordine);
        }
    }

    public Ordine findById(Long id) {
        return entityManager.find(Ordine.class, id);
    }

    public List<Ordine> findAll() {
        return entityManager.createQuery("SELECT o FROM Ordine o", Ordine.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Ordine ordine = findById(id);
        if (ordine != null) {
            entityManager.remove(ordine);
        }
    }
}
