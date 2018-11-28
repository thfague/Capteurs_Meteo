package Class_Metier;

public abstract class Capteur {
    private float valeur;

    public Capteur(float valeur) {
        this.valeur = valeur;
    }

    public float getValeur() {
        return valeur;
    }
}
