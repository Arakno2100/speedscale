package model.bean;

import javax.persistence.*;

@Entity
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String intestatario;
    private int meseScadenza;
    private int annoScadenza;
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public MetodoPagamento() {
    }

    public MetodoPagamento(String numero, String intestatario, int meseScadenza, int annoScadenza, String cvv, Utente utente) {
        this.numero = numero;
        this.intestatario = intestatario;
        this.meseScadenza = meseScadenza;
        this.annoScadenza = annoScadenza;
        this.cvv = cvv;
        this.utente = utente;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIntestatario() {
        return intestatario;
    }

    public void setIntestatario(String intestatario) {
        this.intestatario = intestatario;
    }

    public int getMeseScadenza() {
        return meseScadenza;
    }

    public void setMeseScadenza(int meseScadenza) {
        this.meseScadenza = meseScadenza;
    }

    public int getAnnoScadenza() {
        return annoScadenza;
    }

    public void setAnnoScadenza(int annoScadenza) {
        this.annoScadenza = annoScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "MetodoPagamento{" + "id=" + id + ", numero=" + numero + ", intestatario=" + intestatario + ", meseScadenza=" + meseScadenza + ", annoScadenza=" + annoScadenza + ", cvv=" + cvv + ", utente=" + utente + '}';
    }
}
