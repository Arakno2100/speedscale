package model.bean;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Indirizzo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String via;
    private String citta;
    private String provincia;
    private String cap;
    private String nazione;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public Indirizzo() {}

    public Indirizzo(String via, String citta, String provincia, String cap, String nazione) {
        this.via = via;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.nazione = nazione;
    }    

    public Indirizzo(String via, String citta, String cap, String nazione, Utente utente) {
        this.via = via;
        this.citta = citta;
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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
        return "Indirizzo{" + "id=" + id + ", via=" + via + ", citt\u00e0=" + citta + ", cap=" + cap + ", nazione=" + nazione + ", utente=" + utente + '}';
    }    
}
