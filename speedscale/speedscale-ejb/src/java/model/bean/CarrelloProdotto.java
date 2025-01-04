package model.bean;

import javax.persistence.*;

@Entity
public class CarrelloProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrello_id")
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    private int quantità;

    public CarrelloProdotto() {
    }

    public CarrelloProdotto(Carrello carrello, Prodotto prodotto, int quantità) {
        this.carrello = carrello;
        this.prodotto = prodotto;
        this.quantità = quantità;
    }

    public Long getId() {
        return id;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
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

    @Override
    public String toString() {
        return "CarrelloProdotto{" + "id=" + id + ", carrelloId=" + carrello.getId() + ", prodotto=" + prodotto + ", quantit\u00e0=" + quantità + '}';
    }
}
