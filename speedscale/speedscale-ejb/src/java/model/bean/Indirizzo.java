package model.bean;

import javax.persistence.*;

@Entity
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String via;
    private String città;
    private String cap;
    private String nazione;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public Indirizzo() {
    }

    public Indirizzo(String via, String città, String cap, String nazione, Utente utente) {
        this.via = via;
        this.città = città;
        this.cap = cap;
        this.nazione = nazione;
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Indirizzo{" + "id=" + id + ", via=" + via + ", citt\u00e0=" + città + ", cap=" + cap + ", nazione=" + nazione + ", utente=" + utente + '}';
    }    
}
