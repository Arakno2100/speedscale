package model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    private String telefono;

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
        
        
        if ((carrello.getUtente() == null) || (!carrello.getUtente().equals(this)))
            carrello.setUtente(this);
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
        final Utente other = (Utente) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cognome, other.cognome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataNascita, other.dataNascita)) {
            return false;
        }
        if (!Objects.equals(this.ruoli, other.ruoli)) {
            return false;
        }
        if (!Objects.equals(this.carrello, other.carrello)) {
            return false;
        }
        if (!Objects.equals(this.indirizzi, other.indirizzi)) {
            return false;
        }
        if (!Objects.equals(this.metodiPagamento, other.metodiPagamento)) {
            return false;
        }
        return Objects.equals(this.ordini, other.ordini);
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