package model.bean;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantitàDisponibile;
    private String image_path;

    @Enumerated(EnumType.STRING)
    private Scala scala;

    @Enumerated(EnumType.STRING)
    private Marca marca;

    @ElementCollection
    @CollectionTable(name = "prodotto_urls", joinColumns = @JoinColumn(name = "prodotto_id"))
    @Column(name = "url")
    private List<String> urls;  // Lista di URL

    public Prodotto() {}

    public Prodotto(String nome, String descrizione, double prezzo, int quantitàDisponibile, Scala scala, Marca marca) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantitàDisponibile = quantitàDisponibile;
        this.scala = scala;
        this.marca = marca;
        this.urls = new ArrayList<>(); // Lista vuota di default
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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", quantitàDisponibile=" + quantitàDisponibile +
                ", scala=" + scala +
                ", marca=" + marca +
                ", urls=" + urls + '}';
    }
}