package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.bean.Ruolo;
import model.bean.Utente;

@Singleton
@Startup
public class DatabasePopulator {
    
    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @PostConstruct
    private void populateDB() {
        
        List<Ruolo> ruoli = new ArrayList<>();
        ruoli.add(Ruolo.AMMINISTRATORE);
        Utente admin = new Utente("admin@email.it", "ee33429c9cf42536c60b539488a1e4bdd5b162a69f57cf11cd38b2b9e1e069d5392f9d5329145179889a98483d379f0f6c03708538376e2f54f48afd22973c8c", "Mario", "Rossi", new Date(), ruoli);        
        
        entityManager.persist(admin);
    }

}