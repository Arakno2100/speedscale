package model.bean;

import javax.persistence.*;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantitàDisponibile;

    @Enumerated(EnumType.STRING)
    private Scala scala;

    @Enumerated(EnumType.STRING)
    private Marca marca;

    // Getters and setters

    public Prodotto() {
    }

    public Prodotto(String nome, String descrizione, double prezzo, int quantitàDisponibile, Scala scala, Marca marca) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantitàDisponibile = quantitàDisponibile;
        this.scala = scala;
        this.marca = marca;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantitàDisponibile() {
        return quantitàDisponibile;
    }

    public void setQuantitàDisponibile(int quantitàDisponibile) {
        this.quantitàDisponibile = quantitàDisponibile;
    }

    public Scala getScala() {
        return scala;
    }

    public void setScala(Scala scala) {
        this.scala = scala;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Prodotto{" + "id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", prezzo=" + prezzo + ", quantit\u00e0Disponibile=" + quantitàDisponibile + ", scala=" + scala + ", marca=" + marca + '}';
    }
}
