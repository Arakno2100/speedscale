package model.bean;

import javax.persistence.*;
import java.util.List;

@Entity
public class Carrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL)
    private List<CarrelloProdotto> prodotti; // Associazioni con quantità

    public Carrello() {
    }

    public Carrello(Utente utente, List<CarrelloProdotto> prodotti) {
        this.utente = utente;
        this.prodotti = prodotti;
    }

    public Long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<CarrelloProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<CarrelloProdotto> prodotti) {
        this.prodotti = prodotti;
    }

    @Override
    public String toString() {
        return "Carrello{" + "id=" + id + ", utente=" + utente + ", prodotti=" + prodotti + '}';
    }    
}
