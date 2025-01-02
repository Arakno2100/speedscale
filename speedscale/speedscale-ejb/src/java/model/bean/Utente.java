package model.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Utente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nome;
    private String cognome;
    private Date dataNascita;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Ruolo> ruoli;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Carrello carrello;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Indirizzo> indirizzi;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<MetodoPagamento> metodiPagamento;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Ordine> ordini;

    public Utente(String email, String password, String nome, String cognome, Date dataNascita, List<Ruolo> ruoli, Carrello carrello, List<Indirizzo> indirizzi, List<MetodoPagamento> metodiPagamento, List<Ordine> ordini) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.ruoli = ruoli;
        this.carrello = carrello;
        this.indirizzi = indirizzi;
        this.metodiPagamento = metodiPagamento;
        this.ordini = ordini;
    }

    public Utente() {
    }

    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public List<Ruolo> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public List<Indirizzo> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(List<Indirizzo> indirizzi) {
        this.indirizzi = indirizzi;
    }

    public List<MetodoPagamento> getMetodiPagamento() {
        return metodiPagamento;
    }

    public void setMetodiPagamento(List<MetodoPagamento> metodiPagamento) {
        this.metodiPagamento = metodiPagamento;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(List<Ordine> ordini) {
        this.ordini = ordini;
    }

    @Override
    public String toString() {
        return "Utente{" + "id=" + id + ", email=" + email + ", password=" + password + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita + ", ruoli=" + ruoli + ", carrello=" + carrello + ", indirizzi=" + indirizzi + ", metodiPagamento=" + metodiPagamento + ", ordini=" + ordini + '}';
    }
}
