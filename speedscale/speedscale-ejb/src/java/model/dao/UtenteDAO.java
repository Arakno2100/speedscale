package model.dao;

import model.bean.Utente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class UtenteDAO {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @Transactional
    public void save(Utente utente) {
        if (utente.getId() == null) {
            entityManager.persist(utente);
            entityManager.flush();
        } else {
            entityManager.merge(utente);
        }
    }

    public Utente findById(Long id) {
        return entityManager.find(Utente.class, id);
    }
    
    public Utente findByEmail(String email) {
        try
        {
            return entityManager.createQuery("SELECT u FROM Utente u WHERE u.email = :email", Utente.class)
                            .setParameter("email", email)
                            .getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
    }

    public List<Utente> findAll() {
        return entityManager.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Utente utente = findById(id);
        if (utente != null) {
            entityManager.remove(utente);
        }
    }
}
