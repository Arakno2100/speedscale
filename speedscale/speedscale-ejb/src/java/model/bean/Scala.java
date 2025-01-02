package model.bean;

public enum Scala {
    SCA_1_72(1.72),
    SCA_1_48(1.48),
    SCA_1_35(1.35),
    SCA_1_24(1.24),
    SCA_1_18(1.18);

    // Attributo per memorizzare il valore numerico della scala
    private final double valore;

    // Costruttore per inizializzare il valore numerico
    Scala(double valore) {
        this.valore = valore;
    }

    // Metodo per ottenere il valore numerico della scala
    public double getValore() {
        return valore;
    }

    // Metodo opzionale per ottenere una rappresentazione String della scala (ad esempio "1:72")
    public String getStringa() {
        return "1:" + (int) (1 / valore);
    }

    // Metodo per ottenere un enum a partire dal valore numerico (facoltativo)
    public static Scala fromValore(double valore) {
        for (Scala scala : Scala.values()) {
            if (scala.getValore() == valore) {
                return scala;
            }
        }
        throw new IllegalArgumentException("Scala con valore " + valore + " non trovata.");
    }
}
