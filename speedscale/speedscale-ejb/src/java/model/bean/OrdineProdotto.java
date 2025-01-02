package model.bean;

import javax.persistence.*;

@Entity
public class OrdineProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int quantità;
    private double prezzoUnitario;

    public OrdineProdotto() {
    }

    public OrdineProdotto(Ordine ordine, Prodotto prodotto, int quantità, double prezzoUnitario) {
        this.ordine = ordine;
        this.prodotto = prodotto;
        this.quantità = quantità;
        this.prezzoUnitario = prezzoUnitario;
    }

    public Long getId() {
        return id;
    }

    public Ordine getOrdine() {
        return ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantità() {
        return quantità;
    }

    public void setQuantità(int quantità) {
        this.quantità = quantità;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    @Override
    public String toString() {
        return "OrdineProdotto{" + "id=" + id + ", ordine=" + ordine + ", prodotto=" + prodotto + ", quantit\u00e0=" + quantità + ", prezzoUnitario=" + prezzoUnitario + '}';
    }
}
