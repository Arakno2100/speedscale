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
    
    @ManyToOne
    @JoinColumn(name = "indirizzo_id")
    private Indirizzo indirizzo;
    
    @ManyToOne
    @JoinColumn(name = "metodo_id")
    private MetodoPagamento metodoPagamento;

    @OneToMany(mappedBy = "ordine", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<OrdineProdotto> prodotti; // Associazioni con quantità e prezzoUnitario

    public Ordine() {}

    public Ordine(Date data, StatoOrdine stato, Utente utente, Indirizzo indirizzo, MetodoPagamento metodoPagamento, List<OrdineProdotto> prodotti) {
        this.data = data;
        this.stato = stato;
        this.utente = utente;
        this.indirizzo = indirizzo;
        this.metodoPagamento = metodoPagamento;
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

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public List<OrdineProdotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(List<OrdineProdotto> prodotti) {
        this.prodotti = prodotti;
    }
    
    public float getTotale() {
        float totale = 0;
        for (OrdineProdotto prodotto : prodotti)
            totale += prodotto.getPrezzoUnitario() * prodotto.getQuantità();
        return totale;
    }

    @Override
    public String toString() {
        return "Ordine{" + "id=" + id + ", data=" + data + ", stato=" + stato + ", utente=" + utente + ", indirizzo=" + indirizzo + ", metodoPagamento=" + metodoPagamento + ", prodotti=" + prodotti + '}';
    }
}
