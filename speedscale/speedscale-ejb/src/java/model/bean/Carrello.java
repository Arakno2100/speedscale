package model.bean;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
        
        if(!utente.getCarrello().equals(this))
            utente.setCarrello(this);
    }

    public List<CarrelloProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<CarrelloProdotto> prodotti) {
        this.prodotti = prodotti;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carrello other = (Carrello) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.utente, other.utente)) {
            return false;
        }
        return Objects.equals(this.prodotti, other.prodotti);
    }
    
    

    @Override
    public String toString() {
        return "Carrello{" + "id=" + id + ", utente=" + utente + ", prodotti=" + prodotti + '}';
    }    
}
