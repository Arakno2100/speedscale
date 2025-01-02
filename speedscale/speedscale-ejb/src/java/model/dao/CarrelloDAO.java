package model.dao;

import model.bean.Carrello;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class CarrelloDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Carrello carrello) {
        if (carrello.getId() == null) {
            entityManager.persist(carrello);
        } else {
            entityManager.merge(carrello);
        }
    }

    public Carrello findById(Long id) {
        return entityManager.find(Carrello.class, id);
    }

    public List<Carrello> findAll() {
        return entityManager.createQuery("SELECT c FROM Carrello c", Carrello.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Carrello carrello = findById(id);
        if (carrello != null) {
            entityManager.remove(carrello);
        }
    }
}
