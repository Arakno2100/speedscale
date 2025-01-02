package model.dao;

import model.bean.CarrelloProdotto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class CarrelloProdottoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(CarrelloProdotto carrelloProdotto) {
        if (carrelloProdotto.getId() == null) {
            entityManager.persist(carrelloProdotto);
        } else {
            entityManager.merge(carrelloProdotto);
        }
    }

    public CarrelloProdotto findById(Long id) {
        return entityManager.find(CarrelloProdotto.class, id);
    }

    public List<CarrelloProdotto> findAll() {
        return entityManager.createQuery("SELECT c FROM CarrelloProdotto c", CarrelloProdotto.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        CarrelloProdotto carrelloProdotto = findById(id);
        if (carrelloProdotto != null) {
            entityManager.remove(carrelloProdotto);
        }
    }
}
