package model.bean;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date data;

    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<OrdineProdotto> prodotti; // Associazioni con quantità e prezzoUnitario

    public Ordine() {
    }

    public Ordine(Date data, StatoOrdine stato, Utente utente, List<OrdineProdotto> prodotti) {
        this.data = data;
        this.stato = stato;
        this.utente = utente;
        this.prodotti = prodotti;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatoOrdine getStato() {
        return stato;
    }

    public void setStato(StatoOrdine stato) {
        this.stato = stato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<OrdineProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<OrdineProdotto> prodotti) {
        this.prodotti = prodotti;
    }

    @Override
    public String toString() {
        return "Ordine{" + "id=" + id + ", data=" + data + ", stato=" + stato + ", utente=" + utente + ", prodotti=" + prodotti + '}';
    }
}
