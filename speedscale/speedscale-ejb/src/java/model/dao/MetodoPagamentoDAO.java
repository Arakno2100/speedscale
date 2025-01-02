package model.dao;

import model.bean.MetodoPagamento;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class MetodoPagamentoDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(MetodoPagamento metodoPagamento) {
        if (metodoPagamento.getId() == null) {
            entityManager.persist(metodoPagamento);
        } else {
            entityManager.merge(metodoPagamento);
        }
    }

    public MetodoPagamento findById(Long id) {
        return entityManager.find(MetodoPagamento.class, id);
    }

    public List<MetodoPagamento> findAll() {
        return entityManager.createQuery("SELECT m FROM MetodoPagamento m", MetodoPagamento.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        MetodoPagamento metodoPagamento = findById(id);
        if (metodoPagamento != null) {
            entityManager.remove(metodoPagamento);
        }
    }
}
