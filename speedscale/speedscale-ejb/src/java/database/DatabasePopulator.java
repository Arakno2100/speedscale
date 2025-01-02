package database;

import model.bean.Carrello;
import model.bean.CarrelloProdotto;
import model.bean.Scala;
import model.bean.Indirizzo;
import model.bean.OrdineProdotto;
import model.bean.Ordine;
import model.bean.Prodotto;
import model.bean.Marca;
import model.bean.Ruolo;
import model.bean.MetodoPagamento;
import model.bean.Utente;
import model.bean.StatoOrdine;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Singleton
@Startup
@DataSourceDefinition(
    className = "org.apache.derby.jdbc.EmbeddedDataSource",
    name = "java:global/jdbc/SpeedScaleDataSource",
    user = "APP",
    password = "APP",
    databaseName = "SpeedScaleDB",
    properties = {"create=true"}
)
public class DatabasePopulator {

    @PersistenceContext(unitName = "SpeedScalePU")
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void populateDatabase() {
        // Creazione degli oggetti da popolare nel database

        // Enum
        Ruolo ruoloAdmin = Ruolo.AMMINISTRATORE;
        Ruolo ruoloCliente = Ruolo.CLIENTE;

        // Utilizzo dei nuovi nomi degli enum Scala
        Scala scala1_72 = Scala.SCA_1_72;
        Scala scala1_48 = Scala.SCA_1_48;

        Marca marcaTamiya = Marca.TAMIYA;
        Marca marcaHasegawa = Marca.HASEGAWA;

        StatoOrdine statoElaborazione = StatoOrdine.IN_ELABORAZIONE;
        StatoOrdine statoSpedito = StatoOrdine.SPEDITO;

        // Creazione di un utente di esempio
        Utente utente = new Utente();
        utente.setEmail("admin@modellini.it");
        utente.setPassword("admin123");  // Ricordati di criptare la password
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataNascita(new java.util.Date(1980, 5, 15));
        utente.setRuoli(Arrays.asList(ruoloAdmin));

        // Creazione di un carrello
        Carrello carrello = new Carrello();
        carrello.setUtente(utente);

        // Creazione dei prodotti
        Prodotto prodotto1 = new Prodotto();
        prodotto1.setNome("Modellino Auto");
        prodotto1.setDescrizione("Un modellino di auto sportiva scala 1:72");
        prodotto1.setPrezzo(25.50);
        prodotto1.setQuantitàDisponibile(100);
        prodotto1.setScala(scala1_72);
        prodotto1.setMarca(marcaTamiya);

        Prodotto prodotto2 = new Prodotto();
        prodotto2.setNome("Modellino Aereo");
        prodotto2.setDescrizione("Un modellino di aereo militare scala 1:48");
        prodotto2.setPrezzo(45.99);
        prodotto2.setQuantitàDisponibile(50);
        prodotto2.setScala(scala1_48);
        prodotto2.setMarca(marcaHasegawa);

        // Associare i prodotti al carrello
        CarrelloProdotto carrelloProdotto1 = new CarrelloProdotto();
        carrelloProdotto1.setCarrello(carrello);
        carrelloProdotto1.setProdotto(prodotto1);
        carrelloProdotto1.setQuantità(2);

        CarrelloProdotto carrelloProdotto2 = new CarrelloProdotto();
        carrelloProdotto2.setCarrello(carrello);
        carrelloProdotto2.setProdotto(prodotto2);
        carrelloProdotto2.setQuantità(1);

        // Creazione di un ordine
        Ordine ordine = new Ordine();
        ordine.setData(new java.util.Date());
        ordine.setStato(statoElaborazione);
        ordine.setUtente(utente);

        OrdineProdotto ordineProdotto1 = new OrdineProdotto();
        ordineProdotto1.setOrdine(ordine);
        ordineProdotto1.setProdotto(prodotto1);
        ordineProdotto1.setQuantità(2);
        ordineProdotto1.setPrezzoUnitario(prodotto1.getPrezzo());

        OrdineProdotto ordineProdotto2 = new OrdineProdotto();
        ordineProdotto2.setOrdine(ordine);
        ordineProdotto2.setProdotto(prodotto2);
        ordineProdotto2.setQuantità(1);
        ordineProdotto2.setPrezzoUnitario(prodotto2.getPrezzo());

        // Persistenza dei dati
        entityManager.persist(utente);
        entityManager.persist(carrello);
        entityManager.persist(prodotto1);
        entityManager.persist(prodotto2);
        entityManager.persist(carrelloProdotto1);
        entityManager.persist(carrelloProdotto2);
        entityManager.persist(ordine);
        entityManager.persist(ordineProdotto1);
        entityManager.persist(ordineProdotto2);

        // Aggiungi un indirizzo e un metodo di pagamento
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia("Via Roma 1");
        indirizzo.setCittà("Milano");
        indirizzo.setCap("20100");
        indirizzo.setNazione("Italia");
        indirizzo.setUtente(utente);
        entityManager.persist(indirizzo);

        MetodoPagamento metodoPagamento = new MetodoPagamento();
        metodoPagamento.setNumero("1234567890123456");
        metodoPagamento.setIntestatario("Mario Rossi");
        metodoPagamento.setMeseScadenza(12);
        metodoPagamento.setAnnoScadenza(2025);
        metodoPagamento.setCvv("123");
        metodoPagamento.setUtente(utente);
        entityManager.persist(metodoPagamento);
    }
}
