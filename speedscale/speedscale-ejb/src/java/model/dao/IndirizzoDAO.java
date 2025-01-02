package model.dao;

import model.bean.Indirizzo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class IndirizzoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Indirizzo indirizzo) {
        if (indirizzo.getId() == null) {
            entityManager.persist(indirizzo);
        } else {
            entityManager.merge(indirizzo);
        }
    }

    public Indirizzo findById(Long id) {
        return entityManager.find(Indirizzo.class, id);
    }

    public List<Indirizzo> findAll() {
        return entityManager.createQuery("SELECT i FROM Indirizzo i", Indirizzo.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Indirizzo indirizzo = findById(id);
        if (indirizzo != null) {
            entityManager.remove(indirizzo);
        }
    }
}
