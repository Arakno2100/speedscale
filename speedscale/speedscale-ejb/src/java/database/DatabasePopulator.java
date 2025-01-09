package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.bean.Marca;
import model.bean.Prodotto;
import model.bean.Ruolo;
import model.bean.Scala;
import model.bean.Utente;
import model.dao.ProdottoDAO;
import model.dao.UtenteDAO;
import service.Catalogo;

@Singleton
@Startup
public class DatabasePopulator {
    
    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;
    
    @EJB
    private UtenteDAO utenteDAO;
    
    @EJB
    private ProdottoDAO prodottoDAO;
    
    @EJB
    private Catalogo catalogo;

    @PostConstruct
    private void populateDB() {
        
        createUtenti();
        createProdotti();
    }
    
    private void createUtenti() {
        
        List<Ruolo> adminRuoli = new ArrayList<>();
        adminRuoli.add(Ruolo.AMMINISTRATORE);
        Utente admin = new Utente("admin@email.it", "ee33429c9cf42536c60b539488a1e4bdd5b162a69f57cf11cd38b2b9e1e069d5392f9d5329145179889a98483d379f0f6c03708538376e2f54f48afd22973c8c", "Mario", "Rossi", new Date(), adminRuoli);        
        
        List<Ruolo> clienteRuoli = new ArrayList<>();
        clienteRuoli.add(Ruolo.CLIENTE);
        Utente cliente = new Utente("user@email.it", "88ba325658610a3d6e200eb01d6cf71390d5dab8abd46d309f306a3082489fea9e5817fe9a54dd91775a09ce71bc2d5aa7ebbd18ea2c5e33cf797f56a4256b06", "Riccardo", "Verdi", new Date(), clienteRuoli);
        
        utenteDAO.save(admin);
        utenteDAO.save(cliente);
    }
    
    private void createProdotti() {
        
        List<String> urls = new ArrayList<>();
        
        Prodotto p1 = new Prodotto("Charles Leclerc Ferrari SF-24 Imola 2024",
                "Il modellino Burago, Bburago della nuova e bellissima Ferrari SF-24 la monoposto con cui Charles Leclerc disputa il mondiale F1 2024!",
                14.99,
                100,
                Scala.SCA_1_43,
                Marca.BBURAGO);
        
        urls.add("https://i0.wp.com/motorsportclan.com/wp-content/uploads/2024/06/modellino-ferrari-f1-2024-sf-24-leclerc-burago-scala-143.jpg?fit=1200%2C800&ssl=1");
        p1.setUrls(urls);
        prodottoDAO.save(p1);
        
        Prodotto p2 = new Prodotto("Ferrari 499P #51 Vincitrice 24 ore Le Mans 2023 Hypercar",
                "L’attesissimo modellino della stupenda Ferrari 499P numero 51, il prototipo hypercar che riporta, dopo 58 anni, la scuderia di Maranello alla vittoria nella leggendaria 24 ore di Le Mans con Alessandro Pier Guidi, Antonio Giovinazzi e James Calado!",
                25.50,
                100,
                Scala.SCA_1_43,
                Marca.BBURAGO);
        
        urls = new ArrayList<>();
        urls.add("https://i0.wp.com/motorsportclan.com/wp-content/uploads/2023/06/modellino-ferrari-499p-numero-51-bburago-24h-le-mans-1-43.jpg?fit=1029%2C700&ssl=1");
        p2.setUrls(urls);
        prodottoDAO.save(p2);
        
        Prodotto p3 = new Prodotto("Francesco Bagnaia Ducati GP22 Campione MotoGP 2022",
                "L’attesissimo modellino della Ducati MotoGP Campione del mondo 2022 con il nostro Pecco Bagnaia!",
                14.90,
                100,
                Scala.SCA_1_18,
        Marca.LOOKSMART);
        
        urls = new ArrayList<>();
        urls.add("https://i0.wp.com/motorsportclan.com/wp-content/uploads/2023/09/modellino-ducati-pecco-bagnaia-gp22-campione-2022-scala1-18.jpg?fit=1200%2C800&ssl=1");
        p3.setUrls(urls);
        prodottoDAO.save(p3);
        
        catalogo.addNewProdotto(p1);
        catalogo.addNewProdotto(p2);
        catalogo.addNewProdotto(p3);        
    }
}