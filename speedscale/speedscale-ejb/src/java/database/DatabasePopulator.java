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
        
        List<Ruolo> adminRuoli = new ArrayList<>();
        adminRuoli.add(Ruolo.AMMINISTRATORE);
        Utente admin = new Utente("admin@email.it", "ee33429c9cf42536c60b539488a1e4bdd5b162a69f57cf11cd38b2b9e1e069d5392f9d5329145179889a98483d379f0f6c03708538376e2f54f48afd22973c8c", "Mario", "Rossi", new Date(), adminRuoli);        
        
        List<Ruolo> clienteRuoli = new ArrayList<>();
        clienteRuoli.add(Ruolo.CLIENTE);
        Utente cliente = new Utente("user@email.it", "88ba325658610a3d6e200eb01d6cf71390d5dab8abd46d309f306a3082489fea9e5817fe9a54dd91775a09ce71bc2d5aa7ebbd18ea2c5e33cf797f56a4256b06", "Riccardo", "Verdi", new Date(), clienteRuoli);
        
        entityManager.persist(admin);
        entityManager.persist(cliente);
    }

}