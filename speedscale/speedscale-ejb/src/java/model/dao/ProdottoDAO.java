package model.dao;

import model.bean.Prodotto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class ProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Prodotto prodotto) {
        if (prodotto.getId() == null) {
            entityManager.persist(prodotto);
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

    @Transactional
    public void delete(Long id) {
        Prodotto prodotto = findById(id);
        if (prodotto != null) {
            entityManager.remove(prodotto);
        }
    }
}
