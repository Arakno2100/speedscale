package model.dao;

import model.bean.OrdineProdotto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class OrdineProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(OrdineProdotto ordineProdotto) {
        if (ordineProdotto.getId() == null) {
            entityManager.persist(ordineProdotto);
        } else {
            entityManager.merge(ordineProdotto);
        }
    }

    public OrdineProdotto findById(Long id) {
        return entityManager.find(OrdineProdotto.class, id);
    }

    public List<OrdineProdotto> findAll() {
        return entityManager.createQuery("SELECT o FROM OrdineProdotto o", OrdineProdotto.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        OrdineProdotto ordineProdotto = findById(id);
        if (ordineProdotto != null) {
            entityManager.remove(ordineProdotto);
        }
    }
}
