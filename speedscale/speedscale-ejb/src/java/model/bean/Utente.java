package model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Utente implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String nome;
    private String cognome;

    @Temporal(TemporalType.DATE)
    private Date dataNascita;

    @ElementCollection
    @CollectionTable(name = "utente_ruoli", joinColumns = @JoinColumn(name = "utente_id"))
    @Column(name = "ruolo")
    @Enumerated(EnumType.STRING)
    private List<Ruolo> ruoli;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Carrello carrello;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Indirizzo> indirizzi;

    @OneToMany(mappedBy = "utente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MetodoPagamento> metodiPagamento;

    @OneToMany(mappedBy = "utente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Ordine> ordini;

    public Utente() {}
    
    public Utente(String email, String password, String nome, String cognome, Date dataNascita, List<Ruolo> ruoli) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.ruoli = ruoli;
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
        return "Utente{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", ruoli=" + ruoli +
                '}';
    }
}